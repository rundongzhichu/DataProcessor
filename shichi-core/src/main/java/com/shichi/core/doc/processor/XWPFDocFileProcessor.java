package com.shichi.core.doc.processor;

import com.shichi.core.doc.anno.Paragraph;
import com.shichi.core.doc.anno.Table;
import com.shichi.core.doc.processor.api.AbstractDocFileProcessor;
import com.shichi.core.doc.resolver.XWPFParagraphResolver;
import com.shichi.core.doc.resolver.XWPFTableResolver;
import com.shichi.core.doc.resolver.api.Resolver;
import com.shichi.core.utils.ReflectUtils;
import org.apache.poi.ss.formula.atp.Switch;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class XWPFDocFileProcessor<D> extends AbstractDocFileProcessor<D> {

    Logger logger = LoggerFactory.getLogger(XWPFDocFileProcessor.class);

    private D d;

    private String inputFile;

    private String outputFile;

    private XWPFDocument xwpfDocument;

    private FileInputStream fis;

    private FileOutputStream fos;

    public XWPFDocFileProcessor(D d) {
        this.d = d;
        this.xwpfDocument = new XWPFDocument();
    }

    public XWPFDocFileProcessor(D d, String inputFile) throws IOException {
        this.d = d;
        fis = new FileInputStream(inputFile);
        this.xwpfDocument = new XWPFDocument(fis);
    }

    public void parseObjToDoc() {
        parseObjToDoc(d);
    }

    public void parseObjToDoc(D docObj) {
        Field[] fields = docObj.getClass().getDeclaredFields();
        for (Field field :
                fields) {
            for (Annotation anno:
                 field.getDeclaredAnnotations()) {
                Resolver resolver = null;
                if(anno instanceof Paragraph) {
                    resolver = new XWPFParagraphResolver(xwpfDocument, docObj, field, (Paragraph) anno);
                } else if(anno instanceof Table) {
                    resolver = new XWPFTableResolver(xwpfDocument, docObj, field, (Table) anno);
                }
                if(resolver != null) {
                    resolver.resolve();
                }
            }
        }
    }

    public void save(String filePath) throws IOException {
        try {
            fos = new FileOutputStream(new File(filePath));
            xwpfDocument.write(fos);
        } catch (FileNotFoundException e) {
            logger.error("Could not find the file!", e);
        } catch (IOException e) {
            logger.error("IOException Occurs!", e);
        } finally {
            fos.close();
        }
    }

    public void close() {
        try {
            if(fis != null) {
                fis.close();
            }

            if(fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            logger.error("IOException Occurs!", e);
        }
    }

}
