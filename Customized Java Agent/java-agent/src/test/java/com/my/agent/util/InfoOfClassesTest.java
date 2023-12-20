package com.my.agent.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;

class InfoOfClassesTest {

    private final static Logger LOGGER = Logger.getLogger("InfoOfClassesTest");

    @BeforeAll
    public static void init() {
        LOGGER.info("Let's start the Test!");
    }

    @Test
    @DisplayName("존재하지 않는 클래스명을 조회하기")
    public void testNotFoundNameMethodInfo() {
        // given
        String notFoundClassName = "com.your.agent.CustomJavaAgent";

        // when

        // then (assertThrows - 발생하는 exception을 통해 판단)
        Assertions.assertThrows(
                ClassNotFoundException.class,
                () -> {
                    Class<?> c = Class.forName(notFoundClassName);
                }
        );
    }

    @Test
    @DisplayName("특정 클래스에서 method name 목록 추출하기")
    public void testMethodListInfo() {
        // given
        String className = "com.my.agent.CustomJavaAgent";
        String[] methodNames = {"listLoadedClasses", "premain"};

        // when
        Method[] rtMethods = InfoOfClasses.rtMethodsOf(className);
        String[] rtMethodNames = Arrays.stream(rtMethods)
                                        .map(Method::getName)
                                        .sorted()
                                        .toArray(String[]::new);

        // then (assertArrayEquals - 두 Array 비교를 통해 판단)
        Assertions.assertArrayEquals(methodNames, rtMethodNames);
    }


    @Test
    @DisplayName("특정 클래스에서 name으로 필드 추출하기")
    public void testFieldInfo() {
        // given
        String className = "com.my.agent.CustomJavaAgent";
        String fieldName = "BOOTSTRAP";

        // when
        Field[] rtFields = InfoOfClasses.rtFieldsOf(className);
        Optional<Field> rtField = Arrays.stream(rtFields)
                                        .filter(field -> field.getName().equals(fieldName))
                                        .findFirst();

        // then (assertNotNull - 추출된 필드 객체 null 여부로 판단)
        Assertions.assertNotNull(rtField);
    }

}