package com.suncreate.shinyportal.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 *         日期 2018/8/8
 *         描述 用户信息
 */

public class UserInfo implements Serializable {


    private String addTime;
    private String email;
    private long expireTime;
    private String id;
    private String idCard;
    private int isDel;
    private long loginTime;
    private Map map;
    private String mobileNo;
    private String officePhone;
    private double orgId;
    private String orgname;
    private String password;
    private String policeNo;
    private int ptzLevel;
    private String realName;
    private String token;
    private String userName;
    private Integer userType;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public double getOrgId() {
        return orgId;
    }

    public void setOrgId(double orgId) {
        this.orgId = orgId;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
    }

    public int getPtzLevel() {
        return ptzLevel;
    }

    public void setPtzLevel(int ptzLevel) {
        this.ptzLevel = ptzLevel;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static class Map implements Serializable{
        private String ipAdress;
        private String userTypes;

        public String getIpAdress() {
            return ipAdress;
        }

        public void setIpAdress(String ipAdress) {
            this.ipAdress = ipAdress;
        }

        public String getUserTypes() {
            return userTypes;
        }

        public void setUserTypes(String userTypes) {
            this.userTypes = userTypes;
        }
    }

}
