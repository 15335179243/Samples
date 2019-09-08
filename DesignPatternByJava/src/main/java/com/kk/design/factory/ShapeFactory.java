package com.kk.design.factory;

public class ShapeFactory {

    /**
     * 创建一个图形
     * @param shapeDes
     * @return
     */
    public Shape createShape(String shapeDes) {
        if (shapeDes == null) {
            return null;
        }
        if (shapeDes.equals("circle")) {
            return new Circle();
        }
        if (shapeDes.equals("rectangle")) {
            return new Rectangle();
        }
        if (shapeDes.equals("triangle")) {
            return new Triangle();
        }
        return null;
    }
}
