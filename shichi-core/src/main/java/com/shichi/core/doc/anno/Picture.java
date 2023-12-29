package com.shichi.core.doc.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Picture {

//    import org.apache.poi.xwpf.usermodel.*;
//import java.io.*;
//
//    public class WordToPdfWithImage {
//        public static void main(String[] args) throws Exception {
//            // 1. 创建一个新的空白的Word文档对象
//            XWPFDocument document = new XWPFDocument();
//
//            // 2. 打开或者创建一个现有的Word文档
//            FileInputStream fileInput = new FileInputStream("input.docx");
//            document = new XWPFDocument(fileInput);
//
//            // 3. 获取Word文档的内容
//            for (XWPFParagraph paragraph : document.getParagraphs()) {
//                System.out.println(paragraph.getText());
//
//                // 4. 在段落中插入图片
//                String imagePath = "image.jpg";
//                InputStream inputStream = new FileInputStream(new File(imagePath));
//                int format = DocumentFormat.IMAGE_JPEG;
//                byte[] bytes = IOUtils.toByteArray(inputStream);
//                int pictureType = XWPFDocument.PICTURE_TYPE_JPEG;
//                int widthUnits = Units.EMU_PER_INCH * 80 / 96;
//                int heightUnits = Units.EMU_PER_INCH * 60 / 96;
//                int widthInches = 80;
//                int heightInches = 60;
//                int id = document.createPictureData(bytes).getId();
//                CTPicture ctPicture = paragraph.createRun().getCTR().addNewDrawing().addNewInline().addNewGraphic().addNewGraphicData().addNewPic().addNewNvPicPr().addNewCNvPr().setName("image" + id);
//                CTNonVisualDrawingProps nvdpr = ctPicture.getNvPicPr().addNewCNvPr();
//                nvdpr.setDescr("Description of the Picture");
//                nvdpr.setTitle("Title of the Picture");
//                CTBlipFillProperties blipProp = ctPicture.addNewBlipFill().addNewBlip().addNewEmbed();
//                blipProp.setDpi(dpi);
//                blipProp.setCstate(STRelationshipId.fromInt(id));
//                CTTcPr tcPr = ctPicture.addNewTcPr();
//                CTRelSize relSize = tcPr.addNewHMerge().addNewFrom().addNewEa().addNewF().addNewLim().addNewNumRef().addNewNumCacheDefByFormula().addNewPTab().addNewTabStopList().addNewTabStop().addNewPos().setVal(BigInteger.valueOf((long)(widthUnits)));
//                CTRelSize relSizeHeight = tcPr.addNewVMerge().addNewFrom().addNewEa().addNewF().addNewLim().addNewNumRef().addNewNumCacheDefByFormula().addNewPTab().addNewTabStopList().addNewTabStop().addNewPos().setVal(BigInteger.valueOf((long)(heightUnits)));
//                C

}
