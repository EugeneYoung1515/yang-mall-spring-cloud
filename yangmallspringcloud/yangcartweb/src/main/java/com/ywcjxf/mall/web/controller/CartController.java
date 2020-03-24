package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.CartService;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import com.ywcjxf.mall.web.filter.RedisSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.ywcjxf.mall.constant.Cart.CART_COOKIE_NAME;
import static com.ywcjxf.mall.constant.Cart.THIRTY_DAY_AS_SECONDS;
import static com.ywcjxf.mall.constant.Domain.COOKIE_DOMAIN_CART_SITE;
import static com.ywcjxf.mall.constant.Domain.ITEM_SITE;
import static com.ywcjxf.mall.constant.Domain.REDIRECT_CART_TO_ORDER;
import static com.ywcjxf.mall.constant.RequestToken.REQ_TOKEN;
import static com.ywcjxf.mall.constant.Session.*;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.HASH_MAP_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.DefaultCharset.UTF_8;

@Controller
public class CartController {
    private CartService cartService;
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @RequestMapping("/addcart")
    @ResponseBody
    @CrossOrigin(origins = {ITEM_SITE},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT},allowedHeaders = {"X-Requested-With","Content-Type","Accept"},allowCredentials = "true")
    public Map<String,Object> addCart(HttpServletRequest request, @RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession,
                                      Integer goods_id, Integer quantity,
                                      HttpServletResponse response){
        //说是goods_id 其实是 规格id

        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        map.put("state","true");

        //校验库存？
        /*
        Spec spec = cartService.getSpec(goods_id);
        if(spec.getNowInventory()<quantity){
            map.put("state","false");
            map.put()
        }
        */

        User user = (User) redisSession.getAttribute(USER);
        Cookie cookie = getCookie(request,CART_COOKIE_NAME);

        boolean flag = false;

        String cookieValue = null;
        if(cookie!=null) {
            cookieValue =cookie.getValue();
        }
        if(cookieValue!=null && !cookieValue.isEmpty()) {
            flag = true;
        }

        if(user!=null){//登录了
            PairDto<Boolean,Integer,String> pairDto = cartService.addItemToRedis(user,flag,cookieValue,goods_id,quantity);
            if(pairDto.getFirst()){
                /*
                cookie.setValue(null);
                cookie.setMaxAge(0);

                cookie.setPath("/");
                cookie.setDomain("cart.yangmall.com");

                response.addCookie(cookie);
                */

                removeCookie(cookie,response);
            }

            map.put("num",pairDto.getSecond());
            map.put("amount",pairDto.getThird());

            redisSession.setMaxInactiveInterval(ONE_DAY_AS_SECONDS);
        }else{//没登录
            PairDto<String,Integer,String> pairDto = cartService.addItemToCookie(flag,cookieValue,goods_id,quantity);//拿到要放到cookie里json字符串
            String json = pairDto.getFirst();
            if(flag){//有cookie 且cookie里有值
                if(json!=null) {
                    try {
                        cookie.setValue(URLEncoder.encode(json, UTF_8));
                        cookie.setMaxAge(THIRTY_DAY_AS_SECONDS);

                        cookie.setPath("/");
                        cookie.setDomain(COOKIE_DOMAIN_CART_SITE);

                        response.addCookie(cookie);
                    }catch (IOException ex){
                        //ex.printStackTrace();

                        logger.error("url encode"+"_"+ex.getMessage(),ex);
                    }
                }
            }else{
                if(json!=null) {
                    try {
                        cookie = new Cookie(CART_COOKIE_NAME, URLEncoder.encode(json, UTF_8));
                        cookie.setDomain(COOKIE_DOMAIN_CART_SITE);
                        cookie.setPath("/");
                        cookie.setMaxAge(THIRTY_DAY_AS_SECONDS);
                        //和上面的区别 就是多了设置cookie的名字

                        //System.out.println(cookie.getValue());
                        logger.debug(cookie.getValue());
                        response.addCookie(cookie);
                    }catch (IOException ex){
                        //ex.printStackTrace();
                        logger.error("url encode"+"_"+ex.getMessage(),ex);
                    }
                }
            }

            map.put("num",pairDto.getSecond());
            map.put("amount",pairDto.getThird());

            redisSession.setMaxInactiveInterval(FIVE_MIN_AS_SECONDS);
        }



        return map;
    }

    private Cookie getCookie(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie;
                }
            }
        }
        return null;
    }

    @RequestMapping("/goods_num")
    @ResponseBody
    @CrossOrigin(origins = {ITEM_SITE},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT},allowedHeaders = {"X-Requested-With","Content-Type","Accept"},allowCredentials = "true")
    public Map<String,Object> showCartGoodsNum(HttpServletRequest request,
                                               @RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession,
                                               HttpServletResponse response){
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);

        User user = (User) redisSession.getAttribute(USER);
        if(user!=null){
            map.put("cart_goods_num",cartService.showCartGoodsNumFromRedis(user));

            redisSession.setMaxInactiveInterval(ONE_DAY_AS_SECONDS);
        }else{
            /*
            Cookie cookie = getCookie(request,"cart");
            String cookieValue = null;
            if(cookie!=null) {
                cookieValue =cookie.getValue();
            }

            int goodsNum = 0;
            if(cookieValue!=null && !cookieValue.isEmpty()) {
                goodsNum = cartService.showCartGoodsNumFromCookie(cookieValue);
            }
            */
            int goodsNum = 0;
            PairDto<Boolean,String,Cookie> pairDto = validCookie(request,CART_COOKIE_NAME);
            if(pairDto.getFirst()){
                goodsNum = cartService.showCartGoodsNumFromCookie(pairDto.getSecond());
            }

            map.put("cart_goods_num",goodsNum);

            redisSession.setMaxInactiveInterval(FIVE_MIN_AS_SECONDS);
        }
        return map;
    }

    private PairDto<Boolean,String,Cookie> validCookie(HttpServletRequest request,String cookieName){//可用 有效的cookie
        Cookie cookie = getCookie(request,cookieName);

        boolean flag = false;

        String cookieValue = null;
        if(cookie!=null) {
            cookieValue =cookie.getValue();
        }
        if(cookieValue!=null && !cookieValue.isEmpty()) {
            flag = true;
        }

        PairDto<Boolean,String,Cookie> pairDto = new PairDto<>();
        pairDto.setFirst(flag);
        pairDto.setSecond(cookieValue);
        pairDto.setThird(cookie);

        return pairDto;
    }

    private void removeCookie(Cookie cookie,HttpServletResponse response){
        cookie.setValue(null);
        cookie.setMaxAge(0);

        cookie.setPath("/");
        cookie.setDomain(COOKIE_DOMAIN_CART_SITE);

        response.addCookie(cookie);
    }

    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request,
                           @RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession,
                           HttpServletResponse response, ModelMap map){

        User user = (User) redisSession.getAttribute(USER);
        //if(user!=null){
            redisSession.setMaxInactiveInterval(ONE_DAY_AS_SECONDS);

            Map<Integer,String> quantityInfo = (Map<Integer,String>)redisSession.getAttribute("quantityInfo");//看下面的方法
            if(quantityInfo!=null){
                //System.out.println(quantityInfo);

                logger.info(quantityInfo.toString());
                map.addAttribute("quantityInfo",quantityInfo);
                redisSession.removeAttribute("quantityInfo");
            }

            map.addAttribute("selectInfo",(String)redisSession.getAttribute("selectInfo"));
            redisSession.removeAttribute("selectInfo");

            PairDto<Boolean,String,Cookie> pairDto = validCookie(request,CART_COOKIE_NAME);
            if(pairDto.getFirst()){
                if(cartService.cookieToRedis(user,pairDto.getSecond(),false)){
                    removeCookie(pairDto.getThird(),response);
                }
            }
            //把cookie中的购物车移到redis中

            PairDto<Map<Spec,Integer>,Map<Integer,String>,Object> pairDto1 = cartService.showSpecWithItem(user);
            map.addAttribute("specAndNum",pairDto1.getFirst());
            map.addAttribute("specIdAndAmount",pairDto1.getSecond());

            String uuid = UUID.randomUUID().toString();
            redisSession.setAttribute(REQ_TOKEN,uuid);
            map.addAttribute(REQ_TOKEN,uuid);

            return "m_cart";//视图逻辑名;

        //}else{
            //redisSession.setMaxInactiveInterval(300);

            //return "redirect:http://user.yangmall.com/login_register";
        //}
    }

    @RequestMapping("/changequantity")
    @ResponseBody
    public Map<String,Object> changeQuantity(Integer cart_id,Integer quantity,@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        User user = (User) redisSession.getAttribute(USER);
        if(user!=null) {
            redisSession.setMaxInactiveInterval(ONE_DAY_AS_SECONDS);

            PairDto<Spec, String, String> pairDto = cartService.changeQuantity(user, cart_id, quantity);

            map.put("state","true");

            String exceptionString = pairDto.getThird();
            if(exceptionString!=null){
                if(exceptionString.equals("invalid")){
                    map.put("state","invalid");
                    map.put("msg","商品已下架");
                    return map;
                }
                if(exceptionString.equals("shortage")){
                    map.put("state","shortage");
                    map.put("msg","库存不足");
                    map.put("goods_num",pairDto.getFirst().getNowInventory());
                }
            }


            map.put("represent_price",pairDto.getFirst().getRepresentPrice());
            map.put("subtotal",pairDto.getSecond());
        }else{
            redisSession.setMaxInactiveInterval(FIVE_MIN_AS_SECONDS);

            map.put("state","");
            map.put("msg","更新失败");
        }

        return map;
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> delGoods(Integer cart_id,@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){//实际上是删除购物车中的一个规格
        User user =(User)redisSession.getAttribute(USER);
        redisSession.setMaxInactiveInterval(ONE_DAY_AS_SECONDS);
        Integer quantity = cartService.delSpec(user,cart_id);//购物车中的商品数量
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        map.put("state",true);
        map.put("quantity",quantity);
        return map;
    }

    @RequestMapping(value = "/buy_step1",method = RequestMethod.POST)
    public String check(@RequestParam(value = "cart_id[]",required = false) ArrayList<String> cartIds, Integer ifcart, String reqToken,
                        @RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){//不能用RedirectAttributes redirectAttributes 因为 集群和分布式

        if(ifcart!=1){

        }

        String sessionReqToken = (String)redisSession.getAttribute(REQ_TOKEN);
        if(sessionReqToken==null||sessionReqToken.isEmpty()||reqToken==null||reqToken.isEmpty()|| !reqToken.equals(sessionReqToken)){
            //throw new RuntimeException("非法操作");

            return "redirect:/cart";
        }else{
            redisSession.removeAttribute(REQ_TOKEN);

            if(cartIds!=null && !cartIds.isEmpty()) {
                Map<Integer, Integer> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);//这个map可能要持久化到redis
                for (String s : cartIds) {
                    String[] a = s.split("\\|");
                    map.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
                }

                //Map<Integer,String> quantityInfo = cartService.checkQuantityBatch(new ArrayList<>(map.keySet()),map);

                User user = (User)redisSession.getAttribute(USER);

                Map<Integer,String> quantityInfo = orderService.decrInventoryAndSend(map,user);//这里没有判断quantityInfo是不是null
                if(!quantityInfo.isEmpty()){
                    //redirectAttributes.addFlashAttribute("quantityInfo",quantityInfo);

                    redisSession.setAttribute("quantityInfo",quantityInfo);
                    return "redirect:/cart";
                }else{

                    return "redirect:"+REDIRECT_CART_TO_ORDER;
                }
            }else{

                redisSession.setAttribute("selectInfo","请选择商品");
                return "redirect:/cart";
            }
        }
    }

    /*
    @RequestMapping("/test")
    @ResponseBody
    public String RabbitmqTest(){
        orderService.test();
        return "test";
    }
    */


    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
