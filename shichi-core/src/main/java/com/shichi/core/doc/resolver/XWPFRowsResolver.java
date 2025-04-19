package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.anno.Rows;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.lang.reflect.Field;
import java.util.List;

public class XWPFRowsResolver<C extends XWPFTable, O, F extends Field, A extends Rows> extends AbstractXWPFResolver<C, O, F, A> {

    public XWPFRowsResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        resolve(getFieldValue());
    }

    public void resolve(Object rowObj) {
        if (rowObj == null) return;
        if(rowObj instanceof List<?>) {
            process((List<?>) rowObj);
        } else {
            throw new RuntimeException("Rows obj should be a List!");
        }
    }

    public void process(List<?> rows) {
        for (Object row:
             rows) {
            Row rowAnno = ReflectUtils.getAnnonation(row.getClass(), Row.class);
            if(rowAnno != null) {
                XWPFRowResolver resolver = new XWPFRowResolver<>(c, row, null, rowAnno);
                resolver.resolve(row);
            }
        }
    }

}
