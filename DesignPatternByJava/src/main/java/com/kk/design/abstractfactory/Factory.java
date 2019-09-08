package com.kk.design.abstractfactory;

public abstract class Factory {

    public abstract Shape getShape(String shape);

    public abstract Color getColor(String color);
}
