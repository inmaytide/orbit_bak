package com.inmaytide.orbit.sys.domain.link;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Moss
 * @since October 25, 2017
 */
@Entity
@Table(name = "sys_user_organization")
public class UserOrganization implements Serializable {

    private static final long serialVersionUID = 3650938473566584791L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "u_id")
    private Long uId;

    @Column(name = "o_id")
    private Long oId;

    public UserOrganization() {

    }

    public UserOrganization(Long uId, Long oId) {
        this.uId = uId;
        this.oId = oId;
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

    public Long getoId() {
        return oId;
    }

    public void setoId(Long oId) {
        this.oId = oId;
    }
}
