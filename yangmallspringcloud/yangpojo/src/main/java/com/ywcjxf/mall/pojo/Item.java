package com.ywcjxf.mall.pojo;

import com.ywcjxf.mall.parpojo.ItemPar;

import java.math.BigDecimal;
import java.util.Date;

public class Item extends ItemPar{
    private Integer itemId;

    private String title;

    private String subtitle;

    private String note;

    private String imageUrl;

    private String likeImageUrl;

    private Integer firstDistrictId;

    private String firstDistrictName;

    private Integer secondDistrictId;

    private String secondDistrictName;

    private Integer categoryId;

    private String categoryName;

    private Integer specId;

    private String specName;

    private BigDecimal price;

    private BigDecimal salePrice;

    private BigDecimal reprensentPrice;

    private String yunfeiCost;

    private Integer sales;

    private Integer nowInventory;

    private Integer originalInventory;

    private Integer totalSales;

    private String parameters;

    private String detailImageLinks;

    private Integer statusCode;

    private String statusName;

    private Integer isDelete;

    private Date createDate;

    private Object tsvColumn;

    private Integer enterNum;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getLikeImageUrl() {
        return likeImageUrl;
    }

    public void setLikeImageUrl(String likeImageUrl) {
        this.likeImageUrl = likeImageUrl == null ? null : likeImageUrl.trim();
    }

    public Integer getFirstDistrictId() {
        return firstDistrictId;
    }

    public void setFirstDistrictId(Integer firstDistrictId) {
        this.firstDistrictId = firstDistrictId;
    }

    public String getFirstDistrictName() {
        return firstDistrictName;
    }

    public void setFirstDistrictName(String firstDistrictName) {
        this.firstDistrictName = firstDistrictName == null ? null : firstDistrictName.trim();
    }

    public Integer getSecondDistrictId() {
        return secondDistrictId;
    }

    public void setSecondDistrictId(Integer secondDistrictId) {
        this.secondDistrictId = secondDistrictId;
    }

    public String getSecondDistrictName() {
        return secondDistrictName;
    }

    public void setSecondDistrictName(String secondDistrictName) {
        this.secondDistrictName = secondDistrictName == null ? null : secondDistrictName.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

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

    public BigDecimal getReprensentPrice() {
        return reprensentPrice;
    }

    public void setReprensentPrice(BigDecimal reprensentPrice) {
        this.reprensentPrice = reprensentPrice;
    }

    public String getYunfeiCost() {
        return yunfeiCost;
    }

    public void setYunfeiCost(String yunfeiCost) {
        this.yunfeiCost = yunfeiCost == null ? null : yunfeiCost.trim();
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

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters == null ? null : parameters.trim();
    }

    public String getDetailImageLinks() {
        return detailImageLinks;
    }

    public void setDetailImageLinks(String detailImageLinks) {
        this.detailImageLinks = detailImageLinks == null ? null : detailImageLinks.trim();
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Object getTsvColumn() {
        return tsvColumn;
    }

    public void setTsvColumn(Object tsvColumn) {
        this.tsvColumn = tsvColumn;
    }

    public Integer getEnterNum() {
        return enterNum;
    }

    public void setEnterNum(Integer enterNum) {
        this.enterNum = enterNum;
    }
}