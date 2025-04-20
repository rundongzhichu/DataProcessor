package com.shichi.core.doc.resolver.api;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

public abstract class AbstractXWPFResolver<C, O, F extends Field, A> extends AbstractResolver<C, O, F, A > {

    protected C c;

    public AbstractXWPFResolver(C c, O o, F f, A a) {
        Assert.notNull(c, "container object should not be null!");
        Assert.notNull(o, "Object should not Be Null!");
        Assert.notNull(a, "Annotation should not Be Null!");
        this.c = c;
        this.o = o;
        this.f = f;
        this.a = a;
    }

}
