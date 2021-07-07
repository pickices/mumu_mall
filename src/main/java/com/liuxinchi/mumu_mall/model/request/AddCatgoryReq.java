package com.liuxinchi.mumu_mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddCatgoryReq {

    @NotNull(message = "类品名不能为空")
    @Size(min = 2,max = 5,message = "类品名应在2-5个字符之间")
    private String name;

    @NotNull(message = "类品级别不能为空")
    @Max(value = 3,message = "类品级别最多为3")
    private Integer type;

    @NotNull(message = "类品父级不能为空")
    private Integer parentId;

    @NotNull(message = "orderNum不能为空")
    private Integer orderNum;

    @Override
    public String toString() {
        return "AddCatgoryReq{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", parentId=" + parentId +
                ", orderNum=" + orderNum +
                '}';
    }

    public AddCatgoryReq(String name, Integer type, Integer parentId, Integer orderNum) {
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
