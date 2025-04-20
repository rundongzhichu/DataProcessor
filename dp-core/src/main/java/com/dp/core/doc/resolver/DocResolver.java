package com.dp.core.doc.resolver;

import com.dp.core.doc.processor.XWPFDocFileProcessor;
import com.dp.core.doc.processor.api.DocFileProcessor;
import com.dp.core.doc.anno.Doc;
import com.dp.core.doc.processor.HWPFDocFileProcessor;
import org.springframework.util.Assert;

public class DocResolver<DA extends Doc, D> {

    private DA da;

    private D d;

    public DocResolver(DA da, D d) {
        Assert.notNull(da, "Doc Annotation should not Be Null!");
        this.da = da;
        this.d = d;
    }

    public DocFileProcessor<D> getProcessor() {
        switch (da.type()) {
            case DOC:
                return new HWPFDocFileProcessor<D>(this.d);
            case DOCX:
                return new XWPFDocFileProcessor<D>(this.d);
        }
        return null;
    }

}
