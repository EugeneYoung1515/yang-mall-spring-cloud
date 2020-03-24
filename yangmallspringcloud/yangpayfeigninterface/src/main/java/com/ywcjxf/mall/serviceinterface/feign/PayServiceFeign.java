package com.ywcjxf.mall.serviceinterface.feign;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.PayService1;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("pay-service")
public interface PayServiceFeign extends PayService1 {

    @Override
    @GetMapping("/orderIdAndAmount")
    Order orderIdAndAmount(@RequestParam("userId") Integer userId);

    @Override
    @GetMapping("/showCode")
    Map<String,String> showCode(@RequestParam("userId") Integer userId, @RequestParam("method") String method, @RequestParam("orderId") Long orderId);

    @Override
    @PostMapping("/payNotify")
    void payNotify(@RequestParam("aoId") String aoId, @RequestParam("orderId") Long orderId, @RequestParam("payPrice") String payPrice, @RequestParam("payTime") String payTime, @RequestParam("detail") String detail, @RequestParam("more") String more);

    @Override
    @GetMapping("paySuccessIsTrue")
    boolean paySuccessIsTrue(@RequestParam("aoId") String aoId, @RequestParam("userId") Integer userId);
}
