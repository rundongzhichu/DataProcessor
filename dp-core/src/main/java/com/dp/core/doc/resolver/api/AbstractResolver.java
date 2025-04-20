package com.dp.core.doc.resolver.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class AbstractResolver<C, O, F extends Field, A > implements Resolver<C, O, F, A> {

    Logger logger = LoggerFactory.getLogger(AbstractResolver.class);

    protected O o;
    protected F f;
    protected A a;

    public Object getFieldValue() {
        if(f.getModifiers() != Modifier.PUBLIC) {
            f.setAccessible(true);
        }
        try {
            return f.get(o);
        } catch (IllegalAccessException e) {
            logger.error("Can not get vaule", e);
        }
        return null;
    }


    public void setFieldValue(Object value) {
        if(f.getModifiers() != Modifier.PUBLIC) {
            f.setAccessible(true);
        }
        try {
            f.set(o, f.getType().cast(value));
        } catch (IllegalAccessException e) {
            logger.error("Can not set value", e);
        }
    }

}
