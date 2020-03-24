package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.pojo.Spec;

import java.util.List;

public interface ItemService {

    Item getItemById(Integer itemId);
    void insertItemImage(Integer itemId,String imageLink);
    Integer getItemIdByNullOrderByIdLimitOne();
    Spec getItemFirstSpec(Integer itemId);
    List<Spec> getAllSpecByItemId(Integer itemId);
    Integer getTotalSalesByItemId(Integer itemId);
}
