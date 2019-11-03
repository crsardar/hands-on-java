package com.crsardar.handson.java.binding;

/**
 * @author Chittaranjan Sardar
 */
public class StaticDynamicBinding {


    public static void main(String[] args) {


        StaticDynamicBinding staticDynamicBinding = new StaticDynamicBinding();

        // Static
        DynamicClassA dynamicClassA = new DynamicClassD();

        staticDynamicBinding.staticBindingMethod(dynamicClassA); // See there is no method for DynamicClassD.
        staticDynamicBinding.staticBindingMethod(null); // "null" always taking the lowest child class.

        // Dynamic
        dynamicClassA.dynamicBinding();
    }

    // Static Binding Example ->
    public void staticBindingMethod(DynamicClassA dynamicClassA){

        System.out.println("\n\tI am at - public void staticBindingMethod(DynamicClassA dynamicClassA)");
    }

    public void staticBindingMethod(DynamicClassB dynamicClassB){

        System.out.println("\n\tI am at - public void staticBindingMethod(DynamicClassB dynamicClassB)");
    }

    public void staticBindingMethod(DynamicClassC dynamicClassC){

        System.out.println("\n\tI am at - public void staticBindingMethod(DynamicClassC dynamicClassC)");
    }

    public void oneMoreStaticBinding(){

        DynamicClassA dynamicClassA = new DynamicClassB();

        staticBindingMethod(dynamicClassA);
        staticBindingMethod(null);
    }
    // Static Binding Example <-

    // Dynamic Binding ->
    private static class DynamicClassA{

        public void dynamicBinding(){

            System.out.println("\n\tI am at - dynamicBinding() of " + this.getClass().getSimpleName());
        }
    }

    private static class DynamicClassB extends DynamicClassA{

        public void dynamicBinding(){

            System.out.println("\n\tI am at - dynamicBinding() of " + this.getClass().getSimpleName());
        }
    }

    private static class DynamicClassC extends DynamicClassB{

        public void dynamicBinding(){

            System.out.println("\n\tI am at - dynamicBinding() of " + this.getClass().getSimpleName());
        }
    }

    private static class DynamicClassD extends DynamicClassC{

        public void dynamicBinding(){

            System.out.println("\n\tI am at - dynamicBinding() of " + this.getClass().getSimpleName());
        }
    }
    // Dynamic Binding <-
}
