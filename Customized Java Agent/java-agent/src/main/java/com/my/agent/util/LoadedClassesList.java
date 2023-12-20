package com.my.agent.util;

import com.my.agent.CustomJavaAgent;

import java.util.Arrays;
import java.util.logging.Logger;

import static com.my.agent.CustomJavaAgent.*;

public class LoadedClassesList {

    private static final Logger LOGGER = Logger.getLogger(LoadedClassesList.class.getName());

    /* 로드된 클래스 목록 출력 */
    public static void printClassesLoaded(String classLoaderType) {
        LOGGER.info("[Agent] LoadedClassesList printClassesLoaded (" + classLoaderType + ")");
        Class<?>[] classes = null;

        if(classLoaderType.equals("ALL")) classes = LoadedClassesList.listAllLoadedClasses();
        else classes = LoadedClassesList.listInitialLoadedClasses(classLoaderType);

        Arrays.asList(classes)
                .forEach(c -> System.out.println(c.getName()));
    }

    /* 로드된 클래스 전체 목록 리턴 */
    public static Class<?>[] listAllLoadedClasses() {
        LOGGER.info("[Agent] CustomJavaAgent listAllLoadedClasses");
        return CustomJavaAgent.instrumentation.getAllLoadedClasses();
    }

    /* Initiating Loader 종류 별 로드된 클래스 목록 리턴 */
    public static Class<?>[] listInitialLoadedClasses(String classLoaderType) {
        LOGGER.info("[Agent] CustomJavaAgent listInitialLoadedClasses (" + classLoaderType + ")");
        return CustomJavaAgent.instrumentation.getInitiatedClasses(
                getClassLoader(classLoaderType)   // classLoaderType null이면 default ClassLoader는 'BOOTSTRAP'
        );
    }

    public static Class<?>[] listInitialLoadedClasses2(ClassLoader classLoader) {
        return CustomJavaAgent.instrumentation.getInitiatedClasses(classLoader);
    }

    /* 특정 타입의 클래스 로더 리턴 */
    private static ClassLoader getClassLoader(String classLoaderType) {
        LOGGER.info("[Agent] CustomJavaAgent getClassLoader (" + classLoaderType + ")");
        ClassLoader classLoader = null;

        switch (classLoaderType) {
            case SYSTEM:
                classLoader = ClassLoader.getSystemClassLoader();
                break;
            case EXTENSION:
                classLoader = ClassLoader.getSystemClassLoader().getParent();
                break;
            case BOOTSTRAP:
                break;
            default:
                break;
        }

        return classLoader;
    }

}

