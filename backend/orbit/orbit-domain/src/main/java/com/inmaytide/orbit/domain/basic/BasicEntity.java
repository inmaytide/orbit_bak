package com.inmaytide.orbit.domain.basic;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.data.annotation.*;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class BasicEntity implements Serializable {

    private static final long serialVersionUID = 5784033340704847103L;

    @Id(strategy = Id.GenerationType.ASSIGNATION)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Column(name = "create_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @CreatedDate
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @LastModifiedDate
    private LocalDateTime updateTime;

    @CreatedBy
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creator;

    @LastModifiedBy
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updater;

    @Version
    private Integer version;

    public BasicEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
