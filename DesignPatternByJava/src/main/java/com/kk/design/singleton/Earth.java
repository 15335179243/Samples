package com.kk.design.singleton;

/**
 * 单列模式，懒汉式
 * 多线程安全
 * 由于加锁了，效率不高
 */
public class Earth {
    private static Earth instance;

    private Earth (){}

    /**
     * 线程安全的构造方法
     * @return
     */
    public static synchronized Earth getInstance(){
        if (instance == null) {
            instance = new Earth();
        }
        return instance;
    }

    public void show(){
        String toString = instance.toString();
        System.out.println("地球的地址是：" + toString);
    }
}
