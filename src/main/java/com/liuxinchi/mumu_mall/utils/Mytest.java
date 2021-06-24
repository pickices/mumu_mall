package com.liuxinchi.mumu_mall.utils;

public class Mytest {
    public static void main(String[] args) {
        System.out.println(OrderEnum.PADI);
        int state = OrderEnum.DELIVERED.getState();
        String stateInfo = OrderEnum.DELIVERED.getStateInfo();

        System.out.println(state+"-->"+stateInfo);
        OrderEnum orderEnum = OrderEnum.RECELIED;
        System.out.println(orderEnum);
    }
}
