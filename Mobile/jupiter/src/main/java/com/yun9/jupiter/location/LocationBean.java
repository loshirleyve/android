package com.yun9.jupiter.location;

import com.baidu.location.BDLocation;

/**
 * Created by Leon on 15/5/28.
 */
public class LocationBean {

    private String time;
    private int code;
    private double latitude;
    private double lontitude;
    private double radius;
    private String addr;
    private String province;
    private String city;
    private String district;
    private float speed;
    private int satelliteNumber;
    private int locType;


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(this.getTime());
        sb.append("\nType code(0 GPS 1 Network) : ");
        sb.append(this.getLocType());
        sb.append("\nlatitude : ");
        sb.append(this.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(this.getLontitude());
        sb.append("\nradius : ");
        sb.append(this.getRadius());
        if (this.getLocType() == LocationFactory.LocType_GPS) {
            sb.append("\nspeed : ");
            sb.append(this.getSpeed());
            sb.append("\nsatellite : ");
            sb.append(this.getSatelliteNumber());
        } else if (this.getLocType() == LocationFactory.LocType_NETWORK) {
            sb.append("\naddr : ");
            sb.append(this.getAddr());
            sb.append("\nProvince : ");
            sb.append(this.getProvince());
            sb.append("\ncity : ");
            sb.append(this.getCity());
            sb.append("\nDistrict : ");
            sb.append(this.getDistrict());
        }

        return sb.toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLontitude() {
        return lontitude;
    }

    public void setLontitude(double lontitude) {
        this.lontitude = lontitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getSatelliteNumber() {
        return satelliteNumber;
    }

    public void setSatelliteNumber(int satelliteNumber) {
        this.satelliteNumber = satelliteNumber;
    }

    public int getLocType() {
        return locType;
    }

    public void setLocType(int locType) {
        this.locType = locType;
    }
}