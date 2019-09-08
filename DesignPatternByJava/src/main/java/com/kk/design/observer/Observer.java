package com.kk.design.observer;

/**
 * 抽象的观察者
 */
public abstract class Observer {
    protected FoodStore store;
    public abstract void update();
}
