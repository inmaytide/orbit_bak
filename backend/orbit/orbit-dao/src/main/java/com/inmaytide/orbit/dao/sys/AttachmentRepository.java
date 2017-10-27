package com.inmaytide.orbit.dao.sys;

import com.inmaytide.orbit.domain.sys.Attachment;

import org.springframework.data.mybatis.repository.support.MybatisRepository;

/**
 * @author Moss
 * @since October 27, 2017
 */
public interface AttachmentRepository extends MybatisRepository<Attachment, Long> {
}
