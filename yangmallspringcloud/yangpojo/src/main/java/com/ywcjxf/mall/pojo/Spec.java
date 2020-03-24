package com.ywcjxf.mall.pojo;

import com.ywcjxf.mall.parpojo.SpecPar;

import java.math.BigDecimal;

public class Spec extends SpecPar{
    private Integer specId;

    private String specName;

    private BigDecimal price;

    private BigDecimal salePrice;

    private BigDecimal representPrice;

    private Integer sales;

    private Integer nowInventory;

    private Integer originalInventory;

    private Integer statusCode;

    private String statusName;

    private Integer itemId;

    private String specTitle;

    private String yunfeiCost;

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getNowInventory() {
        return nowInventory;
    }

    public void setNowInventory(Integer nowInventory) {
        this.nowInventory = nowInventory;
    }

    public Integer getOriginalInventory() {
        return originalInventory;
    }

    public void setOriginalInventory(Integer originalInventory) {
        this.originalInventory = originalInventory;
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

    public String getSpecTitle() {
        return specTitle;
    }

    public void setSpecTitle(String specTitle) {
        this.specTitle = specTitle == null ? null : specTitle.trim();
    }

    public String getYunfeiCost() {
        return yunfeiCost;
    }

    public void setYunfeiCost(String yunfeiCost) {
        this.yunfeiCost = yunfeiCost == null ? null : yunfeiCost.trim();
    }
}