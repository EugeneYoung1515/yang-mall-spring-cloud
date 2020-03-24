package com.ywcjxf.mall.service.controller;

import com.ywcjxf.mall.mapper.PairDtoMapper;
import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.OrderItemSpec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.dto.ItemQuantityAndUser;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrderServiceController{
    private OrderService orderService;

    private PairDtoMapper pairDtoMapper;

    @RequestMapping("/decrInventoryAndSend")
    public Map<Integer, String> decrInventoryAndSend(@RequestBody ItemQuantityAndUser itemQuantityAndUser) {
        return orderService.decrInventoryAndSend(itemQuantityAndUser.getItemQuantity(),itemQuantityAndUser.getUser());
    }

    @RequestMapping("/test")
    public Object test() {
        orderService.test();
        return null;
    }

    @RequestMapping("/getOrder")
    public Map<String,String> getOrder(User user) {
        return pairDtoMapper.toMap(orderService.getOrder(user));
    }

    @RequestMapping("/insert")
    public Object insert(@RequestBody List<List<String>> lists, Integer id) {
        orderService.insert(lists,id);
        return null;
    }

    @RequestMapping("/validateAddressAndInsert")
    public Integer validateAddressAndInsert(String trueName, String cityId, String areaId, String areaInfo, String address, String mobPhone) {
        return orderService.validateAddressAndInsert(trueName,cityId,areaId,areaInfo,address,mobPhone);
    }

    @RequestMapping("/submitOrder")
    public Map<Integer, String> submitOrder(@RequestBody ItemQuantityAndUser itemQuantityAndUser, Integer addressId, String token, String orderId) {
        return orderService.submitOrder(itemQuantityAndUser.getItemQuantity(),itemQuantityAndUser.getUser(),addressId,token,orderId);
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setPairDtoMapper(PairDtoMapper pairDtoMapper) {
        this.pairDtoMapper = pairDtoMapper;
    }
}
