package com.ywcjxf.mall.pojo;

import com.ywcjxf.mall.parpojo.OrderItemSpecPar;

import java.math.BigDecimal;

public class OrderItemSpec extends OrderItemSpecPar{
    private Integer specId;

    private Long orderId;

    private String specTitle;

    private String specName;

    private BigDecimal salePrice;

    private BigDecimal representPrice;

    private Integer quantity;

    private Integer statusCode;

    private String statusName;

    private Integer itemId;

    private String yunfeiCost;

    public OrderItemSpec(){}

    public OrderItemSpec(Integer specId, Long orderId, String specTitle, String specName, BigDecimal salePrice, BigDecimal representPrice, Integer quantity, Integer itemId) {
        this.specId = specId;
        this.orderId = orderId;
        this.specTitle = specTitle;
        this.specName = specName;
        this.salePrice = salePrice;
        this.representPrice = representPrice;
        this.quantity = quantity;
        this.itemId = itemId;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSpecTitle() {
        return specTitle;
    }

    public void setSpecTitle(String specTitle) {
        this.specTitle = specTitle == null ? null : specTitle.trim();
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getRepresentPrice() {
        return representPrice;
    }

    public void setRepresentPrice(BigDecimal representPrice) {
        this.representPrice = representPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getYunfeiCost() {
        return yunfeiCost;
    }

    public void setYunfeiCost(String yunfeiCost) {
        this.yunfeiCost = yunfeiCost == null ? null : yunfeiCost.trim();
    }
}