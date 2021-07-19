package com.hwc.yunweimenhu.entity;

/**
 * Created by Christ on 2021/7/19.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class WorkPeople {

    private int id;
    private String userId;
    private int roleId;
    private Object roleType;
    private int isTemp;
    private Object startTime;
    private Object endTime;
    private Map map;
    private String realName;
    private String mobileNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Object getRoleType() {
        return roleType;
    }

    public void setRoleType(Object roleType) {
        this.roleType = roleType;
    }

    public int getIsTemp() {
        return isTemp;
    }

    public void setIsTemp(int isTemp) {
        this.isTemp = isTemp;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public static class Map {
    }
}
