package com.dp.core.doc.processor;

import com.dp.core.doc.processor.api.DocFileProcessor;
import com.dp.core.doc.anno.Doc;
import com.dp.core.doc.resolver.DocResolver;
import com.dp.core.utils.ReflectUtils;

public class DocProcessorFactory {

    public static <D> DocFileProcessor<D> getProcessor(D d) {
        DocResolver docResolver = new DocResolver(ReflectUtils.getAnnonation(d.getClass(), Doc.class), d);
        return docResolver.getProcessor();
    }

}
