package com.ywcjxf.mall.serviceinterface.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ywcjxf.mall.mapper.PairDtoMapper;
import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.CartService;
import com.ywcjxf.mall.serviceinterface.CartService1;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceFeignDecorator implements CartService {
    private CartService1 cartService1;
    private PairDtoMapper pairDtoMapper;

    @Override
    public PairDto<Boolean, Integer, String> addItemToRedis(User user, boolean flag, String cookieValue, Integer specId, Integer quantity) {
        return pairDtoMapper.fromMap(cartService1.addItemToRedis(user,flag,cookieValue,specId,quantity),Boolean.class,Integer.class,String.class);
    }

    @Override
    public boolean cookieToRedis(User user, String cookieValue, boolean exceptionStatus) {
        return cartService1.cookieToRedis(user,cookieValue,exceptionStatus);
    }

    @Override
    public PairDto<String, Integer, String> addItemToCookie(boolean flag, String cookieValue, Integer specId, Integer quantity) {
        return pairDtoMapper.fromMap(cartService1.addItemToCookie(flag,cookieValue,specId,quantity),String.class,Integer.class,String.class);
    }

    @Override
    public Spec getSpec(Integer specId) {
        return cartService1.getSpec(specId);
    }

    @Override
    public int showCartGoodsNumFromRedis(User user) {
        return cartService1.showCartGoodsNumFromRedis(user.getUserId());
    }

    @Override
    public int showCartGoodsNumFromCookie(String cookieValue) {
        return cartService1.showCartGoodsNumFromCookie(cookieValue);
    }

    @Override
    public PairDto<Map<Spec, Integer>, Map<Integer, String>, Object> showSpecWithItem(User user) {
        return pairDtoMapper.fromMap(cartService1.showSpecWithItem(user.getUserId()),new TypeReference<Map<Spec, Integer>>(){},
                new TypeReference<Map<Integer, String>>(){},Object.class);
    }

    @Override
    public PairDto<Spec, String, String> changeQuantity(User user, Integer specId, Integer quantity) {
        return pairDtoMapper.fromMap(cartService1.changeQuantity(user,specId,quantity),Spec.class,String.class,String.class);
    }

    @Override
    public Integer delSpec(User user, Integer specId) {
        return cartService1.delSpec(user,specId);
    }

    @Override
    public Map<Integer, String> checkQuantityBatch(List<Integer> specIds, Map<Integer, Integer> map) {
        return cartService1.checkQuantityBatch(specIds,map);
    }

    @Autowired
    public void setCartService1(CartService1 cartService1) {
        this.cartService1 = cartService1;
    }

    @Autowired
    public void setPairDtoMapper(PairDtoMapper pairDtoMapper) {
        this.pairDtoMapper = pairDtoMapper;
    }
}
