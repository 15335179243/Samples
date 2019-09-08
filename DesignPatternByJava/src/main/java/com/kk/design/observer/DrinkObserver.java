package com.kk.design.observer;

public class DrinkObserver extends Observer {

    public DrinkObserver(FoodStore store){
        this.store = store;
        this.store.attach(this);
    }
    @Override
    public void update() {
        System.out.println("Drinking...\tand current state is: " + store.getState());
    }
}
