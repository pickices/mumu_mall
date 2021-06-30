package com.liuxinchi.mumu_mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateProductReq {

    @NotNull(message = "商品id不能为空")
    private Integer id;

    @NotNull(message = "商品名不能为空")
    private String name;

    @NotNull(message = "商品图片不能为空")
    private String image;

    private String detail;

    @NotNull(message = "商品分类不能为空")
    private Integer categoryId;

    @Min(value = 1,message = "商品价格不能小于1分")
    private Integer price;

    @Max(value = 10000,message = "商品库存不能大于10000")
    private Integer stock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public UpdateProductReq() {
    }

    public UpdateProductReq(Integer id, String name, String image, String detail, Integer categoryId, Integer price, Integer stock) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
    }
}
