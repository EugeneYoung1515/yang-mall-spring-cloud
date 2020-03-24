package com.ywcjxf.mall.serviceinterface;

import com.ywcjxf.mall.pojo.IndexAd;

import java.util.List;

public interface IndexAdService {
    void insertImage(String imageLink);
    List<IndexAd> getAllValidIndexAd(Integer categoryId);
}
