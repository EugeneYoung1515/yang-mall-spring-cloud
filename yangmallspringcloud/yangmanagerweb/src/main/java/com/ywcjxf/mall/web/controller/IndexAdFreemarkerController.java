package com.ywcjxf.mall.web.controller;

import com.ywcjxf.mall.pojo.IndexAd;
import com.ywcjxf.mall.serviceinterface.IndexAdService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ywcjxf.mall.constant.FileLocation.INDEX_PAGE;

@Controller
public class IndexAdFreemarkerController {
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private IndexAdService indexAdService;

    private static final Logger logger = LoggerFactory.getLogger(IndexAdFreemarkerController.class);

    @RequestMapping("/createindexhtml")
    @ResponseBody
    public Map<String,Object> createIndexHtml(){
        try (FileWriter fileWriter = new FileWriter(INDEX_PAGE)){
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("index.ftl");
            Map<String, List<IndexAd>> map = new HashMap<>(16);
            map.put("list", indexAdService.getAllValidIndexAd(1));
            template.process(map, fileWriter);
        }catch (Exception e){
            //e.printStackTrace();

            logger.error(e.getMessage(),e);
        }
        Map<String,Object> map = new HashMap<>(16);
        map.put("status","生成完毕");
        return map;
    }

    @Autowired
    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Autowired
    public void setIndexAdService(IndexAdService indexAdService) {
        this.indexAdService = indexAdService;
    }
}
