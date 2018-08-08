package com.inmaytide.orbit.commons.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 5784033340704847103L;

    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long creator;

    private Long updater;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
