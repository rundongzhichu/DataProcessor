package com.shichi.core.doc.processor;

import com.shichi.core.excel.processor.HSSFExcelProcessor;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class XWPFDocFileProcessor<D> extends DefaultDocFileProcessor<D> {

    Logger logger = LoggerFactory.getLogger(XWPFDocFileProcessor.class);

    private D d;

    private XWPFDocument xwpfDocument;

    public XWPFDocFileProcessor(D d) {
        this.d = d;
        this.xwpfDocument = new XWPFDocument();
    }

    public XWPFDocFileProcessor(D d, String inputFile) throws IOException {
        this.d = d;
        FileInputStream fis = new FileInputStream(inputFile);
        this.xwpfDocument = new XWPFDocument(fis);
    }

    public void parseObjToDoc() {
        parseObjToDoc(d);
    }

    public void parseObjToDoc(D docObj) {
        ReflectUtils.getAnnonation(docObj, )

    }



    //    代码参考：
    //    https://www.pudn.com/news/62615ba80e75e42012407364.html

    /**
     * 替换段落里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量 ： 关键方法
     * @param para 要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i < runs.size(); i++) {
                //System.out.println("字体选择："+runs.get(i).getFontFamily());
                //System.out.println("字体大小："+runs.get(i).getFontSize());
                //System.out.println("字体权重："+runs.get(i).getColor());
                //要把样式还回去（目前考虑到的是字体选择和大小）：
                XWPFRun run = runs.get(i);
                //后续会被删掉，要提前拿出来
                String fontFamily = run.getFontFamily();
                int fontSize = run.getFontSize();
                String color = run.getColor();
                int kerning = run.getKerning();
                int characterSpacing = run.getCharacterSpacing();
                boolean bold = run.isBold();

                String runText = run.toString();
                matcher = this.matcher(runText);
                if (matcher.find()) {
                    while ((matcher = this.matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    XWPFRun newRun = para.insertNewRun(i);
                    newRun.setText(runText);
                    newRun.setFontFamily(fontFamily);
                    newRun.setFontSize(fontSize);
                    newRun.setColor(color);
                    //字距，暂不知道区别
                    newRun.setKerning(kerning);
                    //字符间距，字间距
                    newRun.setCharacterSpacing(characterSpacing);
                    //是否是粗体
                    newRun.setBold(bold);
                }
            }
        }
    }
    /**
     * 替换表格里面的变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }
    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
