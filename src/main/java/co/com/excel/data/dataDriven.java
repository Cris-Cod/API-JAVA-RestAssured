package co.com.excel.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class dataDriven {

    //Identify Testcases column by scanning the entire 1st row

    public ArrayList<String> getData(String testName) throws IOException {
        ArrayList<String> datos = new ArrayList<String>();

        FileInputStream fis = new FileInputStream("data.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("testdata")) {
                XSSFSheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rows = sheet.iterator();  // sheet colletion of rows
                Row firstRow = rows.next();
                Iterator<Cell> cell = firstRow.cellIterator(); // rows colletion of cells
                int k = 0;
                int column = 0;
                while (cell.hasNext()) {
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Testcases")) {
                        column = k;
                    }
                    k++;
                }
                System.out.println(column);

                while (rows.hasNext()) {
                    Row r = rows.next();
                    if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testName)) {
                        Iterator<Cell> cv = r.cellIterator();
                        while (cv.hasNext()) {
                            datos.add(cv.next().getStringCellValue());
                        }
                    }
                }
            }


        }

        return datos;
    }
}
