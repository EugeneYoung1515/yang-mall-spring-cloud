package com.ywcjxf.mall.pardao;

import com.github.pagehelper.Page;
import com.ywcjxf.mall.pojo.Item;

import java.util.List;

public interface ItemParMapper {
    Page<Item> selectByFullTextSearch(String keyword);
    Page<Item> selectByFullTextSearchWithSort(String keyword,String sort);
    Integer selectCountByFullTextSearch(String keyword);
    Integer selectIdByNullOrderByIdLimitOne();
    Integer selectTotalSalesById(Integer itemId);
    List<Item> selectAllItemById(List<Integer> list);
}