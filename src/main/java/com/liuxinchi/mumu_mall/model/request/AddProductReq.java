package com.liuxinchi.mumu_mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddProductReq {

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

    private Integer status;

    public AddProductReq() {
    }

    @Override
    public String toString() {
        return "AddProductReq{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", detail='" + detail + '\'' +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }

    public AddProductReq(String name, String image, String detail, Integer categoryId, Integer price, Integer stock, Integer status) {
        this.name = name;
        this.image = image;
        this.detail = detail;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
