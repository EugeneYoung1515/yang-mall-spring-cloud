package com.ywcjxf.mall.web.vo;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;

import java.math.BigDecimal;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class GoodsVo2 {
    //private Integer goodsId;
    private String goodsId;
    private String goodsName;
    private BigDecimal goodsPrice;
    //private BigDecimal representPrice;

    private String representPrice;
    private Integer goodsStorage;
    private Integer specIndex;
    private String specValue;

    /*
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    */

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /*
    public BigDecimal getRepresentPrice() {
        return representPrice;
    }

    public void setRepresentPrice(BigDecimal representPrice) {
        this.representPrice = representPrice;
    }
    */

    public Integer getGoodsStorage() {
        return goodsStorage;
    }

    public void setGoodsStorage(Integer goodsStorage) {
        this.goodsStorage = goodsStorage;
    }

    public Integer getSpecIndex() {
        return specIndex;
    }

    public void setSpecIndex(Integer specIndex) {
        this.specIndex = specIndex;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    public String getRepresentPrice() {
        return representPrice;
    }

    public void setRepresentPrice(String representPrice) {
        this.representPrice = representPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
