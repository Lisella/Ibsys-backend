package de.Ibsys.ibsys;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class InsertExcelDataIntoDBTest {

    @Autowired
    private HikariDataSource dataSource;

    @Test
    public void insertDataFromExcelIntoDB() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String excelFilePath = "C:/path/to/excel.xlsx";
        try (FileInputStream inputStream = new FileInputStream(excelFilePath)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int column1Value = (int) row.getCell(0).getNumericCellValue();
                int column2Value = (int) row.getCell(1).getNumericCellValue();
                //String column3Value = row.getCell(2).getStringCellValue();

                String sql = "INSERT INTO public.\"WorkstationWaitlist\" (\"ID\", \"timeneed\") VALUES (?, ?)";
                jdbcTemplate.update(sql, column1Value, column2Value);
            }

            System.out.println("Data from the Excel file successfully inserted into the database.");
            dataSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
