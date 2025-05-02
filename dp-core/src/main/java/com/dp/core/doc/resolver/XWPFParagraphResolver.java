package com.dp.core.doc.resolver;

import com.dp.core.doc.anno.Picture;
import com.dp.core.doc.resolver.api.AbstractXWPFResolver;
import com.dp.core.doc.anno.Paragraph;
import com.dp.core.doc.resolver.api.Resolver;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author shoucai
 */
public class XWPFParagraphResolver<C, O, F extends Field, A extends Paragraph> extends AbstractXWPFResolver<C, O, F, A> {

    Logger logger = LoggerFactory.getLogger(XWPFParagraphResolver.class);

    public XWPFParagraphResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        resolve(getFieldValue());
    }

    public void resolve(Object paragraph) {
        if (paragraph == null) return;
        if (paragraph instanceof String) {
            processFormatStrParagraph((String) paragraph);
        } else if (paragraph instanceof List) {
            for (Object seg : (List) paragraph) {
                resolve((String) seg);
            }
        }
    }

    private void processFormatStrParagraph(String text) {
        XWPFParagraph xwpfParagraph = createParagraph();
        xwpfParagraph.removeRun(0);
        setStyle(xwpfParagraph);

        XWPFRun xwpfRun = xwpfParagraph.createRun();
        setStyle(xwpfRun);
        xwpfRun.setText(text);
        if(f != null) {
            Annotation[] annotations = f.getDeclaredAnnotations();
            Resolver resolver = null;
            for (Annotation anno : annotations) {
                if(anno instanceof Picture) {
                    xwpfRun.addBreak(BreakType.TEXT_WRAPPING);
                    resolver = new XWPFPictureResolver<>(xwpfRun, o, f, (Picture) anno);
                }
                if(resolver != null) {
                    resolver.resolve();
                }
            }
        }
    }

    /**
     * 设置段落字体
     * @param xwpfRun
     */
    protected void setStyle(XWPFRun xwpfRun) {
        xwpfRun.setBold(a.bold());
        xwpfRun.setItalic(a.italic());
        xwpfRun.setStrikeThrough(a.strikeThrough());
        xwpfRun.setTextPosition(a.textPosition());
        xwpfRun.setFontSize(a.fontSize());
    }

    /**
     * 设置段落的边框
     * @param xwpfParagraph
     */
    private void setStyle(XWPFParagraph xwpfParagraph) {
        xwpfParagraph.setBorderTop(a.borderTop());
        xwpfParagraph.setBorderBottom(a.borderBottom());
        xwpfParagraph.setBorderLeft(a.borderLeft());
        xwpfParagraph.setBorderRight(a.borderRight());
        xwpfParagraph.setBorderBetween(a.borderBetween());
        xwpfParagraph.setAlignment(a.paragraphAlignment());
    }

    private XWPFParagraph createParagraph() {
        if (c instanceof XWPFHeader) {

            return ((XWPFHeader) c).createParagraph();
        }
        else if (c instanceof XWPFDocument) {
            return ((XWPFDocument) c).createParagraph();
        } else if (c instanceof XWPFTableCell){
            return ((XWPFTableCell) c).addParagraph();
        } else
            logger.error("Unsupported XWPF Container type!");
        return null;
    }

}
