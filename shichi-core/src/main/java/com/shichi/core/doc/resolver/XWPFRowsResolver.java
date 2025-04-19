package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.lang.reflect.Field;

public class XWPFRowsResolver<C extends XWPFTable, O, F extends Field, A extends Row> extends AbstractXWPFResolver<C, O, F, A> {

    public XWPFRowsResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {

    }
}
