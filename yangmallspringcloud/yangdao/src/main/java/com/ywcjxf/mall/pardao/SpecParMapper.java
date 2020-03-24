package com.ywcjxf.mall.pardao;

import com.github.pagehelper.Page;
import com.ywcjxf.mall.pojo.Spec;

import java.util.List;

public interface SpecParMapper {
    List<Spec> selectAllByItemId(Integer itemId);
    Spec selectFirstSpecByItemId(Integer itemId);
    List<Spec> selectAllSpecById(List<Integer> list);
    //List<Spec> selectAllOnlyIdAndInventory();
    Page<Spec> selectAllOnlyIdAndInventory();
    Integer selectCount();
    Spec selectForUpdateById(Integer specId);
    //void updateInventory(Integer quantity,Integer specId);

    void updateInventory(Spec spec);
}
