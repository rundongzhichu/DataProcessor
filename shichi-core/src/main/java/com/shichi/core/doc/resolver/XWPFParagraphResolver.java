package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Paragraph;
import com.shichi.core.doc.model.XWPFParagraphModel;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author shoucai
 */
public class XWPFParagraphResolver<O, PF extends Field, PA extends Paragraph>  {

    Logger logger = LoggerFactory.getLogger(XWPFParagraphResolver.class);

    private XWPFDocument xwpfDocument;

    private O o;

    private PF pf;

    private PA pa;

    public XWPFParagraphResolver(XWPFDocument xwpfDocument, O o, PF pf, PA pa) {
        Assert.notNull(xwpfDocument, "xwpfDocument should not be null!");
        Assert.notNull(o, "Doc Object should not Be Null!");
        Assert.notNull(pf, "Paragraph Field Object should not Be Null!");
        Assert.notNull(pa, "Paragraph Annotation should not Be Null!");
        this.xwpfDocument = xwpfDocument;
        this.o = o;
        this.pf = pf;
        this.pa = pa;
    }

    public void resolve() {
        resolve(getFieldValue());
    }

    public void resolve(Object paragraph) {
        if(paragraph == null) return;
        if(paragraph instanceof String) {
            processFormatStrParagraph((String) paragraph);
        } else if (paragraph instanceof XWPFParagraphModel) {
            processXWPFParagraphModel((XWPFParagraphModel) paragraph);
        } else if(paragraph instanceof List) {
            for (Object seg : (List)paragraph) {
                resolve(seg);
            }
        }
    }

    private void processXWPFParagraphModel(XWPFParagraphModel xwpfParagraphModel) {
        XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
        setBorders(xwpfParagraph, xwpfParagraphModel, xwpfParagraphModel.isAnnoFirst());

        XWPFRun xwpfRun = xwpfParagraph.createRun();
        setFont(xwpfRun);
        xwpfRun.setText(xwpfParagraph.getText());
    }

    private void processFormatStrParagraph(String text){
        XWPFParagraph xwpfParagraph = xwpfDocument.createParagraph();
        setBorders(xwpfParagraph);

        XWPFRun xwpfRun = xwpfParagraph.createRun();
        setFont(xwpfRun);
        xwpfRun.setText(text);
    }

    private void setFont(XWPFRun xwpfRun) {
        xwpfRun.setBold(pa.bold());
        xwpfRun.setItalic(pa.italic());
        xwpfRun.setStrikeThrough(pa.strikeThrough());
        xwpfRun.setTextPosition(pa.textPosition());
        xwpfRun.setFontSize(pa.fontSize());
    }

    private void setBorders(XWPFParagraph xwpfParagraph) {
        setBorders(xwpfParagraph, null, true);
    }

    private void setBorders(XWPFParagraph xwpfParagraph, XWPFParagraphModel xwpfParagraphModel, boolean annoFirst) {
        if(!annoFirst  && xwpfParagraphModel != null) {
            setBorders(xwpfParagraph, xwpfParagraphModel.getBorders());
            return;
        }

        setBorders(xwpfParagraph, pa.borders());
        if(pa.borderTop() != null) {
            xwpfParagraph.setBorderTop(pa.borderTop());
        }
        if(pa.borderBottom() != null) {
            xwpfParagraph.setBorderBottom(pa.borderBottom());
        }
        if(pa.borderLeft() != null) {
            xwpfParagraph.setBorderLeft(pa.borderLeft());
        }
        if(pa.borderRight() != null) {
            xwpfParagraph.setBorderRight(pa.borderRight());
        }
        if(pa.borderBetween() != null) {
            xwpfParagraph.setBorderBetween(pa.borderBetween());
        }
    }

    private void setBorders(XWPFParagraph xwpfParagraph, Borders[] borders) {
        int bordersLen = borders.length;
        xwpfParagraph.setBorderTop(bordersLen > 0 ? borders[0] : Borders.NONE);
        xwpfParagraph.setBorderBottom(bordersLen > 1 ? borders[1] : Borders.NONE);
        xwpfParagraph.setBorderLeft(bordersLen > 2 ? borders[2] : Borders.NONE);
        xwpfParagraph.setBorderRight(bordersLen > 3 ? borders[3] : Borders.NONE);
        xwpfParagraph.setBorderBetween(bordersLen > 4 ? borders[4] : Borders.NONE);
    }

    public Object getFieldValue() {
        if(pf.getModifiers() != Modifier.PUBLIC) {
            pf.setAccessible(true);
        }
        try {
            return pf.get(o);
        } catch (IllegalAccessException e) {
            logger.error("Can not get vaule", e);
        }
        return null;
    }


    public void setFieldValue(Object value) {
        if(pf.getModifiers() != Modifier.PUBLIC) {
            pf.setAccessible(true);
        }
        try {
            pf.set(o, pf.getType().cast(value));
        } catch (IllegalAccessException e) {
            logger.error("Can not set value", e);
        }
    }

}
