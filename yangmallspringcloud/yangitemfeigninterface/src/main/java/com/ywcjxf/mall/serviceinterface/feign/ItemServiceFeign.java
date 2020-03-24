package com.ywcjxf.mall.serviceinterface.feign;

import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.serviceinterface.ItemService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface ItemServiceFeign extends ItemService {

    @Override
    @GetMapping("/getItemById")
    Item getItemById(@RequestParam("itemId") Integer itemId);

    @Override
    @PostMapping("/insertItemImage")
    void insertItemImage(@RequestParam("itemId") Integer itemId, @RequestParam("imageLink")String imageLink);

    @Override
    @GetMapping("/getItemIdByNullOrderByIdLimitOne")
    Integer getItemIdByNullOrderByIdLimitOne();

    @Override
    @GetMapping("/getItemFirstSpec")
    Spec getItemFirstSpec(@RequestParam("itemId") Integer itemId);

    @Override
    @GetMapping("/getAllSpecByItemId")
    List<Spec> getAllSpecByItemId(@RequestParam("itemId")Integer itemId);

    @Override
    @GetMapping("/getTotalSalesByItemId")
    Integer getTotalSalesByItemId(@RequestParam("itemId")Integer itemId);
}
