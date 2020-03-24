package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.Item;

import java.util.List;
import java.util.Set;

import com.github.pagehelper.Page;

public interface SearchService {
    Page<Item> search(String keyword,String sort,Integer pageNum,Integer pageSize);
    Integer searchCount(String keyword);
    Set<String> hotWords();
}
