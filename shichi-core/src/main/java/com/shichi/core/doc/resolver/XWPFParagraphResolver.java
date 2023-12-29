package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Paragraph;
import com.shichi.core.doc.model.XWPFParagraphModel;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        } else if (paragraph instanceof XWPFParagraphModel) {
            processXWPFParagraphModel((XWPFParagraphModel) paragraph);
        } else if (paragraph instanceof List) {
            for (Object seg : (List) paragraph) {
                resolve(seg);
            }
        }
    }

    private void processXWPFParagraphModel(XWPFParagraphModel xwpfParagraphModel) {
        XWPFParagraph xwpfParagraph = createParagraph();
        setBorders(xwpfParagraph, xwpfParagraphModel, xwpfParagraphModel.isAnnoFirst());

        XWPFRun xwpfRun = xwpfParagraph.createRun();
        setFont(xwpfRun);
        xwpfRun.setText(xwpfParagraph.getText());
    }

    private void processFormatStrParagraph(String text) {
        XWPFParagraph xwpfParagraph = createParagraph();
        setBorders(xwpfParagraph);

        XWPFRun xwpfRun = xwpfParagraph.createRun();
        setFont(xwpfRun);
        xwpfRun.setText(text);
    }

    protected void setFont(XWPFRun xwpfRun) {
        xwpfRun.setBold(a.bold());
        xwpfRun.setItalic(a.italic());
        xwpfRun.setStrikeThrough(a.strikeThrough());
        xwpfRun.setTextPosition(a.textPosition());
        xwpfRun.setFontSize(a.fontSize());
    }

    private void setBorders(XWPFParagraph xwpfParagraph) {
        setBorders(xwpfParagraph, null, true);
    }

    private void setBorders(XWPFParagraph xwpfParagraph, XWPFParagraphModel xwpfParagraphModel, boolean annoFirst) {
        if (!annoFirst && xwpfParagraphModel != null) {
            setBorders(xwpfParagraph, xwpfParagraphModel.getBorders());
            return;
        }

        setBorders(xwpfParagraph, a.borders());
        if (a.borderTop() != null) {
            xwpfParagraph.setBorderTop(a.borderTop());
        }
        if (a.borderBottom() != null) {
            xwpfParagraph.setBorderBottom(a.borderBottom());
        }
        if (a.borderLeft() != null) {
            xwpfParagraph.setBorderLeft(a.borderLeft());
        }
        if (a.borderRight() != null) {
            xwpfParagraph.setBorderRight(a.borderRight());
        }
        if (a.borderBetween() != null) {
            xwpfParagraph.setBorderBetween(a.borderBetween());
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

    private XWPFParagraph createParagraph() {
        if (c instanceof XWPFHeader)
            return ((XWPFHeader) c).createParagraph();
        else if (c instanceof XWPFDocument)
            return ((XWPFDocument) c).createParagraph();
        else
            logger.error("Unsupported XWPF Container type!");
        return null;
    }

}
