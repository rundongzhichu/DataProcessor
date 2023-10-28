package com.shichi.core.excel.resolver;

import com.shichi.core.excel.anno.ECell;
import com.shichi.core.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class HSSFExcelCellResolver<RO, CF extends Field, CA extends ECell> {

    Logger logger = LoggerFactory.getLogger(HSSFExcelCellResolver.class);

    private RO ro;

    private CF cf;

    private CA ca;

    public HSSFExcelCellResolver(RO ro, CF cf, CA ca) {
        Assert.notNull(ro, "Row Object should not Be Null!");
        Assert.notNull(cf, "Cell Field Object should not Be Null!");
        Assert.notNull(ca, "Cell Annotation should not Be Null!");
        this.ro = ro;
        this.cf = cf;
        this.ca = ca;
    }

    public Object getFieldValue() {
        if(cf.getModifiers() != Modifier.PUBLIC) {
            cf.setAccessible(true);
        }
        try {
            return cf.get(ro);
        } catch (IllegalAccessException e) {
            logger.error("Can not get vaule", e);
        }
        return null;
    }

    public int getColumnIndex() {
        return ca.index();
    }

}
