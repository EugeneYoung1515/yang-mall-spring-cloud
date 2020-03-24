package com.ywcjxf.mall.service.controller;

import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.serviceinterface.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemServiceController {

    private ItemService itemService;

    @RequestMapping("/getItemById")
    public Item getItemById(Integer itemId) {
        return itemService.getItemById(itemId);
    }

    @RequestMapping("/insertItemImage")
    public Object insertItemImage(Integer itemId, String imageLink) {
        itemService.insertItemImage(itemId,imageLink);
        return null;
    }

   @RequestMapping("/getItemIdByNullOrderByIdLimitOne")
    public Integer getItemIdByNullOrderByIdLimitOne() {
        return itemService.getItemIdByNullOrderByIdLimitOne();
    }

    @RequestMapping("/getItemFirstSpec")
    public Spec getItemFirstSpec(Integer itemId) {
        return itemService.getItemFirstSpec(itemId);
    }

    @RequestMapping("/getAllSpecByItemId")
    public List<Spec> getAllSpecByItemId(Integer itemId) {
        return itemService.getAllSpecByItemId(itemId);
    }

    @RequestMapping("/getTotalSalesByItemId")
    public Integer getTotalSalesByItemId(Integer itemId) {
        return itemService.getTotalSalesByItemId(itemId);
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}
