package com.inmaytide.orbit.attachment.service.impl;

import com.inmaytide.orbit.attachment.dao.AttachmentRepository;
import com.inmaytide.orbit.attachment.enums.AttachmentStatus;
import com.inmaytide.orbit.attachment.service.AttachmentService;
import com.inmaytide.orbit.attachment.util.AttachmentUtils;
import com.inmaytide.orbit.commons.id.SnowflakeIdGenerator;
import com.inmaytide.orbit.attachment.domain.Attachment;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Moss
 * @since October 27, 2017
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private static final String ATTACHMENT_CACHE_PATTERN = "attachmet-%s";

    private static final Integer TEMPORARY_TIMEOUT_HOURS = 24;

    @Autowired
    private AttachmentRepository repository;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Optional<Attachment> get(Long id) {
        Optional<Attachment> inst = getTemporary(id);
        if (!inst.isPresent()) {
            inst = getFormal(id);
        }
        return inst;
    }

    @Override
    public Optional<Attachment> getByStatus(Long id, AttachmentStatus status) {
        switch (status) {
            case TEMPORARY:
                return getTemporary(id);
            case FORMAL:
                return getFormal(id);
        }
        throw new IllegalArgumentException();
    }

    private Optional<Attachment> getTemporary(Long id) {
        Object inst = redisTemplate.opsForValue().get(getCacheKey(id));
        return Optional.ofNullable((Attachment) inst);
    }

    private Optional<Attachment> getFormal(Long id) {
        return getRepository().findById(id);
    }

    @Override
    public Attachment insert(Attachment inst) {
        inst.setId(SnowflakeIdGenerator.generate());
        String key = getCacheKey(inst.getId());
        redisTemplate.opsForValue().set(key, inst, TEMPORARY_TIMEOUT_HOURS, TimeUnit.HOURS);
        return inst;
    }

    @Override
    public void remove(Long id) {
        getFormal(id).ifPresent(this::remove);
    }

    @Override
    public void remove(Attachment inst) {
        getRepository().delete(inst);
        AttachmentUtils.getResource(inst).getFile().delete();
    }

    @Override
    public void remove(String ids) {
        split(ids, NumberUtils::toLong).forEach(this::remove);
    }

    @Override
    public void removeByBelong(String belongs) {
        getRepository().findByBelongIn(split(belongs, NumberUtils::toLong, Collectors.toList())).forEach(this::remove);
    }

    @Override
    public Attachment formal(Long id) {
        return getTemporary(id).map(this::formal).orElseThrow(() -> new IllegalArgumentException("The attachment with [" + id + "] is not exists."));
    }

    @Override
    public Attachment formal(Attachment inst) {
        inst = AttachmentUtils.formal(inst);
        return getRepository().insert(inst);
    }

    private String getCacheKey(Long id) {
        return String.format(ATTACHMENT_CACHE_PATTERN, id);
    }

    @Override
    public AttachmentRepository getRepository() {
        return repository;
    }
}
