package com.shichi.core.doc.processor;

import com.shichi.core.doc.processor.api.AbstractDocFileProcessor;

public class HWPFDocFileProcessor<D> extends AbstractDocFileProcessor<D> {

    private D d;

    public HWPFDocFileProcessor(D d) {
        this.d = d;
    }

    public HWPFDocFileProcessor(D d, String inputFile) {
        this.d = d;
    }


}
