package com.kk.design.singleton;

/**
 * 单例模式，懒汉式
 * 双锁机制，多线程安全，且效率高
 */
public class Moon {
    private static volatile Moon instance;

    private Moon(){}

    public static Moon getInstance(){
        if (instance == null) {
            synchronized (Moon.class){
                if (instance == null) {
                    instance = new Moon();
                }
            }
        }
        return instance;
    }
}
