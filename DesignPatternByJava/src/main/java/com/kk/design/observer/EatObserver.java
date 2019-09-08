package com.kk.design.observer;

public class EatObserver extends Observer {

    public EatObserver(FoodStore subject){
        this.store = subject;
        this.store.attach(this);
    }
    @Override
    public void update() {
        System.out.println("Eating...\tand current state is: " + store.getState());
    }
}
