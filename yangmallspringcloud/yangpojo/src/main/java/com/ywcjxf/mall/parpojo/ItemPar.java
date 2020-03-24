package com.ywcjxf.mall.parpojo;

import com.ywcjxf.mall.pojo.Item;
import com.ywcjxf.mall.pojo.Spec;

import java.util.List;

public class ItemPar{
    private List<Spec> allSpec;

    private List<Item> likeItems;

    public List<Spec> getAllSpec() {
        return allSpec;
    }

    public void setAllSpec(List<Spec> allSpec) {
        this.allSpec = allSpec;
    }

    public List<Item> getLikeItems() {
        return likeItems;
    }

    public void setLikeItems(List<Item> likeItems) {
        this.likeItems = likeItems;
    }
}
