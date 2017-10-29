package com.inmaytide.orbit.attachment.service;

import com.inmaytide.orbit.consts.AttachmentStatus;
import com.inmaytide.orbit.dao.sys.AttachmentRepository;
import com.inmaytide.orbit.domain.sys.Attachment;
import com.inmaytide.orbit.service.basic.BasicService;

import java.util.Optional;

public interface AttachmentService extends BasicService<AttachmentRepository, Attachment, Long> {

    Optional<Attachment> get(Long id);

    Optional<Attachment> getByStatus(Long id, AttachmentStatus status);

    /**
     * Add a temporary attachment instance to the cache, use {@link AttachmentService#formal(Long)}
     * or {@link AttachmentService#formal(Attachment)} to change to a formal instance,
     * and if it does not, it will be deleted after 24 hours.
     *
     * @param inst a temporary attachment instance needs to add
     * @see AttachmentService#formal(Long)
     * @see AttachmentService#formal(Attachment)
     * @see com.inmaytide.orbit.attachment.service.impl.AttachmentServiceImpl#TEMPORARY_TIMEOUT_HOURS
     */
    Attachment insert(Attachment inst);

    Attachment formal(Long id);

    Attachment formal(Attachment inst);

    void remove(Long id);

    void remove(String ids);

}
