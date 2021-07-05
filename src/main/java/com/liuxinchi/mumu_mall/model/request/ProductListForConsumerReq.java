package com.liuxinchi.mumu_mall.model.request;

public class ProductListForConsumerReq {

    private String orderBy;

    private Integer categoryId;

    private String keyword;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public ProductListForConsumerReq() {
    }

    public ProductListForConsumerReq(String orderBy, Integer categoryId, String keyword, Integer pageNum, Integer pageSize) {
        this.orderBy = orderBy;
        this.categoryId = categoryId;
        this.keyword = keyword;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
