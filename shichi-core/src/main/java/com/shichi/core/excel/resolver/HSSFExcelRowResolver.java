package com.shichi.core.excel.resolver;

import com.shichi.core.excel.anno.ERow;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

public class HSSFExcelRowResolver<EO, RF extends Field, RA extends ERow> {

    private EO ro;

    private RF rf;

    private RA ra;

    public HSSFExcelRowResolver(EO ro, RF rf, RA ra) {
        Assert.notNull(ro, "Excel Object should not Be Null!");
        Assert.notNull(rf, "Row Field Object should not Be Null!");
        Assert.notNull(ra, "Row Annotation should not Be Null!");
        this.ro = ro;
        this.rf = rf;
        this.ra = ra;
    }

}
