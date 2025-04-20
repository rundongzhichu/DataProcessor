package com.dp.core.doc.processor;

import com.dp.core.doc.processor.api.AbstractDocFileProcessor;

public class HWPFDocFileProcessor<D> extends AbstractDocFileProcessor<D> {

    private D d;

    public HWPFDocFileProcessor(D d) {
        this.d = d;
    }

    public HWPFDocFileProcessor(D d, String inputFile) {
        this.d = d;
    }


}
