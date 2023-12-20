package com.my.agent.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class InfoOfClasses {

    private static final Logger LOGGER = Logger.getLogger(InfoOfClasses.class.getName());

    /* 타겟 클래스 별 메소드 출력 */
    public static void printMethods(String[] classNames) {
        try {
            for(String className : classNames) {
                Class<?> targetClass = Class.forName(className);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                LOGGER.info("[Agent] Target Class Name : " + className);

                // 메소드 정보 출력
                Method[] methods = targetClass.getDeclaredMethods();
                for(Method method : methods) {
                    printMethodInfo(method);
                }

                // 필드 정보 출력
                Field[] fields = targetClass.getFields();
                for(Field field : fields) {
                    printFieldInfo(field);
                }
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /* 메소드 별 정보 출력 (name, param, exception, return type) */
    private static void printMethodInfo(Method method) {
        System.out.println("-----------------------------------------------------------------");
        LOGGER.info("[Agent] Method Info");

        System.out.println("declaring class = " + method.getDeclaringClass());
        System.out.println("name = " + method.getName());

        Class<?>[] paramTypes = method.getParameterTypes();
        for(int i=0 ; i<paramTypes.length ; i++) {
            System.out.println("param #" + i + " = " + paramTypes[i]);
        }

        Class<?>[] exceptionTypes = method.getExceptionTypes();
        for(int i=0 ; i<exceptionTypes.length ; i++) {
            System.out.println("exception #" + i + " " + exceptionTypes[i]);
        }

        System.out.println("return type = " + method.getReturnType());
    }


    /* 필드 별 정보 출력 */
    private static void printFieldInfo(Field field) {
        System.out.println("-----------------------------------------------------------------");
        LOGGER.info("[Agent] Field Info");

        System.out.println("declaring class = " + field.getDeclaringClass());
        System.out.println("name = " + field.getName());
        System.out.println("type = " + field.getType());
        System.out.println("modifiers = " + field.getModifiers());
    }

    public static Method[] rtMethodsOf(String className) {
        try {
            Class<?> targetClass = Class.forName(className);
            return targetClass.getDeclaredMethods();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Field[] rtFieldsOf(String className) {
        try {
            Class<?> targetClass = Class.forName(className);
            return targetClass.getFields();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    /*
    public static void invokeMethod(String className, String methodName, Class<?>[] paramTypeList, Object[] argList) {
        try {
            Class<?> targetClass = Class.forName(className);
            Method targetMethod = targetClass.getMethod(methodName, paramTypeList);
            Object returnObj = targetMethod.invoke(new CustomJavaAgent(), argList);
            System.out.println("returnObj.toString() = " + returnObj.toString());

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    */
}
