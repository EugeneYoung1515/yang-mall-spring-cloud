package com.ywcjxf.mall.pojo;

public class District {
    private Integer districtId;

    private Integer parentDistrictId;

    private String districtName;

    private Integer grade;

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getParentDistrictId() {
        return parentDistrictId;
    }

    public void setParentDistrictId(Integer parentDistrictId) {
        this.parentDistrictId = parentDistrictId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}