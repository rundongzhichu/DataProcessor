package com.shichi.core.utils;

import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

public class ReflectUtils {

    public ReflectUtils() {
    }

    public static <A extends Annotation> A getAnnonation(Object object, Class<A> aclass) {
        Assert.notNull(object, "Object Must not Be Empty!");
        return object.getClass().getAnnotation(aclass);
    }


}
