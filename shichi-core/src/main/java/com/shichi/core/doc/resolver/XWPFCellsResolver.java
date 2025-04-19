package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Cell;
import com.shichi.core.doc.anno.Cells;
import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.lang.reflect.Field;
import java.util.List;

public class XWPFCellsResolver<C extends XWPFTableRow, O, F extends Field, A extends Cells> extends AbstractXWPFResolver<C, O, F, A> {

    public XWPFCellsResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        resolve(getFieldValue());
    }

    public void resolve(Object cells) {
        if(cells == null) {
            return;
        }
        if(cells instanceof List<?>) {
            process((List<?>) cells);
        } else {
            throw new RuntimeException("cells obj should be a List!");
        }
    }

    private void process(List<?> cells) {
        for (Object cell:
                cells) {
            Cell cellAnno = ReflectUtils.getAnnonation(cell.getClass(), Cell.class);
            if(cellAnno != null) {
                XWPFCellResolver resolver = new XWPFCellResolver<>(c, cell, null, cellAnno);
                resolver.resolve(cell);
            }
        }
    }

}
