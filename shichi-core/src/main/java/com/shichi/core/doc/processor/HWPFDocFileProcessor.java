package com.shichi.core.doc.processor;

public class HWPFDocFileProcessor<D> extends DefaultDocFileProcessor<D> {

    private D d;

    public HWPFDocFileProcessor(D d) {
        this.d = d;
    }

    public HWPFDocFileProcessor(D d, String inputFile) {
        this.d = d;
    }


}
