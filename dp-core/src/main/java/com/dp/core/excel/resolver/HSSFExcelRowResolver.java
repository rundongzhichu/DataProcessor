package com.dp.core.excel.resolver;

import com.dp.core.excel.anno.ERow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class HSSFExcelRowResolver<EO, RF extends Field, RA extends ERow> {

    Logger logger = LoggerFactory.getLogger(HSSFExcelRowResolver.class);

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

    public void setFieldValue(Object obj, Object value) {
        rf.getType().getName();
        if(rf.getModifiers() != Modifier.PUBLIC) {
            rf.setAccessible(true);
        }
        try {
            rf.set(obj, rf.getType().cast(value));
        } catch (IllegalAccessException e) {
            logger.error("Can not set value", e);
        }
    }

}
