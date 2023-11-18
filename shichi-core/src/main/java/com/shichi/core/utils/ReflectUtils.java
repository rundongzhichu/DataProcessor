package com.shichi.core.utils;

import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class ReflectUtils {

    public ReflectUtils() {
    }

    public static <O extends AnnotatedElement, A extends Annotation> A getAnnonation(O object, Class<A> aclass) {
        Assert.notNull(object, "Object Must not Be Empty!");
        return object.getAnnotation(aclass);
    }

    public static <O extends AccessibleObject & Member> void setAccessible(O object, boolean access) {
        Assert.notNull(object, "Object Must not Be Empty!");
        object.setAccessible(access);
    }


}
