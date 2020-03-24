package com.ywcjxf.mall.service.controller;

import com.ywcjxf.mall.mapper.PairDtoMapper;
import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.PayService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PayServiceController{
    private PayService payService;

    private PairDtoMapper pairDtoMapper;

    @RequestMapping("/orderIdAndAmount")
    public Order orderIdAndAmount(User user) {
        return payService.orderIdAndAmount(user);
    }

    @RequestMapping("/showCode")
    public Map<String,String> showCode(User user, String method, Long orderId) {
        return pairDtoMapper.toMap(payService.showCode(user,method,orderId));
    }

    @RequestMapping("/payNotify")
    public Object payNotify(String aoId, Long orderId, String payPrice, String payTime, String detail, String more) {
        payService.payNotify(aoId,orderId,payPrice,payTime,detail,more);
        return null;
    }

    @RequestMapping("/paySuccessIsTrue")
    public boolean paySuccessIsTrue(String aoId, User user) {
        return payService.paySuccessIsTrue(aoId,user);
    }

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @Autowired
    public void setPairDtoMapper(PairDtoMapper pairDtoMapper) {
        this.pairDtoMapper = pairDtoMapper;
    }
}
