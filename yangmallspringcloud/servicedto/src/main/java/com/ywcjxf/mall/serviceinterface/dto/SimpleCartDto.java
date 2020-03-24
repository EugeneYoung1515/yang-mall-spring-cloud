package com.ywcjxf.mall.serviceinterface.dto;

import com.ywcjxf.mall.pojo.User;

import java.util.Date;
import java.util.Map;

public class SimpleCartDto {
    private Integer userId;
    private Integer goodsId;
    private Integer quantity;

    private Map<Integer,Integer> map;//或者用这个来放所有的规格及相应的数量

    private String identifier;
    //1.uuid
    //2.订单是否过期 有没有新订单提交
    //3.订单id
    //4.订单状态

    private User user;
    private Boolean isOrder = false;//是扣减库存用于下订单 不是用于用于返库存
    private Date createDate;

    private Long orderId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getOrder() {
        return isOrder;
    }

    public void setOrder(Boolean order) {
        isOrder = order;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
