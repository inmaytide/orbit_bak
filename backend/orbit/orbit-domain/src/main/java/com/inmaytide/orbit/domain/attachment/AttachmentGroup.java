package com.inmaytide.orbit.domain.attachment;

import com.inmaytide.orbit.domain.basis.AbstractEntity;
import org.springframework.data.mybatis.annotations.Entity;

@Entity(table = "sys_attachment_group")
public class AttachmentGroup extends AbstractEntity {

    private static final long serialVersionUID = -7257259206342674263L;

    private Integer status;

    private Long belong;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBelong() {
        return belong;
    }

    public void setBelong(Long belong) {
        this.belong = belong;
    }
}
