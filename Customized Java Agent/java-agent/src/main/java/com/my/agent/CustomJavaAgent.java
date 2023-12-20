package com.my.agent;

import com.my.agent.util.InfoOfClasses;
import com.my.agent.util.LoadedClassesList;

import java.lang.instrument.Instrumentation;
import java.util.logging.Logger;

public class CustomJavaAgent {

    public static final String BOOTSTRAP = "BOOTSTRAP";
    public static final String SYSTEM = "SYSTEM";
    public static final String EXTENSION = "EXTENSION";
    public static Instrumentation instrumentation;
    private static final Logger LOGGER = Logger.getLogger(CustomJavaAgent.class.getName());


    public static void premain(String agentArgs, Instrumentation instrumentation) {
        LOGGER.info("[Agent] CustomJavaAgent premain");
        CustomJavaAgent.instrumentation = instrumentation;

        listLoadedClasses();
        InfoOfClasses.printMethods(new String[] {"java.util.Stack", "com.my.agent.CustomJavaAgent"});
    }

    /* LoadedClasses 목록 출력 */
    private static void listLoadedClasses() {
        LoadedClassesList.printClassesLoaded(BOOTSTRAP);
        LoadedClassesList.printClassesLoaded(SYSTEM);
        LoadedClassesList.printClassesLoaded(EXTENSION);

        LOGGER.info("---------------------------------");
        LoadedClassesList.printClassesLoaded("ALL");
    }
}
