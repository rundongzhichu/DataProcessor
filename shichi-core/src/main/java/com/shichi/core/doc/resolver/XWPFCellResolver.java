package com.shichi.core.doc.resolver;

import com.shichi.core.doc.anno.Cell;
import com.shichi.core.doc.anno.Paragraph;
import com.shichi.core.doc.anno.Row;
import com.shichi.core.doc.anno.Rows;
import com.shichi.core.doc.resolver.api.AbstractXWPFResolver;
import com.shichi.core.doc.resolver.api.Resolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.hibernate.query.sqm.tree.SqmStatement;

import javax.lang.model.element.NestingKind;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * word文档单元格的设置
 *
 * @param <C>
 * @param <O>
 * @param <F>
 * @param <A>
 */
public class XWPFCellResolver<C extends XWPFTableRow, O, F extends Field, A extends Cell> extends AbstractXWPFResolver<C, O, F, A> {


    public XWPFCellResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        resolve(getFieldValue());
    }

    public void resolve(Object cell) {
        if(cell == null) {
            return;
        }
        process(cell);
    }

    private void process(Object cell) {
        XWPFTableCell xwpfTableCell = c.createCell();
        xwpfTableCell.removeParagraph(0);
        if(cell instanceof String){
            // cell 单元还可以放表格之类的其他对象
            // 如果cell字段附带了段落属性，那么就是用段落配置文字样式
            Paragraph paragraph = ReflectUtils.getAnnonation(f, Paragraph.class);
            if(paragraph != null) {
                XWPFParagraphResolver  paragraphResolver = new XWPFParagraphResolver(xwpfTableCell, cell, null, paragraph);
                paragraphResolver.resolve(cell);
            } else {
                xwpfTableCell.setText((String) cell);
            }
        } else {
            Field[] fields = cell.getClass().getDeclaredFields();
            for (Field field :
                    fields) {
                for(Annotation annotation: field.getAnnotations()) {
                    if(annotation instanceof  Paragraph) {
                        XWPFParagraphResolver  paragraphResolver = new XWPFParagraphResolver(xwpfTableCell, cell, field, (Paragraph) annotation);
                        paragraphResolver.resolve();
                    }
                }
            }
        }
        setStyle(xwpfTableCell);
    }

    private void setStyle(XWPFTableCell xwpfTableCell) {
        xwpfTableCell.setWidth(a.width());
        xwpfTableCell.setWidthType(a.widthType());
        xwpfTableCell.setVerticalAlignment(a.verticalAlignment());
        xwpfTableCell.setColor(a.color());
    }

}
