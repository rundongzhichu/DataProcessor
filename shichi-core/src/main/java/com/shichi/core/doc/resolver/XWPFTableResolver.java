package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.anno.Table;
import com.shichi.core.doc.model.XWPFTableModel;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.doc.resolver.api.Resolver;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class XWPFTableResolver<C extends XWPFDocument, O extends XWPFTableModel, F extends Field, A extends Table> extends AbstractXWPFResolver<C, O, F, A> {

    public XWPFTableResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        XWPFTable xwpfTable = null;
        if(!o.isAnnoFirst()) {
            if(a.fixed())
                xwpfTable = c.createTable(o.getRow(), o.getCol());
            else
                xwpfTable = c.createTable();
        } else {
            if(o.isFixed())
                xwpfTable = c.createTable(a.row(), a.col());
            else
                xwpfTable = c.createTable();
        }
        processTable(xwpfTable);
    }

    private void processTable(XWPFTable xwpfTable) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field :
                fields) {
            for(Annotation annotation: field.getAnnotations()) {
                Resolver resolver = null;
                if (annotation instanceof Row) {
                    resolver = new XWPFRowResolver(xwpfTable, o, field, (Row) annotation);
                }
                if (resolver != null) resolver.resolve();
            }
        }
    }

}
