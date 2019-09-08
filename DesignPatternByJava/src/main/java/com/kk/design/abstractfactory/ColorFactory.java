package com.kk.design.abstractfactory;

public class ColorFactory extends Factory{
    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equals("Red")) {
            return new Red();
        } else if (color.equals("Blue")){
            return new Blue();
        }
        return null;
    }
}
