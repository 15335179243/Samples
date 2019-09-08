package com.kk.design.decorator;

/**
 * shape 的装饰器类。
 * 这个装饰类，在实例化的时候会接收一个 Shape 的实现类的实例对象，
 */
public class ShapeDecorator implements Shape {

    protected Shape shape;

    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void draw() {
        shape.draw();
    }
}
