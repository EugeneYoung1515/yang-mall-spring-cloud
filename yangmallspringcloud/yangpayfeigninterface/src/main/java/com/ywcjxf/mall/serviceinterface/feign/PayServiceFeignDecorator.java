package com.ywcjxf.mall.serviceinterface.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ywcjxf.mall.mapper.PairDtoMapper;
import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.PayService;
import com.ywcjxf.mall.serviceinterface.PayService1;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceFeignDecorator implements PayService{

    private PayService1 payService1;

    private PairDtoMapper pairDtoMapper;

    @Override
    public Order orderIdAndAmount(User user) {
        return payService1.orderIdAndAmount(user.getUserId());
    }

    @Override
    public PairDto<Order, PairDto<String, String, String>, Object> showCode(User user, String method, Long orderId) {
        return pairDtoMapper.fromMap(payService1.showCode(user.getUserId(),method,orderId),Order.class,new TypeReference<PairDto<String, String, String>>(){},
        Object.class);
    }

    @Override
    public void payNotify(String aoId, Long orderId, String payPrice, String payTime, String detail, String more) {
        payService1.payNotify(aoId,orderId,payPrice,payTime,detail,more);
    }

    @Override
    public boolean paySuccessIsTrue(String aoId, User user) {
        return payService1.paySuccessIsTrue(aoId,user.getUserId());
    }

    @Autowired
    public void setPayService1(PayService1 payService1) {
        this.payService1 = payService1;
    }

    @Autowired
    public void setPairDtoMapper(PairDtoMapper pairDtoMapper) {
        this.pairDtoMapper = pairDtoMapper;
    }
}
