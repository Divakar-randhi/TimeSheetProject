package com.timesheet.pro.excelexporter;
import com.timesheet.pro.Entities.Timesheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Component
public class TimesheetExcelExporter {

    public void exportAllTimesheets(List<Timesheet> timesheets) {
        String filePath = System.getProperty("user.home") + "/Downloads/timesheets.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Timesheets");

            // Header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Timesheet ID");
            header.createCell(1).setCellValue("Category ID");
            header.createCell(2).setCellValue("Shift ID");
            header.createCell(3).setCellValue("User ID");
            header.createCell(4).setCellValue("Work Date");
            header.createCell(5).setCellValue("Hours Worked");
            header.createCell(6).setCellValue("Details");

            // Data rows
            int rowNum = 1;
            for (Timesheet t : timesheets) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(t.getTimesheetId());
                row.createCell(1).setCellValue(t.getCategory() != null ? t.getCategory().getCategoryId() : null);
                row.createCell(2).setCellValue(t.getShift() != null ? t.getShift().getShiftId() : null);
                row.createCell(3).setCellValue(t.getUser() != null ? t.getUser().getUserId() : null);
                row.createCell(4).setCellValue(t.getWorkDate().toString());
                row.createCell(5).setCellValue(t.getHoursWorked().toString());
                row.createCell(6).setCellValue(t.getDetails());
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

