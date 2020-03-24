package com.ywcjxf.mall.parpojo;

import java.math.BigDecimal;

public class OrderItemSpecPar {
    //private BigDecimal eachSpecTotal;
    private String eachSpecTotal;

    private String imageUrl;
    private String yunfeiCost;

    /*
    public BigDecimal getEachSpecTotal() {
        return eachSpecTotal;
    }

    public void setEachSpecTotal(BigDecimal eachSpecTotal) {
        this.eachSpecTotal = eachSpecTotal;
    }
    */

    public String getEachSpecTotal() {
        return eachSpecTotal;
    }

    public void setEachSpecTotal(String eachSpecTotal) {
        this.eachSpecTotal = eachSpecTotal;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getYunfeiCost() {
        return yunfeiCost;
    }

    public void setYunfeiCost(String yunfeiCost) {
        this.yunfeiCost = yunfeiCost;
    }
}
