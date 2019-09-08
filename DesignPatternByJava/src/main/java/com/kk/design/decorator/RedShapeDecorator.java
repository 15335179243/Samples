package com.kk.design.decorator;

public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        shape.draw();
        setRedBorder();
    }

    /**
     * 该方法起到了装饰作用
     */
    private void setRedBorder() {
        System.out.println("Border color: Red");
    }
}
