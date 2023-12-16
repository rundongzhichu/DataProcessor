package com.shichi.core.doc.processor;

import com.shichi.core.doc.anno.Doc;
import com.shichi.core.doc.processor.api.DocFileProcessor;
import com.shichi.core.doc.resolver.DocResolver;
import com.shichi.core.utils.ReflectUtils;

public class DocProcessorFactory {

    public static <D> DocFileProcessor<D> getProcessor(D d) {
        DocResolver docResolver = new DocResolver(ReflectUtils.getAnnonation(d.getClass(), Doc.class), d);
        return docResolver.getProcessor();
    }

}
