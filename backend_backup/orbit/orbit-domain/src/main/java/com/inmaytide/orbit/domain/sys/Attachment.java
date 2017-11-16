package com.inmaytide.orbit.domain.sys;

import com.inmaytide.orbit.domain.basic.BasicEntity;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Entity;

@Entity(table = "sys_attachment")
public class Attachment extends BasicEntity {

    private static final long serialVersionUID = -9196737333697039963L;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "storage_name")
    private String storageName;

    private String extension;

    @Column(name = "storage_address")
    private String storageAddress;

    @Column(name = "group_id")
    private Long group;

    private Long belong;

    private Long size;

    private Integer status;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public Long getBelong() {
        return belong;
    }

    public void setBelong(Long belong) {
        this.belong = belong;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
