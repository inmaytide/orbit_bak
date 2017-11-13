package com.inmaytide.orbit.domain.sys.link;

import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;

import java.io.Serializable;

/**
 * @author Moss
 * @since October 25, 2017
 */
@Entity(table = "sys_user_organization")
public class UserOrganization implements Serializable {

    private static final long serialVersionUID = 3650938473566584791L;

    @Id
    private Long id;

    @Column(name = "u_id")
    private Long uId;

    @Column(name = "o_id")
    private Long oId;

    public UserOrganization() {

    }

    public UserOrganization(Long id, Long uId, Long oId) {
        this.id = id;
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
