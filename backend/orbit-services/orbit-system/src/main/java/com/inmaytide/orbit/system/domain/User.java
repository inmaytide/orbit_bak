package com.inmaytide.orbit.system.domain;

import com.inmaytide.orbit.commons.database.annotation.Associated;
import com.inmaytide.orbit.commons.database.annotation.Table;
import com.inmaytide.orbit.commons.domain.AbstractEntity;
import com.inmaytide.orbit.system.consts.UserStatus;

import java.time.LocalDate;

@Table(schema = "public")
public class User extends AbstractEntity {

    private static final long serialVersionUID = -5004364741503962227L;
    private String name;
    private Long org;
    @Associated(table = "organization", source = "org", target = "name")
    private String orgName;
    private String username;
    private String password;
    private UserStatus status;
    private LocalDate birthday;
    private String email;
    private String qq;
    private String wechat;
    private String telephone;
    private String cellphone;
    private Long avatar;
    private String remark;

    public User() {

    }

    public User(Long id, String username) {
        setId(id);
        this.username = username;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Long getAvatar() {
        return avatar;
    }

    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean isDisabled() {
        return this.status == UserStatus.DISABLED;
    }

    public Long getOrg() {
        return org;
    }

    public void setOrg(Long org) {
        this.org = org;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
