package com.liuxinchi.mumu_mall.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateCatgoryReq {

    @NotNull(message = "id不能为空")
    private Integer id;

    @Size(min = 2,max = 5,message = "类品名应在2-5个字符之间")
    private String name;

    @Max(value = 3,message = "类品级别最多为3")
    private Integer type;

    private Integer parentId;

    private Integer orderNum;

    public UpdateCatgoryReq(Integer id, String name, Integer type, Integer parentId, Integer orderNum) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.orderNum = orderNum;
    }

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
