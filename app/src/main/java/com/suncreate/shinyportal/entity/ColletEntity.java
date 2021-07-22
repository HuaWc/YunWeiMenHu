package com.suncreate.shinyportal.entity;

/**
 * Created by Christ on 2021/7/22.
 * By an amateur android developer
 * Email 627447123@qq.com
 */
public class ColletEntity {

    private String id;
    private String alarmId;
    private String collectionPersionId;
    private String addTime;
    private int isCollect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getCollectionPersionId() {
        return collectionPersionId;
    }

    public void setCollectionPersionId(String collectionPersionId) {
        this.collectionPersionId = collectionPersionId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }
}
