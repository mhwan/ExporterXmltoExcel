package FileManage;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mhwan on 2016. 6. 12..
 */
public class FileInputOutput {
    public String readXmlFile(String path) {
        FileReader fileReader;
        BufferedReader bufferedReader;
        String result = "";

        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean writeExcelFile(ArrayList<String> keylist, Map<String, Object> valuesmap, ArrayList<String> titlelist, String savepath) {
        boolean result = true;
        try {
            FileOutputStream outputStream = new FileOutputStream(savepath);

            if (savepath.endsWith(".xlsx")) {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet();
                XSSFRow row = sheet.createRow(0);
                XSSFCell cell = null;
                for (int i = 0; i < titlelist.size(); i++) {
                    cell = row.createCell(i);
                    cell.setCellStyle(makeNEWCellStyle(workbook));
                    cell.setCellValue(titlelist.get(i));
                }

                for (int i = 0; i < keylist.size(); i++) {
                    row = sheet.createRow(i + 1);
                    String key = keylist.get(i);

                    for (int j = 0; j < titlelist.size(); j++) {
                        cell = row.createCell(j);
                        if (j == 0)
                            cell.setCellValue(key);
                        else {
                            ArrayList<Object> values = (ArrayList<Object>) valuesmap.get(key);
                            String value = (String) values.get(j-1);
                            cell.setCellValue(value.replaceAll("\\\\'", "'"));
                        }
                    }
                }

                for(int colNum = 0; colNum<row.getLastCellNum(); colNum++)
                    sheet.autoSizeColumn(colNum);

                workbook.write(outputStream);
            }

            else if (savepath.endsWith(".xls")) {
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet();
                HSSFRow row = sheet.createRow(0);
                HSSFCell cell = null;
                for (int i = 0; i < titlelist.size(); i++) {
                    cell = row.createCell(i);
                    cell.setCellStyle(makeOLDCellStyle(workbook));
                    cell.setCellValue(titlelist.get(i));
                }

                for (int i = 0; i < keylist.size(); i++) {
                    row = sheet.createRow(i + 1);
                    String key = keylist.get(i);

                    for (int j = 0; j < titlelist.size(); j++) {
                        cell = row.createCell(j);
                        if (j == 0)
                            cell.setCellValue(key);
                        else {
                            ArrayList<Object> values = (ArrayList<Object>) valuesmap.get(key);
                            String value = (String) values.get(j-1);
                            cell.setCellValue(value.replaceAll("\\\\'", "'"));
                        }
                    }
                }

                for(int colNum = 0; colNum<row.getLastCellNum(); colNum++)
                    sheet.autoSizeColumn(colNum);

                workbook.write(outputStream);
            }

            outputStream.close();
        } catch (FileNotFoundException e) {
            result = false;
            e.printStackTrace();
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private XSSFCellStyle makeNEWCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle titlestyle = workbook.createCellStyle();
        titlestyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        titlestyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        titlestyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        titlestyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titlestyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titlestyle.setBorderBottom(CellStyle.BORDER_MEDIUM);

        return titlestyle;
    }

    private HSSFCellStyle makeOLDCellStyle (HSSFWorkbook workbook) {
        HSSFCellStyle titlestyle = workbook.createCellStyle();
        titlestyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titlestyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        titlestyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        titlestyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        titlestyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        titlestyle.setBorderBottom(CellStyle.BORDER_MEDIUM);

        return titlestyle;
    }
}
