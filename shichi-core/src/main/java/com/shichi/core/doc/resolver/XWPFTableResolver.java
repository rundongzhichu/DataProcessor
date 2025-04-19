package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.anno.Rows;
import com.shichi.core.doc.anno.Table;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.doc.resolver.api.Resolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class XWPFTableResolver<C extends XWPFDocument, O, F extends Field, A extends Table> extends AbstractXWPFResolver<C, O, F, A> {

    public XWPFTableResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        // 获取当前表对象的值进行进一步处理
        resolve(getFieldValue());
    }

    public void resolve(Object table) {
        if (table == null) return;
        if (table instanceof List<?>) {
            process((List<?>) table);
        } else {
            process(table);
        }
    }


    /**
     * 处理由rows形成的table 的list对象
     * @param rows
     */
    private void process(List<?> rows) {
        XWPFTable xwpfTable = null;
        if(a.fixed()) {
            xwpfTable = c.createTable(a.row(), a.col());
        } else {
            xwpfTable = c.createTable();
        }

        if(!rows.isEmpty()) {
            for (int i = 0; i < rows.size(); i++) {
                Object row = rows.get(i);
                Row rowAnno = ReflectUtils.getAnnonation(row.getClass(), Row.class);
                XWPFRowResolver resolver = new XWPFRowResolver(xwpfTable, row, null, rowAnno);
                resolver.resolve(row);
            }
        }
    }

    /**
     * 处理table对象本身
     * @param table
     */
    private void process(Object table) {
        XWPFTable xwpfTable = null;
        if(a.fixed()) {
            xwpfTable = c.createTable(a.row(), a.col());
        }
        else {
            xwpfTable = c.createTable();
        }
        process(xwpfTable);

        Field[] fields = table.getClass().getDeclaredFields();
        for (Field field :
                fields) {
            for(Annotation annotation: field.getAnnotations()) {
                Resolver resolver = null;
                if (annotation instanceof Row) {
                    resolver = new XWPFRowResolver(xwpfTable, table, field, (Row) annotation);
                } else if(annotation instanceof Rows) {
                    resolver = new XWPFRowsResolver(xwpfTable, table, field, (Rows) annotation);
                }
                if (resolver != null) resolver.resolve();
            }
        }
    }

}
