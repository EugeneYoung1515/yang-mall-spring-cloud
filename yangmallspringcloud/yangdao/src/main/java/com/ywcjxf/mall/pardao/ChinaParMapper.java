package com.ywcjxf.mall.pardao;

import com.ywcjxf.mall.pojo.China;

import java.util.List;

public interface ChinaParMapper {
    void insertBatch(List<China> list);
    China selectById(Integer id);
}
