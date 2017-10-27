package com.inmaytide.orbit.domain.sys.link;

import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;

import java.io.Serializable;

@Entity(table = "sys_role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 8681542731864225259L;

    @Id
    private Long id;

    @Column(name = "r_id")
    private Long rId;

    @Column(name = "p_id")
    private Long pId;

    public RolePermission() {
    }

    public RolePermission(Long id, Long rId, Long pId) {
        this.id = id;
        this.rId = rId;
        this.pId = pId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }
}
