package com.inmaytide.orbit.uaa.domain;

import com.inmaytide.orbit.enums.UserStatus;
import com.inmaytide.orbit.uaa.domain.converters.UserStatusConverter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.inmaytide.orbit.uaa.domain.id.SnowflakeIdGenerator")
    private Long id;

    private String username;

    private String password;

    private String name;

    private String cellphone;

    private String email;

    @Convert(converter = UserStatusConverter.class)
    private UserStatus status;

    @Column(name = "create_time")
    @CreatedDate
    private LocalDateTime createTime;

    private Long creator;

    @Column(name = "update_time")
    @LastModifiedDate
    private LocalDateTime updateTime;

    private Long updater;

    @Version
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
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

    @Transient
    public boolean disabled() {
        return status == UserStatus.DISABLED;
    }

    public User erasePassword() {
        this.setPassword(null);
        return this;
    }
}

