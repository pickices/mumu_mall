package com.liuxinchi.mumu_mall.utils;

/**
 * @author 拾荒老冰棍
 */

public enum OrderEnum {
    PADI(0,"已支付，未发货"),DELIVERED(1,"已支付，已发货"),RECELIED(2,"已收货");

    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderEnum{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                '}';
    }

    OrderEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
