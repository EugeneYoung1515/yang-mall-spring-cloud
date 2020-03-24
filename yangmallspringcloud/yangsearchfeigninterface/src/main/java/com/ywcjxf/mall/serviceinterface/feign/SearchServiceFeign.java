package com.ywcjxf.mall.serviceinterface.feign;


import com.github.pagehelper.Page;
import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.serviceinterface.SearchService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient("search-service")
public interface SearchServiceFeign extends SearchService{

    @Override
    @GetMapping("/search")
    Page<Item> search(@RequestParam("keyword") String keyword, @RequestParam("sort") String sort, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    @Override
    @GetMapping("/searchCount")
    Integer searchCount(@RequestParam("keyword") String keyword);

    @Override
    @GetMapping("/hotWords")
    Set<String> hotWords();
}
