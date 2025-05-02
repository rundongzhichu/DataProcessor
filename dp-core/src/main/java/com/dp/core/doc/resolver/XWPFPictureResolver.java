package com.dp.core.doc.resolver;

import com.dp.core.doc.anno.Picture;
import com.dp.core.doc.resolver.api.AbstractXWPFResolver;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.ContentTypes;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;

public class XWPFPictureResolver <C extends XWPFRun, O, F extends Field, A extends Picture> extends AbstractXWPFResolver<C, O, F, A> {

    Logger logger = LoggerFactory.getLogger(XWPFPictureResolver.class);

    public XWPFPictureResolver(C c, O o, F f, A a) {
        super(c, o, f, a);
    }

    @Override
    public void resolve() {
        switch(a.type()) {
            case XWPFDocument.PICTURE_TYPE_PNG:
            case XWPFDocument.PICTURE_TYPE_JPEG:
                addNormalPic();

        }
    }

    public void addNormalPic() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(a.url());
            XWPFPicture ctPic = c.addPicture(inputStream, a.type(), a.url(), Units.toEMU(a.width()), Units.toEMU(a.height()));
            inputStream.close();
        } catch (FileNotFoundException e) {
            logger.error("image not found!", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            logger.error("picture format is not correct!", e);
            throw new RuntimeException(e);
        }
    }

}
