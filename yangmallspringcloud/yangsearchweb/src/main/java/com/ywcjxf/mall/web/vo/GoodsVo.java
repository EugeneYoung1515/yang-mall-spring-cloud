package com.ywcjxf.mall.web.vo;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;

import java.math.BigDecimal;

@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class GoodsVo {
    private String goodsImage;
    private BigDecimal representPrice;
    private Integer goodsId;
    private String jd;
    private String goodsTitle;

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public BigDecimal getRepresentPrice() {
        return representPrice;
    }

    public void setRepresentPrice(BigDecimal representPrice) {
        this.representPrice = representPrice;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }
}
