package uz.pdp.apptalababot.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uz.pdp.apptalababot.model.Talaba;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ExcelServiceImpl implements ExcelService {
    private static ExcelService instance;
    private ExcelServiceImpl(){

    }

    public static ExcelService getInstance() {
        if (Objects.isNull(instance))
            instance = new ExcelServiceImpl();
        return instance;
    }

    @Override
    public File writeExcel(List<Talaba> talabalar){

        try {

            // Create a new workbook
            Workbook workbook = new XSSFWorkbook(); // For .xlsx format

            // Create a blank sheet
            Sheet sheet = workbook.createSheet("Talabalar");

            //shapka yaratildi
            createHeader(sheet);

            //tanasini yaratish
            createBody(sheet, talabalar);

            // Write the workbook content to a file
            File file = new File("talabalar.xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();

            // Close the workbook
            workbook.close();

            System.out.println("Excel file created successfully!");

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void createHeader(Sheet sheet) {

        // Create a row and put some cells in it
        Row headerRow = sheet.createRow(0);

       Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Id");

        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Name");

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("Surname");

        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("GroupType");

        Cell headerCell5 = headerRow.createCell(4);
        headerCell5.setCellValue("BirthDate");
    }

    private void createBody(Sheet sheet, List<Talaba> talabalar) {

        int rowNumber = 1;

        for (Talaba talaba : talabalar) {

            Row headerRow = sheet.createRow(rowNumber);

            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue(talaba.getId());

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue(talaba.getName());

            Cell headerCell3 = headerRow.createCell(2);
            headerCell3.setCellValue(talaba.getSurname());

            Cell headerCell4 = headerRow.createCell(3);
            headerCell4.setCellValue(talaba.getGroupType().name());

            Cell headerCell5 = headerRow.createCell(4);
            headerCell5.setCellValue(talaba.getBirthDate().toLocalDateTime().toString());

            rowNumber++;
        }
    }

}
