package com.yun9.jupiter.location;

/**
 * Created by Leon on 15/5/28.
 */
public class PoiInfoBean {
    private String address;

    private String city;

    private String uid;

    private double latitude;

    private double lontitude;

    private String name;

    private String phoneNum;

    private String postCode;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(256);
        sb.append("地址 : ");
        sb.append(this.getAddress());
        sb.append("\n名称 : ");
        sb.append(this.getName());
        sb.append("\n城市 : ");
        sb.append(this.getCity());
        sb.append("\n经度 : ");
        sb.append(this.getLatitude());
        sb.append("\n维度 : ");
        sb.append(this.getLontitude());
        sb.append("\n电话 : ");
        sb.append(this.getPhoneNum());
        sb.append("\n邮编 : ");
        sb.append(this.getPostCode());
        sb.append("\nUID : ");
        sb.append(this.getUid());

        return sb.toString();
    }
}
