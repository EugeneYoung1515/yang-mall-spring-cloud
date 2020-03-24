package com.ywcjxf.mall.web.controller;

import com.alibaba.fastjson.JSON;
import com.ywcjxf.mall.pojo.Spec;
import com.ywcjxf.mall.serviceinterface.ItemService;
import com.ywcjxf.mall.web.vo.GoodsVo2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.HASH_MAP_DEFAULT_INITIAL_CAPACITY;

@Controller
public class ItemController {
    private ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT},allowedHeaders = {"X-Requested-With","Content-Type","Accept"})
    @RequestMapping("/priceandsales")//待定
    @ResponseBody
    public Map<String,Object> representPriceAndSalesAjax(Integer itemId){
        Spec spec = itemService.getItemFirstSpec(itemId);
        Map<String,Object> map = new HashMap<>(HASH_MAP_DEFAULT_INITIAL_CAPACITY);
        map.put("representPrice",spec.getRepresentPrice());
        map.put("totalSales",itemService.getTotalSalesByItemId(itemId));
        map.put("goodsStorage",spec.getNowInventory());
        return map;
    }

    @CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT},allowedHeaders = {"X-Requested-With","Content-Type","Accept"})
    @RequestMapping("/allspec")//待定
    @ResponseBody
    public void allSpecByItemIdAjax(HttpServletResponse response,Integer itemId){
        List<Spec> list = itemService.getAllSpecByItemId(itemId);
        List<GoodsVo2> list2 = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        for(Spec spec:list){
            GoodsVo2 goodsVo2 = new GoodsVo2();
            //goodsVo2.setGoodsId(spec.getSpecId());
            goodsVo2.setGoodsId(spec.getSpecId()+"");
            goodsVo2.setGoodsName(spec.getSpecTitle());
            goodsVo2.setGoodsStorage(spec.getNowInventory());
            goodsVo2.setRepresentPrice(spec.getRepresentPrice().toString());
            goodsVo2.setSpecIndex(spec.getSpecId());
            goodsVo2.setSpecValue(spec.getSpecName());
            list2.add(goodsVo2);
        }

        toJson(response,list2);
    }

    private void toJson(HttpServletResponse response,Object o){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(o));
            writer.close();
        }catch (IOException ex){
            //ex.printStackTrace();

            logger.error("toJson"+"_"+ex.getMessage(),ex);
        }

    }


    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}
