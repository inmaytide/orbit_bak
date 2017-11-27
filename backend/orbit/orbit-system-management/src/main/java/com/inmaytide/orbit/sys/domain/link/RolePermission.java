package com.inmaytide.orbit.sys.domain.link;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 8681542731864225259L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "r_id")
    private Long rId;

    @Column(name = "p_id")
    private Long pId;

    public RolePermission() {
    }

    public RolePermission(Long rId, Long pId) {
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
