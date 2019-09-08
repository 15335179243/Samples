package com.kk.design.decorator;

/**
 * 装饰者模式。
 * 不管什么形状，只要是实现了 Shape 接口的类，实例化后作为参数传递到了形状装饰器【ShapeDecorator】的某个具体类【如：RedShapeDecorator】的对象里面。
 * 保留了该形状本身的特性，还可以具有当前装饰器的特性。
 */
public class Demo {
    public static void main(String[] args) {

        Shape circle = new Circle();
        Shape rectangle = new Rectangle();

        ShapeDecorator circleDecorator = new RedShapeDecorator(circle);
        ShapeDecorator rectangleDecorator = new RedShapeDecorator(rectangle);

        circle.draw();
        rectangle.draw();

        circleDecorator.draw();
        rectangleDecorator.draw();
    }
}
