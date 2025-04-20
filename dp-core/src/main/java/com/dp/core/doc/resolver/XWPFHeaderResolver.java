package com.dp.core.doc.resolver;

import com.dp.core.doc.anno.Header;
import com.dp.core.doc.anno.Paragraph;
import com.dp.core.doc.resolver.api.AbstractXWPFResolver;
import com.dp.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

import java.lang.reflect.Field;

public class XWPFHeaderResolver<C extends XWPFDocument, O, F extends Field, A extends Header> extends AbstractXWPFResolver<C, O, F, A > {

    public XWPFHeaderResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        XWPFHeader header = c.createHeader(a.relation());
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field :
                fields) {
            Paragraph paragraphAnno = ReflectUtils.getAnnonation(field, Paragraph.class);
            XWPFParagraphResolver XWPFParagraphResolver = new XWPFParagraphResolver(header, o, field, paragraphAnno);
            XWPFParagraphResolver.resolve();
        }
    }
}
