package com.shichi.core.doc.model;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.VerticalAlign;

public class XWPFParagraphModel {

    private Borders[] borders;
    private Borders borderTop;
    private Borders borderBottom;
    private Borders borderLeft;
    private Borders borderRight;
    private Borders borderBetween;
    private boolean bold;
    private boolean italic;
    private boolean strikeThrough;
    private int textPosition;
    private int fontSize;
    private VerticalAlign verticalAlign;
    private String text;
    private boolean annoFirst;

    public Borders[] getBorders() {
        return borders;
    }

    public void setBorders(Borders[] borders) {
        this.borders = borders;
    }

    public Borders getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(Borders borderTop) {
        this.borderTop = borderTop;
    }

    public Borders getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(Borders borderBottom) {
        this.borderBottom = borderBottom;
    }

    public Borders getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(Borders borderLeft) {
        this.borderLeft = borderLeft;
    }

    public Borders getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(Borders borderRight) {
        this.borderRight = borderRight;
    }

    public Borders getBorderBetween() {
        return borderBetween;
    }

    public void setBorderBetween(Borders borderBetween) {
        this.borderBetween = borderBetween;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isStrikeThrough() {
        return strikeThrough;
    }

    public void setStrikeThrough(boolean strikeThrough) {
        this.strikeThrough = strikeThrough;
    }

    public int getTextPosition() {
        return textPosition;
    }

    public void setTextPosition(int textPosition) {
        this.textPosition = textPosition;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public void setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAnnoFirst() {
        return annoFirst;
    }

    public void setAnnoFirst(boolean annoFirst) {
        this.annoFirst = annoFirst;
    }
}
