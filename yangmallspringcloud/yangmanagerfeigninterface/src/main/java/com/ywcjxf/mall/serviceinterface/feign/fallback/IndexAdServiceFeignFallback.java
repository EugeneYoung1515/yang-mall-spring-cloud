package com.ywcjxf.mall.serviceinterface.feign.fallback;

import com.ywcjxf.mall.pojo.IndexAd;
import com.ywcjxf.mall.serviceinterface.feign.IndexAdServiceFeign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IndexAdServiceFeignFallback implements IndexAdServiceFeign {
    @Override
    public void insertImage(String imageLink) {
        System.out.println("fallback:insertImage");
    }

    @Override
    public List<IndexAd> getAllValidIndexAd(Integer categoryId) {
        return null;
    }
}
