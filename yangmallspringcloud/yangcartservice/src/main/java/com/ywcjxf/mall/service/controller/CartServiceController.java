package com.ywcjxf.mall.service.controller;

import com.ywcjxf.mall.mapper.PairDtoMapper;
import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.CartService;
import com.ywcjxf.mall.serviceinterface.CartService1;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CartServiceController{
    private CartService cartService;
    private PairDtoMapper pairDtoMapper;

    @RequestMapping("/addItemToRedis")
    public Map<String,String> addItemToRedis(@RequestBody User user, boolean flag, String cookieValue, Integer specId, Integer quantity) {
        PairDto<Boolean,Integer,String> pairDto= cartService.addItemToRedis(user,flag,cookieValue,specId,quantity);
        return pairDtoMapper.toMap(pairDto);
    }

    @RequestMapping("/cookieToRedis")
    public boolean cookieToRedis(@RequestBody User user, String cookieValue, boolean exceptionStatus) {
        return cartService.cookieToRedis(user,cookieValue,exceptionStatus);
    }

    @RequestMapping("/addItemToCookie")
    public Map<String,String> addItemToCookie(boolean flag, String cookieValue, Integer specId, Integer quantity) {
        PairDto<String,Integer,String> pairDto = cartService.addItemToCookie(flag,cookieValue,specId,quantity);
        return pairDtoMapper.toMap(pairDto);
    }

    @RequestMapping("/getSpec")
    public Spec getSpec(Integer specId) {
        return cartService.getSpec(specId);
    }

    @RequestMapping("/showCartGoodsNumFromRedis")
    public int showCartGoodsNumFromRedis(User user) {
        return cartService.showCartGoodsNumFromRedis(user);
    }

    @RequestMapping("/showCartGoodsNumFromCookie")
    public int showCartGoodsNumFromCookie(String cookieValue) {
        return cartService.showCartGoodsNumFromCookie(cookieValue);
    }

    @RequestMapping("/showSpecWithItem")
    public Map<String,String> showSpecWithItem(User user) {
        PairDto<Map<Spec, Integer>, Map<Integer, String>, Object> pairDto = cartService.showSpecWithItem(user);
        return pairDtoMapper.toMap(pairDto);
    }

    @RequestMapping("/changeQuantity")
    public Map<String,String> changeQuantity(@RequestBody User user, Integer specId, Integer quantity) {
        PairDto<Spec, String, String> pairDto = cartService.changeQuantity(user,specId,quantity);
        return pairDtoMapper.toMap(pairDto);
    }

    @RequestMapping("/delSpec")
    public Integer delSpec(@RequestBody User user, Integer specId) {
        return cartService.delSpec(user,specId);
    }

    @RequestMapping("/checkQuantityBatch")
    public Map<Integer, String> checkQuantityBatch(List<Integer> specIds, Map<Integer, Integer> map) {
        return cartService.checkQuantityBatch(specIds,map);
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    public void setPairDtoMapper(PairDtoMapper pairDtoMapper) {
        this.pairDtoMapper = pairDtoMapper;
    }
}
