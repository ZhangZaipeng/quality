package com.hhh.quality.user.login.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long userId;

    private String uname;

    private String iconImgUrl;

    private String mobile;

    private String email;

    private Short authLevel;

    private String securityPwd;

    private Long inviteesUserId;

    private Integer inviteesProportion; //分成比例

    private Integer inviteesValidNum; //邀请有效个数

    private Date createdAt;

    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getIconImgUrl() {
        return iconImgUrl;
    }

    public void setIconImgUrl(String iconImgUrl) {
        this.iconImgUrl = iconImgUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Short authLevel) {
        this.authLevel = authLevel;
    }

    public String getSecurityPwd() {
        return securityPwd;
    }

    public void setSecurityPwd(String securityPwd) {
        this.securityPwd = securityPwd;
    }

    public Long getInviteesUserId() {
        return inviteesUserId;
    }

    public void setInviteesUserId(Long inviteesUserId) {
        this.inviteesUserId = inviteesUserId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getInviteesProportion() {
        return inviteesProportion;
    }

    public void setInviteesProportion(Integer inviteesProportion) {
        this.inviteesProportion = inviteesProportion;
    }

    public Integer getInviteesValidNum() {
        return inviteesValidNum;
    }

    public void setInviteesValidNum(Integer inviteesValidNum) {
        this.inviteesValidNum = inviteesValidNum;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUname() == null ? other.getUname() == null : this.getUname().equals(other.getUname()))
            && (this.getIconImgUrl() == null ? other.getIconImgUrl() == null : this.getIconImgUrl().equals(other.getIconImgUrl()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAuthLevel() == null ? other.getAuthLevel() == null : this.getAuthLevel().equals(other.getAuthLevel()))
            && (this.getSecurityPwd() == null ? other.getSecurityPwd() == null : this.getSecurityPwd().equals(other.getSecurityPwd()))
            && (this.getInviteesUserId() == null ? other.getInviteesUserId() == null : this.getInviteesUserId().equals(other.getInviteesUserId()))
            && (this.getInviteesProportion() == null ? other.getInviteesProportion() == null : this.getInviteesProportion().equals(other.getInviteesProportion()))
            && (this.getInviteesValidNum() == null ? other.getInviteesValidNum() == null : this.getInviteesValidNum().equals(other.getInviteesValidNum()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUname() == null) ? 0 : getUname().hashCode());
        result = prime * result + ((getIconImgUrl() == null) ? 0 : getIconImgUrl().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAuthLevel() == null) ? 0 : getAuthLevel().hashCode());
        result = prime * result + ((getSecurityPwd() == null) ? 0 : getSecurityPwd().hashCode());
        result = prime * result + ((getInviteesUserId() == null) ? 0 : getInviteesUserId().hashCode());
        result = prime * result + ((getInviteesProportion() == null) ? 0 : getInviteesProportion().hashCode());
        result = prime * result + ((getInviteesValidNum() == null) ? 0 : getInviteesValidNum().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }
}
