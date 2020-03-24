package com.ywcjxf.mall.serviceinterface.feign;

import com.ywcjxf.mall.pojo.IndexAd;
import com.ywcjxf.mall.serviceinterface.IndexAdService;
import com.ywcjxf.mall.serviceinterface.feign.fallback.IndexAdServiceFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "manager-service",fallback = IndexAdServiceFeignFallback.class)
public interface IndexAdServiceFeign extends IndexAdService {

    @Override
    @PostMapping("/insertImage")
    void insertImage(@RequestParam("imageLink") String imageLink);

    @Override
    @GetMapping("/getAllValidIndexAd")
    List<IndexAd> getAllValidIndexAd(@RequestParam("categoryId") Integer categoryId);
}
