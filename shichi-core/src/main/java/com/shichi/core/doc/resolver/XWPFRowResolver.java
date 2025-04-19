package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Cell;
import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.model.XWPFParagraphModel;
import com.shichi.core.doc.model.XWPFTableRowModel;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.doc.resolver.api.Resolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class XWPFRowResolver<C extends XWPFTable, O, F extends Field, A extends Row> extends AbstractXWPFResolver<C, O, F, A> {

    public XWPFRowResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        resolve(getFieldValue());
    }

    public void resolve(Object rowObj) {
        if (rowObj == null) return;
        if (rowObj instanceof XWPFTableRowModel) {
            processXWPFTableRowModel((XWPFTableRowModel) rowObj);
        } else {
            process(rowObj);
        }
    }

    private void process(Object obj) {
        XWPFTableRow xwpfTableRow = c.createRow();
        Field[] fields = obj.getClass().getFields();
        for (Field field: fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                Resolver resolver = null;
                if (annotation instanceof Cell) {
                    resolver = new XWPFCellResolver(xwpfTableRow, obj, field, (Cell) annotation);
                }
                if (resolver != null) resolver.resolve();
            }
        }
    }


    public void processXWPFTableRowModel(XWPFTableRowModel xwpfTableRowModel) {
        XWPFTableRow xwpfTableRow = c.createRow();
        Field[] fields = xwpfTableRowModel.getClass().getDeclaredFields();
        for (Field field: fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                Resolver resolver = null;
                if(annotation instanceof Cell) {
                    resolver = new XWPFCellResolver(xwpfTableRow, xwpfTableRowModel, field, (Cell) annotation);
                }
                if(resolver != null) resolver.resolve();
            }
        }
    }


}
