package com.ywcjxf.mall.serviceinterface.feign;

import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.CartService;
import com.ywcjxf.mall.serviceinterface.CartService1;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("cart-service")
public interface CartServiceFeign extends CartService1 {

    @Override
    @PostMapping("/addItemToRedis")
    Map<String,String> addItemToRedis(@RequestBody User user, @RequestParam("flag") boolean flag, @RequestParam("cookieValue") String cookieValue, @RequestParam("specId") Integer specId, @RequestParam("quantity") Integer quantity);

    @Override
    @PostMapping("/cookieToRedis")
    boolean cookieToRedis(@RequestBody User user, @RequestParam("cookieValue") String cookieValue, @RequestParam("exceptionStatus") boolean exceptionStatus);

    @Override
    @PostMapping("/addItemToCookie")
    Map<String,String> addItemToCookie(@RequestParam("flag") boolean flag, @RequestParam("cookieValue") String cookieValue, @RequestParam("specId") Integer specId, @RequestParam("quantity") Integer quantity);

    @Override
    @GetMapping("/getSpec")
    Spec getSpec(@RequestParam("specId") Integer specId);

    @Override
    @GetMapping("/showCartGoodsNumFromRedis")
    int showCartGoodsNumFromRedis(@RequestParam("userId") Integer userId);

    @Override
    @GetMapping("/showCartGoodsNumFromCookie")
    int showCartGoodsNumFromCookie(@RequestParam("cookieValue") String cookieValue);

    @Override
    @GetMapping("/showSpecWithItem")
    Map<String,String> showSpecWithItem(@RequestParam("userId") Integer userId);

    @Override
    @PostMapping("/changeQuantity")
    Map<String,String> changeQuantity(@RequestBody User user, @RequestParam("specId") Integer specId, @RequestParam("quantity") Integer quantity);

    @Override
    @PostMapping("/delSpec")
    Integer delSpec(@RequestBody User user, @RequestParam("specId") Integer specId);

    @Override
    @PostMapping("/checkQuantityBatch")
    Map<Integer, String> checkQuantityBatch(@RequestParam("specIds") List<Integer> specIds, @RequestBody Map<Integer, Integer> map);
}
