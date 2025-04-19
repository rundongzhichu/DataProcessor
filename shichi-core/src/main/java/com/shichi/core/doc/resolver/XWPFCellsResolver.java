package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Cell;
import com.shichi.core.doc.model.XWPFTableRowModel;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.lang.reflect.Field;

public class XWPFCellsResolver<C extends XWPFTableRow, O extends XWPFTableRowModel, F extends Field, A extends Cell> extends AbstractXWPFResolver<C, O, F, A> {

    public XWPFCellsResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {

    }
}
