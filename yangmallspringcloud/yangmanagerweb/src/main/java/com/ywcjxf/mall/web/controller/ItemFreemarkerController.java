package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.pojo.IndexAd;
import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.serviceinterface.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ywcjxf.mall.constant.FileLocation.ITEM_PAGE;

@Controller
//@SessionAttributes("itemId")
public class ItemFreemarkerController {
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(ItemFreemarkerController.class);

    @RequestMapping("/imageanditem/result")
    public String createHtml(ModelMap map,@ModelAttribute("itemId") Integer itemId){
        //System.out.println("run");

        Integer itemId2 = (Integer)map.get("itemId");
        try (FileWriter fileWriter = new FileWriter(ITEM_PAGE+itemId2+".html")){
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");

            Map<String,Item> map2 = new HashMap<>(16);
            map2.put("item", itemService.getItemById(itemId2));
            template.process(map2, fileWriter);

            map.addAttribute("msg","图片上传成功且生成静态页成功");
        }catch (Exception e){
            //e.printStackTrace();

            logger.error("freemarker exception"+"_"+e.getMessage(),e);
        }
        return "c_uploadImageResult";
    }

    @Autowired
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }
}
