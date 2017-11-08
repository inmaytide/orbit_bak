package com.inmaytide.orbit.attachment.dao;


import com.inmaytide.orbit.attachment.domain.AttachmentGroup;
import org.springframework.data.mybatis.repository.support.MybatisRepository;

/**
 * @author Moss
 * @since October 27, 2017
 */
public interface AttachmentGroupRepository extends MybatisRepository<AttachmentGroup, Long> {
}
