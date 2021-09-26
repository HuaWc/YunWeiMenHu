package com.suncreate.shinyportal.entity;

/**
 * Gps坐标类
 * @author lixin
 * create on 19/06/25
 */
public class Gps {
    private double wgLat;
    private double wgLon;
    public Gps(double wgLat, double wgLon) {
        setWgLat(wgLat);
        setWgLon(wgLon);
    }
    public double getWgLat() {
        return wgLat;
    }
    public void setWgLat(double wgLat) {
        this.wgLat = wgLat;
    }
    public double getWgLon() {
        return wgLon;
    }
    public void setWgLon(double wgLon) {
        this.wgLon = wgLon;
    }
    @Override
    public String toString() {
        return wgLat + "," + wgLon;
    }
}