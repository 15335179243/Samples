package com.kk.design.abstractfactory;

public class ShapeFactory extends Factory {
    @Override
    public Shape getShape(String shape) {
        if (shape == null) {
            return null;
        }
        if (shape.equals("Rectangle")) {
            return new Rectangle();
        } else if (shape.equals("Circle")){
            return new Circle();
        }
        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }
}
