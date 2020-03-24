package com.ywcjxf.mall.pardao;

import com.ywcjxf.mall.pojo.OrderItemSpec;

import java.util.List;

public interface OrderItemSpecParMapper {
    void insertBatch(List<OrderItemSpec> list);
}
