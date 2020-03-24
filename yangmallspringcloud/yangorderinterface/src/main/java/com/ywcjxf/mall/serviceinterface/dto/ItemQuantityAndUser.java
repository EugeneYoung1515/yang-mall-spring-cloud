package com.ywcjxf.mall.serviceinterface.dto;

import com.ywcjxf.mall.pojo.User;

import java.util.Map;

public class ItemQuantityAndUser {
    private Map<Integer,Integer> ItemQuantity;
    private User user;

    public ItemQuantityAndUser(Map<Integer, Integer> itemQuantity, User user) {
        ItemQuantity = itemQuantity;
        this.user = user;
    }

    public ItemQuantityAndUser() {
    }

    public Map<Integer, Integer> getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(Map<Integer, Integer> itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
