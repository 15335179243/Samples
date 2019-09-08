package com.kk.design.observer;

public class Demo {
    public static void main(String[] args) {
        FoodStore foodStore = new FoodStore();
        new EatObserver(foodStore);
        new DrinkObserver(foodStore);

        //食品小店，东西变化了，吃的观察者和饮料的观察者都会收到信息
        foodStore.setState(99);
        foodStore.setState(19);
    }
}
