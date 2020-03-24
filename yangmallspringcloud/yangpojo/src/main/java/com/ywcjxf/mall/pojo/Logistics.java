package com.ywcjxf.mall.pojo;

import java.util.Date;

public class Logistics {
    private String name;

    private Long orderId;

    private Date createDate;

    private String province;

    private String city;

    private String district;

    private String address;

    private String sendNumber;

    private Integer sendStatusCode;

    private String sendStatusName;

    private String yunfeiCost;

    private Date sendDate;

    private Date receiveDate;

    private String addressInfo;

    private String phoneNumber;

    private Integer userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(String sendNumber) {
        this.sendNumber = sendNumber == null ? null : sendNumber.trim();
    }

    public Integer getSendStatusCode() {
        return sendStatusCode;
    }

    public void setSendStatusCode(Integer sendStatusCode) {
        this.sendStatusCode = sendStatusCode;
    }

    public String getSendStatusName() {
        return sendStatusName;
    }

    public void setSendStatusName(String sendStatusName) {
        this.sendStatusName = sendStatusName == null ? null : sendStatusName.trim();
    }

    public String getYunfeiCost() {
        return yunfeiCost;
    }

    public void setYunfeiCost(String yunfeiCost) {
        this.yunfeiCost = yunfeiCost == null ? null : yunfeiCost.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo == null ? null : addressInfo.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}