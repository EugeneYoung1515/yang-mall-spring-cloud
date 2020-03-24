package com.ywcjxf.mall.service;

import com.ywcjxf.mall.dao.IndexAdMapper;
import com.ywcjxf.mall.pojo.IndexAd;
import com.ywcjxf.mall.serviceinterface.IndexAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IndexAdServiceImpl implements IndexAdService {
    private IndexAdMapper indexAdMapper;

    @Override
    public void insertImage(String imageLink) {
        Integer id = indexAdMapper.selectIdByNullOrderByIdLimitOne();
        IndexAd indexAd = new IndexAd();
        indexAd.setIndexAdId(id);
        indexAd.setImageUrl(imageLink);
        indexAdMapper.updateByPrimaryKeySelective(indexAd);
    }

    @Override
    public List<IndexAd> getAllValidIndexAd(Integer categoryId) {
        return indexAdMapper.selectAllValidAdByCategory(categoryId,new Date());
    }

    @Autowired
    public void setIndexAdMapper(IndexAdMapper indexAdMapper) {
        this.indexAdMapper = indexAdMapper;
    }
}
