package com.inmaytide.orbit.attachment.service;

import com.inmaytide.orbit.domain.sys.Attachment;
import com.inmaytide.orbit.service.basic.BasicService;

import java.util.Optional;

public interface AttachmentService extends BasicService {

    Optional<Attachment> get(Long id);

    Optional<Attachment> getByStatus(Long id, String status);
}
