package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Cell;
import com.shichi.core.doc.anno.Cells;
import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.doc.resolver.api.Resolver;
import org.apache.poi.xwpf.usermodel.TableRowHeightRule;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

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
        process(rowObj);
    }

    private void process(Object obj) {
        XWPFTableRow xwpfTableRow = c.createRow();
        // 清空所有的cell 重新添加
        // 反向遍历并移除单元格（避免索引问题）
        for (int i = xwpfTableRow.getTableCells().size() - 1; i >= 0; i--) {
            xwpfTableRow.removeCell(i);
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field: fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                Resolver resolver = null;
                if (annotation instanceof Cell) {
                    resolver = new XWPFCellResolver(xwpfTableRow, obj, field, (Cell) annotation);
                } else if (annotation instanceof Cells) {
                    resolver = new XWPFCellsResolver(xwpfTableRow, obj, field, (Cells) annotation);
                }
                if (resolver != null) resolver.resolve();
            }
        }
        setStyle(xwpfTableRow);
    }

    private void setStyle(XWPFTableRow xwpfTableRow) {
        xwpfTableRow.setRepeatHeader(a.repeatHeader());
        xwpfTableRow.setHeightRule(a.heightRule());
        xwpfTableRow.setHeight(a.height());
        xwpfTableRow.setCantSplitRow(a.canSplitRow());
    }

}
