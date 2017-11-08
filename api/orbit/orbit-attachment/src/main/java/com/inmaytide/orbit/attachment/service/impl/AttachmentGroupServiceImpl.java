package com.inmaytide.orbit.attachment.service.impl;

import com.inmaytide.orbit.attachment.dao.AttachmentGroupRepository;
import com.inmaytide.orbit.attachment.service.AttachmentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Moss
 * @since October 27, 2017
 */
@Service
public class AttachmentGroupServiceImpl implements AttachmentGroupService {

    private AttachmentGroupRepository repository;

    @Autowired
    public AttachmentGroupServiceImpl(AttachmentGroupRepository repository) {
        this.repository = repository;
    }


    @Override
    public AttachmentGroupRepository getRepository() {
        return repository;
    }
}
