package com.inmaytide.orbit.commons.domain;

import com.inmaytide.orbit.commons.database.annotation.Associate;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 5784033340704847103L;

    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long creator;

    @Associate(table = "sys_user", associate = "creator", value = "username")
    private String creatorName;

    private Long updater;

    @Associate(table = "sys_user", associate = "updater", value = "username")
    private String updaterName;

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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }
}
