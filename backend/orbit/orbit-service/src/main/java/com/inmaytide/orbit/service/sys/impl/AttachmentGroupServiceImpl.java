package com.inmaytide.orbit.service.sys.impl;

import com.inmaytide.orbit.dao.sys.AttachmentGroupRepository;
import com.inmaytide.orbit.service.sys.AttachmentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
