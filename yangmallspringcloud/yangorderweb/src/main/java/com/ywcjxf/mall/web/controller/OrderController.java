package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.OrderItemSpec;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.serviceinterface.OrderService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import com.ywcjxf.mall.web.filter.RedisSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.ywcjxf.mall.constant.Domain.REDIRECT_ORDER_TO_PAY;
import static com.ywcjxf.mall.constant.RequestToken.REQ_TOKEN;
import static com.ywcjxf.mall.constant.Session.REDIS_SESSION;
import static com.ywcjxf.mall.constant.Session.USER;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.HASH_MAP_DEFAULT_INITIAL_CAPACITY;

@Controller
public class OrderController {
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("/order")
    public ModelAndView getOrder(@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){
        User user = (User)redisSession.getAttribute(USER);
        PairDto<Order,List<OrderItemSpec>,String> pairDto = orderService.getOrder(user);
        ModelAndView modelAndView= new ModelAndView("m_order");
        modelAndView.addObject("orderStat",pairDto.getFirst());
        modelAndView.addObject("orderList",pairDto.getSecond());

        String[] tokenAndOrderId = pairDto.getThird().split("@@@");
        modelAndView.addObject("token",tokenAndOrderId[0]);
        modelAndView.addObject("orderId",tokenAndOrderId[1]);

        String uuid = UUID.randomUUID().toString();
        redisSession.setAttribute(REQ_TOKEN,uuid);
        modelAndView.addObject(REQ_TOKEN,uuid);

        //System.out.println(pairDto.getFirst().getItemTotal()+" "+pairDto.getFirst().getYunfeiCost()+" "+pairDto.getFirst().getPayTotal());
        logger.info(pairDto.getFirst().getItemTotal()+" "+pairDto.getFirst().getYunfeiCost()+" "+pairDto.getFirst().getPayTotal());
        return modelAndView;
    }

    @RequestMapping("/loadaddress")
    public String loadAddress(){
        return "loadAddress";
    }

    @RequestMapping("/addaddress")
    public String addAddress(){//获得表单
        return "addAddress";
    }



    @RequestMapping("/submitaddaddress")
    @ResponseBody
    public Map<String,Object> submitAddAddress(String true_name,String city_id,String area_id,String area_info,
                                   String address,String mob_phone,Integer tel_phone){
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        map.put("state",true);
        if(true_name==null||true_name.isEmpty()){
            map.put("state",false);
            map.put("msg","请填写收货人姓名");
        }
        if(city_id==null||city_id.isEmpty()||area_id==null||area_id.isEmpty()){
            putMag(map,"msg","请选择所在地区");
        }
        if(address==null||address.isEmpty()){
            putMag(map,"msg","请填写收货人详细地址");
        }
        if(mob_phone==null){
            putMag(map,"msg","请填写手机号码");
        }
        if(mob_phone!=null){
            String mobPhone = mob_phone+"";
            if(!mobPhone.matches("^1[0-9]{10}$")){
                putMag(map,"msg","手机号码只能是11位");
            }
        }
        if((Boolean)map.get("state")){
            Integer id = null;
            try {
                id = orderService.validateAddressAndInsert(true_name, city_id, area_id, area_info, address, mob_phone);
            }catch (RuntimeException ex){
                //ex.printStackTrace();
                logger.warn("验证地址"+"_"+ex.getMessage(),ex);
                map.put("state",false);
            }

            if(id!=null){
                map.put("addr_id",id);
            }else{
                map.put("state",false);
            }
        }
        return map;
    }


    /*
    for(var i=45055;i<45056;i++){
if(nc_a[i]!=undefined){
               $.ajax({
                        url:"http://order.yangmall.com/"+i+"/array",
                        type:"post",
                        dataType: "json",
			async: false,
                        xhrFields: {withCredentials: true},
			data:JSON.stringify(nc_a[i]),
			contentType:"application/json",
                        success:function(result){
                        }
                    });
}
}

     */

    /*
    @RequestMapping("/{id}/array")
    @ResponseBody
    public String receiveAddressArray(@PathVariable Integer id,@RequestBody List<List<String>> lists){
        System.out.println(lists);
        System.out.println(id);

        orderService.insert(lists,id);

        return "true";
    }
    */

    private void putMag(Map<String,Object> map,String msgKey,String newMsg){
        map.put("state",false);
        String msg = (String)map.get(msgKey);
        if(msg!=null){
            map.put(msgKey,msg+"\n"+newMsg);
        }else{
            map.put(msgKey,newMsg);
        }
    }

    @RequestMapping(value = "/submitOrder",method = RequestMethod.POST)
    public String submitOrder(@RequestParam(value = "cart_id[]") ArrayList<String> cartIds,
                                    @RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession,
                                    Integer address_id,String buy_city_id,String token,String reqToken,String orderId) {
        //这个方法的结构和cartcontroller的check类似

        String sessionReqToken = (String)redisSession.getAttribute(REQ_TOKEN);
        if(sessionReqToken==null||sessionReqToken.isEmpty()||reqToken==null||reqToken.isEmpty()|| !reqToken.equals(sessionReqToken)){
            return "redirect:/order";
        }
        redisSession.removeAttribute(REQ_TOKEN);
        if (cartIds.isEmpty()) {
            throw new RuntimeException("非法操作");
        }
        Map<Integer, Integer> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        for (String s : cartIds) {
            String[] a = s.split("\\|");
            map.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
        }

        User user = (User)redisSession.getAttribute(USER);

        Map<Integer,String> quantityInfo = orderService.submitOrder(map,user,address_id,token,orderId);
        if(quantityInfo!=null && !quantityInfo.isEmpty()){
            redisSession.setAttribute("quantityInfo",quantityInfo);
            return "redirect:/cart";
        }
        return "redirect:"+REDIRECT_ORDER_TO_PAY;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
