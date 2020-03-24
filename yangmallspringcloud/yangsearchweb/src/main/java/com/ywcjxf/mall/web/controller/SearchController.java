package com.ywcjxf.mall.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.serviceinterface.SearchService;
import com.ywcjxf.mall.web.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.ywcjxf.mall.service.constant.ArrayListOrHashMapConstants.ARRAY_LIST_DEFAULT_INITIAL_CAPACITY;
import static com.ywcjxf.mall.service.constant.DefaultCharset.UTF_8;

@Controller
public class SearchController {

    private SearchService searchService;

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @RequestMapping("/")
    public ModelAndView search(String keyword,@RequestParam(required = false) String sort){
        if(keyword!=null && !keyword.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("m_search");
            int pageSize = 12;
            Page<Item> items = searchService.search(keyword, sort, 1, pageSize);//这里硬编码了
            if (items != null && !items.isEmpty()) {
                modelAndView.addObject("items", items);
                modelAndView.addObject("keyword", keyword);
                //modelAndView.addObject("count",searchService.searchCount(keyword));

                modelAndView.addObject("sort", sort);
                modelAndView.addObject("pageSize", pageSize);

                modelAndView.addObject("total",items.getTotal());
                //System.out.println(items.getTotal());
                logger.debug(items.getTotal()+"");
                return modelAndView;
            }

            modelAndView = new ModelAndView("m_search_none");
            modelAndView.addObject("none",true);
            modelAndView.addObject("keyword", keyword);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("m_search_none");
        modelAndView.addObject("none",false);
        return modelAndView;
    }

    @RequestMapping("/ajax")
    public void search(String keyword, @RequestParam(required = false) String sort, int pagesize, int page_cur, HttpServletResponse response){
        Page<Item> items = searchService.search(keyword, sort, page_cur, pagesize);
        List<GoodsVo> list = new ArrayList<>(ARRAY_LIST_DEFAULT_INITIAL_CAPACITY);
        for(Item item:items.getResult()){
            GoodsVo goods = new GoodsVo();
            goods.setGoodsId(item.getItemId());
            goods.setGoodsImage(item.getImageUrl());
            goods.setGoodsTitle(item.getTitle());
            goods.setRepresentPrice(item.getReprensentPrice());

            if(item.getYunfeiCost().equals("京东物流")){
                goods.setJd("1");
            }

            list.add(goods);
        }

        toJson(response,list);
    }

    @CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT},allowedHeaders = {"X-Requested-With","Content-Type","Accept"})
    @RequestMapping("/hotwords")
    public void hotWords(HttpServletResponse response){
        List<String> list = new ArrayList<>(searchService.hotWords());
        toJson(response,list);
    }

    private void toJson(HttpServletResponse response,Object o){
        response.setContentType("application/json");
        response.setCharacterEncoding(UTF_8);

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
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
