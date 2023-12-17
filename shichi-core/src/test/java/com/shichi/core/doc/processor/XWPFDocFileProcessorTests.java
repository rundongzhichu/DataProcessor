package com.shichi.core.doc.processor;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class XWPFDocFileProcessorTests {

    @Test
    public void testXWPFDocProcessor() throws IOException {
        XWPFDocFileObj xwpfDocFileObj = new XWPFDocFileObj();
        XWPFDocFileProcessor xwpfDocFileProcessor = (XWPFDocFileProcessor) DocProcessorFactory.getProcessor(xwpfDocFileObj);
        xwpfDocFileProcessor.parseObjToDoc();
        xwpfDocFileProcessor.save("XWPFDocFileObj.docx");
    }

}
