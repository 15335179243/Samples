package com.kk.design.singleton;

public class Demo {
    public static void main(String[] args) {

        //单例模式调用
        Earth earth = Earth.getInstance();
        Earth earth2 = Earth.getInstance();

        earth.show();
        earth2.show();

        Galaxy.INSTANCE.show();
    }
}
