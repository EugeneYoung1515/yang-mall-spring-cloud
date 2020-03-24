package com.ywcjxf.mall.serviceinterface.feign;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.OrderItemSpec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.OrderService1;
import com.ywcjxf.mall.serviceinterface.dto.ItemQuantityAndUser;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("order-service")
public interface OrderServiceFeign extends OrderService1{

    @Override
    @PostMapping("/decrInventoryAndSend")//
    Map<Integer, String> decrInventoryAndSend(@RequestBody ItemQuantityAndUser itemQuantityAndUser);

    @Override
    @GetMapping("/test")
    void test();

    @Override
    @GetMapping("/getOrder")//
    Map<String,String> getOrder(@RequestParam("userId") Integer userId);

    @Override
    @PostMapping("/insert")
    void insert(@RequestBody List<List<String>> lists, @RequestParam("id") Integer id);

    @Override
    @PostMapping("/validateAddressAndInsert")
    Integer validateAddressAndInsert(@RequestParam("trueName") String trueName, @RequestParam("cityId") String cityId, @RequestParam("areaId") String areaId, @RequestParam("areaInfo") String areaInfo, @RequestParam("address") String address, @RequestParam("mobPhone") String mobPhone);

    @Override
    @PostMapping("/submitOrder")//
    Map<Integer, String> submitOrder(@RequestBody ItemQuantityAndUser itemQuantityAndUser, @RequestParam("addressId") Integer addressId, @RequestParam("token") String token, @RequestParam("orderId") String orderId);
}
