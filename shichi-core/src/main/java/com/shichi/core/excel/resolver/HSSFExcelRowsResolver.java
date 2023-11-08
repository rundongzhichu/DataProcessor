package com.shichi.core.excel.resolver;

import com.shichi.core.excel.anno.ERows;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

public class HSSFExcelRowsResolver<EO, RSF extends Field, RSA extends ERows> {

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

}
