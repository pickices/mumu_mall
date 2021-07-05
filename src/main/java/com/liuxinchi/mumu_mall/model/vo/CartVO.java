package com.liuxinchi.mumu_mall.model.vo;

public class CartVO {

    private Integer productId;

    private Integer userId;

    private Integer quantity;

    private Integer selected;

    private String productName;

    private String productImage;

    private Integer price;

    private Integer totalPrice;

    public CartVO() {
    }

    public CartVO(Integer productId, Integer userId, Integer quantity, Integer selected, String productName, String productImage, Integer price, Integer totalPrice) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.selected = selected;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
