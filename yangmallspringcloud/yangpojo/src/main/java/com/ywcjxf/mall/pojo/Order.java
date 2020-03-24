package com.ywcjxf.mall.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Long orderId;

    private Integer userId;

    private String userName;

    private Date createDate;

    private BigDecimal payTotal;

    private BigDecimal itemTotal;

    private BigDecimal yunfeiCost;

    private Date payDate;

    private String statusName;

    private Integer statusCode;

    private String payMethod;

    private String payPlatformNumber;

    private Date waitPayDate;

    public Order(){}

    private Order(Builder builder) {
        setOrderId(builder.orderId);
        setUserId(builder.userId);
        setUserName(builder.userName);
        setCreateDate(builder.createDate);
        setPayTotal(builder.payTotal);
        setItemTotal(builder.itemTotal);
        setYunfeiCost(builder.yunfeiCost);
        setPayDate(builder.payDate);
        setStatusName(builder.statusName);
        setStatusCode(builder.statusCode);
        setPayMethod(builder.payMethod);
        setPayPlatformNumber(builder.payPlatformNumber);
        setWaitPayDate(builder.waitPayDate);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(BigDecimal payTotal) {
        this.payTotal = payTotal;
    }

    public BigDecimal getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(BigDecimal itemTotal) {
        this.itemTotal = itemTotal;
    }

    public BigDecimal getYunfeiCost() {
        return yunfeiCost;
    }

    public void setYunfeiCost(BigDecimal yunfeiCost) {
        this.yunfeiCost = yunfeiCost;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public String getPayPlatformNumber() {
        return payPlatformNumber;
    }

    public void setPayPlatformNumber(String payPlatformNumber) {
        this.payPlatformNumber = payPlatformNumber;
    }

    public Date getWaitPayDate() {
        return waitPayDate;
    }

    public void setWaitPayDate(Date waitPayDate) {
        this.waitPayDate = waitPayDate;
    }


    public static final class Builder {
        private Long orderId;
        private Integer userId;
        private String userName;
        private Date createDate;
        private BigDecimal payTotal;
        private BigDecimal itemTotal;
        private BigDecimal yunfeiCost;
        private Date payDate;
        private String statusName;
        private Integer statusCode;
        private String payMethod;
        private String payPlatformNumber;
        private Date waitPayDate;

        private Builder() {
        }

        public Builder orderId(Long val) {
            orderId = val;
            return this;
        }

        public Builder userId(Integer val) {
            userId = val;
            return this;
        }

        public Builder userName(String val) {
            userName = val;
            return this;
        }

        public Builder createDate(Date val) {
            createDate = val;
            return this;
        }

        public Builder payTotal(BigDecimal val) {
            payTotal = val;
            return this;
        }

        public Builder itemTotal(BigDecimal val) {
            itemTotal = val;
            return this;
        }

        public Builder yunfeiCost(BigDecimal val) {
            yunfeiCost = val;
            return this;
        }

        public Builder payDate(Date val) {
            payDate = val;
            return this;
        }

        public Builder statusName(String val) {
            statusName = val;
            return this;
        }

        public Builder statusCode(Integer val) {
            statusCode = val;
            return this;
        }

        public Builder payMethod(String val) {
            payMethod = val;
            return this;
        }

        public Builder payPlatformNumber(String val) {
            payPlatformNumber = val;
            return this;
        }

        public Builder waitPayDate(Date val) {
            waitPayDate = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}