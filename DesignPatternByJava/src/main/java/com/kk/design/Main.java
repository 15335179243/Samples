package com.kk.design;

import com.kk.design.abstractfactory.*;
import com.kk.design.factory.Shape;
import com.kk.design.factory.ShapeFactory;
import com.kk.design.singleton.Earth;
import com.kk.design.singleton.Galaxy;

public class Main {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape circle = shapeFactory.createShape("circle");
        Shape triangle = shapeFactory.createShape("triangle");
        Shape rectangle = shapeFactory.createShape("rectangle");

        circle.draw();
        triangle.draw();
        rectangle.draw();

        System.out.println("********抽象工厂**********");

        com.kk.design.abstractfactory.ShapeFactory shapeFactoryNew =
                (com.kk.design.abstractfactory.ShapeFactory) FactoryProducer.createFactory("ShapeFactory");


        com.kk.design.abstractfactory.ColorFactory colorFactoryNew =
                (com.kk.design.abstractfactory.ColorFactory) FactoryProducer.createFactory("ColorFactory");

       com.kk.design.abstractfactory.Rectangle rectangleNew = (Rectangle) shapeFactoryNew.getShape("Rectangle");
       com.kk.design.abstractfactory.Circle circleNew = (Circle) shapeFactoryNew.getShape("Circle");

       com.kk.design.abstractfactory.Red redNew = (Red) colorFactoryNew.getColor("Red");
       com.kk.design.abstractfactory.Blue blueNew = (Blue) colorFactoryNew.getColor("Blue");

       rectangleNew.draw();
       circleNew.draw();
       redNew.fill();
       blueNew.fill();


    }
}
