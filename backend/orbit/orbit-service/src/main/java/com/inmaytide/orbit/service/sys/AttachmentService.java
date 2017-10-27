package com.inmaytide.orbit.service.sys;

import com.inmaytide.orbit.dao.sys.AttachmentRepository;
import com.inmaytide.orbit.domain.sys.Attachment;
import com.inmaytide.orbit.service.basic.BasicService;

import java.util.Optional;

public interface AttachmentService extends BasicService<AttachmentRepository, Attachment, Long> {

    Optional<Attachment> get(Long id);

    Optional<Attachment> getByStatus(Long id, String status);
}
