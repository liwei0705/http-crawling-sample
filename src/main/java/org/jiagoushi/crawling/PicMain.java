package org.jiagoushi.crawling;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.jiagoushi.crawling.common.Crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传到云文档的图片集合下载到本地的例子（例如健康码截图收集）
 */
public class PicMain {

    public static void main(String[] args) {

        //下载的文档（目前使用HSSF，只支持Office2007以前的文档，xls格式。可以升级到XSSF）
        List<ImageListXlsDataLine> lines = readXls(new File("D://test.xls"));

        try {
            for(XlsDataLine line : lines){
                ImageListXlsDataLine lineNew = (ImageListXlsDataLine) line;
                String picUrl = lineNew.getPicUrl();
                String dir = "D://test//";
                String fileName = lineNew.getName() + ".png";
                Crawler.crawlPicture(picUrl, dir, fileName);
                Thread.sleep(3500);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected static List<ImageListXlsDataLine> readXls(File xlsFile) {
        InputStream is = null;
        HSSFWorkbook hssfWorkbook = null;
        try {
            is = new FileInputStream(xlsFile);
            hssfWorkbook = new HSSFWorkbook(is);
            System.out.println("开始读取文件：" + xlsFile.getName());
            List<ImageListXlsDataLine> list = readLines(hssfWorkbook);

            if (null != hssfWorkbook) {
                hssfWorkbook.close();
            }

            is = null;
            hssfWorkbook = null;

            return list;
        } catch (Exception e) {
            System.out.println("文件操作失败" + xlsFile.getName() + e.getMessage());
            return null;
        }
    }

    protected static List<ImageListXlsDataLine> readLines(HSSFWorkbook hssfWorkbook){
        List<ImageListXlsDataLine> list = new ArrayList<>();
        ImageListXlsDataLine dataLine = null;
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            HSSFRow hssfRowHeader = hssfSheet.getRow(0);
            //header不处理

            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {

                    dataLine = new ImageListXlsDataLine();
                    dataLine.setName(getValue(hssfRow, 2));//姓名，第三列
                    dataLine.setPicUrl(getLink(hssfRow, 3));//图片超链接，第四列
                    if(null == dataLine.getName() || dataLine.getName().length() < 1){
                        break;//没有数据了，中止循环
                    }
                    list.add(dataLine);
                }
            }
        }

        return list;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    static class ImageListXlsDataLine extends XlsDataLine{
        //姓名
        private String name;
        //图片地址（云服务器）
        private String picUrl;
    }

    /**
     * Excel数据行的基类
     *
     */
    @Data
    public static class XlsDataLine {
        //用于继承
    }

    /**
     * 获取一个Cell的值
     * @param hssfRow
     * @param number
     * @return
     */
    protected static String getValue(HSSFRow hssfRow, int number) {

        HSSFCell hssfCell = hssfRow.getCell(number);

        if(null == hssfCell){
            return "";
        }
        if (hssfCell.getCellType() == CellType.BOOLEAN) {
            // 返回布尔类型的值
            String result = String.valueOf(hssfCell.getBooleanCellValue());
            return result;
        } else if (hssfCell.getCellType() == CellType.NUMERIC) {
            // 返回数值类型的值
            double dbl = hssfCell.getNumericCellValue();
            //去除科学记数法表示
            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            String result = nf.format(dbl);

            return result;
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**
     * 获取一个Cell的超链接值
     * @param hssfRow
     * @param number
     * @return
     */
    protected static String getLink(HSSFRow hssfRow, int number) {

        HSSFCell hssfCell = hssfRow.getCell(number);

        if(null == hssfCell){
            return "";
        }
        try{
            return hssfCell.getHyperlink().getAddress();
        }catch (Exception ex){
            return "error" + ex.getMessage();
        }

    }
}
