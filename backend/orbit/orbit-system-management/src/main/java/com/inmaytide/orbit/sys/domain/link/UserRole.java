package com.inmaytide.orbit.sys.domain.link;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 7075901940386166215L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "u_id")
    private Long uId;

    @Column(name = "r_id")
    private Long rId;

    public UserRole() {
    }

    public UserRole(Long uId, Long rId) {
        this.uId = uId;
        this.rId = rId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }
}
