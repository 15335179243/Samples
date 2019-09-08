package com.kk.design.abstractfactory;

/**
 * 工厂创造器
 */
public class FactoryProducer {

    public static Factory createFactory(String factoryName){
        if (factoryName == null) {
            return null;
        }
        if (factoryName.equals("ColorFactory")) {
            return new ColorFactory();
        } else if (factoryName.equals("ShapeFactory")){
            return new ShapeFactory();
        }
        return null;
    }
}
