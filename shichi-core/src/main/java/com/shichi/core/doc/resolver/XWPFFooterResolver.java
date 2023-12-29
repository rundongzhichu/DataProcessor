package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Header;
import com.shichi.core.doc.anno.Paragraph;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;

import java.lang.reflect.Field;

public class XWPFFooterResolver<C extends XWPFDocument, O, F extends Field, A extends Header> extends AbstractXWPFResolver<C, O, F, A > {

    public XWPFFooterResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        XWPFFooter footer = c.createFooter(a.relation());
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field :
                fields) {
            Paragraph paragraphAnno = ReflectUtils.getAnnonation(field, Paragraph.class);
            XWPFParagraphResolver XWPFParagraphResolver = new XWPFParagraphResolver(footer, o, field, paragraphAnno);
            XWPFParagraphResolver.resolve();
        }
    }

}
