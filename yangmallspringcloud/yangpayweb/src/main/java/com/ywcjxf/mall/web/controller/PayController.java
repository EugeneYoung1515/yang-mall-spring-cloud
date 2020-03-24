package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.pojo.Order;
import com.ywcjxf.mall.pojo.User;
import com.ywcjxf.mall.service.exception.NoSuchOrderUser;
import com.ywcjxf.mall.service.pay.config.PayJSConfig;
import com.ywcjxf.mall.service.pay.config.util.SignUtil;
import com.ywcjxf.mall.serviceinterface.PayService;
import com.ywcjxf.mall.serviceinterface.dto.PairDto;
import com.ywcjxf.mall.web.filter.RedisSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ywcjxf.mall.constant.Session.REDIS_SESSION;
import static com.ywcjxf.mall.constant.Session.USER;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;

@Controller
public class PayController {
    private PayService payService;

    private PayJSConfig payJSConfig;

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    //注意订单页和支付页都没有延长session过期时间

    @RequestMapping("/pay")
    public ModelAndView getPayPage(@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){
        User user = (User)redisSession.getAttribute(USER);
        ModelAndView modelAndView = new ModelAndView("m_pay");

        Order order = payService.orderIdAndAmount(user);
        modelAndView.addObject("orderId",order.getOrderId());
        modelAndView.addObject("payTotal",order.getPayTotal());

        return modelAndView;
    }

    @RequestMapping(value = "/showcode",method = RequestMethod.POST)
    //自己有的地方用String 存orderId 有的地方用Long存orderId
    public ModelAndView showCode(@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession,String payment_code,Long orderId){
        //重新请求这个的话
        //如果数据和上一次一样 支付平台哪里不会生成新的记录 有幂等 但是 过期时间减少了

        User user = (User)redisSession.getAttribute(USER);

        ModelAndView modelAndView = new ModelAndView("m_pay_wx");

        String payMethod = null;
        if(payment_code.equals("alipay")){
            payMethod = "alipay";
            modelAndView.addObject("payMethod","支付宝扫码支付");
        }
        if(payment_code.equals("wxpay")){
            payMethod = "native";
            modelAndView.addObject("payMethod","微信扫码支付");
        }
        PairDto<Order,PairDto<String,String,String>,Object> pairDto = payService.showCode(user,payMethod,orderId);

        if(pairDto!=null&&pairDto.getSecond().getThird()==null){
            modelAndView.addObject("orderId",pairDto.getFirst().getOrderId());
            modelAndView.addObject("payTotal",pairDto.getFirst().getPayTotal());
            modelAndView.addObject("code",pairDto.getSecond().getFirst());
            modelAndView.addObject("payPlatformNumber",pairDto.getSecond().getSecond());
        }else if(pairDto!=null&&pairDto.getSecond().getThird()!=null){

        }else{

        }
        return modelAndView;
    }

    @RequestMapping(value = "/paynotify",method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded"/*,headers = "content-type=application/x-www-form-urlencoded"*/)
    public void payNotify(HttpServletRequest request, @RequestParam("aoid") String aoId,
                          @RequestParam("order_id") Long orderId, @RequestParam("pay_price") String payPrice,
                          @RequestParam("pay_time") String payTime, @RequestParam("more") String more,
                          @RequestParam("detail") String detail, @RequestParam("sign") String sign,
                          HttpServletResponse response,@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession){

        redisSession.invalidate();
        //支付平台回调 redis中不设置相应的session

        //System.out.println(request.getHeader("content-type"));
        logger.debug(request.getHeader("content-type"));

        List<String> list = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        list.add(aoId);
        list.add(orderId+"");
        list.add(payPrice);
        list.add(payTime);
        String createSign = SignUtil.sign(list,payJSConfig.getKey());
        //System.out.println(createSign);
        logger.info(createSign);

        if(!createSign.equals(sign)){
            //System.out.println(false);
            logger.info(false+"");
            try {
                response.sendError(404);
            }catch (IOException ex){
                //ex.printStackTrace();
                logger.error("response.sendError(404)"+"_"+ex.getMessage(),ex);
            }
            return;
        }

        //User user = (User)redisSession.getAttribute("user");

        try {
            payService.payNotify(aoId, orderId, payPrice, payTime, detail, more/*,user*/);

            PrintWriter printWriter = null;
            try{
                //response.sendError(200);
                printWriter = response.getWriter();
                printWriter.write("success");
                printWriter.close();
            }catch (IOException ex){
                //ex.printStackTrace();
                logger.error("printWriter.write"+"_"+ex.getMessage(),ex);

                //return;
            }

        }catch (NoSuchOrderUser ex){
            try {
                response.sendError(404);
            }catch (IOException e){
                //e.printStackTrace();
                logger.error("response.sendError(404)"+"_"+ex.getMessage(),ex);
            }
        }
    }

    @RequestMapping("/success")
    @ResponseBody
    public boolean paySuccessIsTrue(@RequestAttribute(REDIS_SESSION) RedisSessionFilter.RedisSession redisSession,String aoId){
        //return payService.paySuccessIsTrue(aoId);

        User user = (User)redisSession.getAttribute(USER);
        return payService.paySuccessIsTrue(aoId,user);
    }

    @RequestMapping("/showsuccess")
    public String successPage(){
        return "success";
    }

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @Autowired
    public void setPayJSConfig(PayJSConfig payJSConfig) {
        this.payJSConfig = payJSConfig;
    }
}
