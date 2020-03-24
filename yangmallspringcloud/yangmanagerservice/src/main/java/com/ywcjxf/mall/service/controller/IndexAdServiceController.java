package com.ywcjxf.mall.service.controller;

import com.ywcjxf.mall.pojo.IndexAd;
import com.ywcjxf.mall.serviceinterface.IndexAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexAdServiceController {
    private IndexAdService indexAdService;

    @RequestMapping("/insertImage")
    public Object insertImage(String imageLink) {
        indexAdService.insertImage(imageLink);
        return null;
    }

    @RequestMapping("/getAllValidIndexAd")
    public List<IndexAd> getAllValidIndexAd(Integer categoryId) {
        return indexAdService.getAllValidIndexAd(categoryId);
    }

    @Autowired
    public void setIndexAdService(IndexAdService indexAdService) {
        this.indexAdService = indexAdService;
    }
}
