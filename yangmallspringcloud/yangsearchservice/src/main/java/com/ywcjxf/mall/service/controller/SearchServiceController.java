package com.ywcjxf.mall.service.controller;

import com.github.pagehelper.Page;
import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.serviceinterface.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SearchServiceController{

    private SearchService searchService;

    @RequestMapping("/search")
    public Page<Item> search(String keyword, String sort, Integer pageNum, Integer pageSize) {
        return searchService.search(keyword,sort,pageNum,pageSize);
    }

    @RequestMapping("/searchCount")
    public Integer searchCount(String keyword) {
        return searchService.searchCount(keyword);
    }

    @RequestMapping("/hotWords")
    public Set<String> hotWords() {
        return searchService.hotWords();
    }

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
