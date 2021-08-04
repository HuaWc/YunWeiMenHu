package com.suncreate.shinyportal.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 *         日期 2018/8/8
 *         描述 用户信息
 */

public class UserInfo implements Serializable {


    private boolean isRemember;
    private String id;
    private String userName;
    private String password;
    private Integer isMustModifyPsd;
    private String realName;
    private String pinyin;
    private Integer sex;
    private String birthday;
    private Integer orgId;
    private String mobileNo;
    private String email;
    private Integer userType;
    private Integer loginCount;
    private String qqNo;
    private String homeAddress;
    private String vcnUserId;
    private String vcnStr;
    private Integer ptzLevel;
    private Integer isUseValidity;
    private String subSysPrivilege;
    private Integer isDel;
    private Integer orderValue;
    private String modifyId;
    private String modifyTime;
    private Integer changeFlag;
    private String officeAddress;
    private Integer validityType;
    private String passwordUpdateDate;
    private Map map;
    private String token;
    private long loginTime;
    private long expireTime;

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsMustModifyPsd() {
        return isMustModifyPsd;
    }

    public void setIsMustModifyPsd(Integer isMustModifyPsd) {
        this.isMustModifyPsd = isMustModifyPsd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getVcnUserId() {
        return vcnUserId;
    }

    public void setVcnUserId(String vcnUserId) {
        this.vcnUserId = vcnUserId;
    }

    public String getVcnStr() {
        return vcnStr;
    }

    public void setVcnStr(String vcnStr) {
        this.vcnStr = vcnStr;
    }

    public Integer getPtzLevel() {
        return ptzLevel;
    }

    public void setPtzLevel(Integer ptzLevel) {
        this.ptzLevel = ptzLevel;
    }

    public Integer getIsUseValidity() {
        return isUseValidity;
    }

    public void setIsUseValidity(Integer isUseValidity) {
        this.isUseValidity = isUseValidity;
    }

    public String getSubSysPrivilege() {
        return subSysPrivilege;
    }

    public void setSubSysPrivilege(String subSysPrivilege) {
        this.subSysPrivilege = subSysPrivilege;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(Integer changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public Integer getValidityType() {
        return validityType;
    }

    public void setValidityType(Integer validityType) {
        this.validityType = validityType;
    }

    public String getPasswordUpdateDate() {
        return passwordUpdateDate;
    }

    public void setPasswordUpdateDate(String passwordUpdateDate) {
        this.passwordUpdateDate = passwordUpdateDate;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }


    public static class Map implements Serializable{
        private String userTypes;
        private String ipAdress;

        public String getUserTypes() {
            return userTypes;
        }

        public void setUserTypes(String userTypes) {
            this.userTypes = userTypes;
        }

        public String getIpAdress() {
            return ipAdress;
        }

        public void setIpAdress(String ipAdress) {
            this.ipAdress = ipAdress;
        }
    }

}
