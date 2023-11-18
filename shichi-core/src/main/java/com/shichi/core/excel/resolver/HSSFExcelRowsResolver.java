package com.shichi.core.excel.resolver;

import com.shichi.core.excel.anno.ERows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class HSSFExcelRowsResolver<EO, RSF extends Field, RSA extends ERows> {

    Logger logger = LoggerFactory.getLogger(HSSFExcelRowsResolver.class);

    private EO eo;

    private RSF rsf;

    private RSA rsa;

    public HSSFExcelRowsResolver(EO eo, RSF rsf, RSA rsa) {
        Assert.notNull(eo, "Excel Object should not Be Null!");
        Assert.notNull(rsf, "Rows Field Object should not Be Null!");
        Assert.notNull(rsa, "Rows Annotation should not Be Null!");
        this.eo = eo;
        this.rsf = rsf;
        this.rsa = rsa;
    }

    public void setFieldValue(Object obj, Object value) {
        rsf.getType().getName();
        if(rsf.getModifiers() != Modifier.PUBLIC) {
            rsf.setAccessible(true);
        }
        try {
            rsf.set(obj, rsf.getType().cast(value));
        } catch (IllegalAccessException e) {
            logger.error("Can not set value", e);
        }
    }

}
