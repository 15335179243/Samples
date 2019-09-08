package com.kk.design.singleton;

/**
 * 单例模式，饿汉式。
 * 推荐使用.
 * 特点：
 * 　在类加载的时候就实例化，浪费内存。
 *     多线程安全
 */
public class Sun {
    private static Sun instance = new Sun();

    private Sun(){}

    public Sun getInstance(){
        return instance;
    }
}
