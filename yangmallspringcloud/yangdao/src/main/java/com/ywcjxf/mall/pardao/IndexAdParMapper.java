package com.ywcjxf.mall.pardao;

import com.ywcjxf.mall.pojo.IndexAd;

import java.util.Date;
import java.util.List;

public interface IndexAdParMapper {
    Integer selectIdByNullOrderByIdLimitOne();
    List<IndexAd> selectAllValidAdByCategory(Integer categoryId,Date date);
}
