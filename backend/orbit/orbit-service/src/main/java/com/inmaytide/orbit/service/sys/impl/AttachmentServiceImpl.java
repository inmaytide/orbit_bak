package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.dao.sys.AttachmentRepository;
import com.inmaytide.orbit.domain.sys.Attachment;
import com.inmaytide.orbit.service.sys.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Moss
 * @since October 27, 2017
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private AttachmentRepository repository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<Attachment> get(Long id) {
        return null;
    }

    @Override
    public Optional<Attachment> getByStatus(Long id, String status) {
        return null;
    }

    @Override
    public AttachmentRepository getRepository() {
        return repository;
    }
}
