package com.inmaytide.orbit.attachment.dao;

import com.inmaytide.orbit.domain.attachment.Attachment;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

import java.util.List;

/**
 * @author Moss
 * @since October 27, 2017
 */
public interface AttachmentRepository extends MybatisRepository<Attachment, Long> {

    List<Attachment> findByBelongIn(List<Long> belongs);

}
