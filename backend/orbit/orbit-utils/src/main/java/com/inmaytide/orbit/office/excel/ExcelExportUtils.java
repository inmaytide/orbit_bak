package com.inmaytide.orbit.office.excel;

import com.inmaytide.orbit.office.excel.annotation.Comment;
import com.inmaytide.orbit.office.excel.annotation.ExcelTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * Excel导出工具类
 *
 * @author Moss
 * @since September 8, 2017
 */
public class ExcelExportUtils {

    private static void setHeader(Sheet sheet, List<Field> fields) {
        final Row row = sheet.createRow(0);
        fields.forEach(field -> {
            Comment comment = ExcelUtils.getComment(field);
            Cell cell = row.createCell(comment.column());
            if (StringUtils.isNotBlank(comment.header())) {
                cell.setCellValue(comment.header());
            } else {
                throw new IllegalArgumentException("When template is not configured, the title must not be empty.");
            }
        });
    }

    private static Workbook generateWorkbook(ExcelTemplate template, List<Field> fields) throws IOException, InvalidFormatException {
        Workbook book;
        if (template != null && StringUtils.isNotBlank(template.template())) {
            ClassPathResource resource = new ClassPathResource(template.template());
            book = WorkbookFactory.create(resource.getInputStream());
        } else {
            book = new XSSFWorkbook();
            Sheet sheet = book.createSheet();
            setHeader(sheet, fields);
        }
        return book;
    }

    private static void setContents(ExcelTemplate template, List<Field> fields,
                                    List<? extends Serializable> list, Workbook book) {
        Sheet sheet = book.getSheetAt(0);
        int startRow = 1;
        if (template != null) {
            sheet = book.getSheetAt(template.sheet());
            startRow = template.startRow();
        }
        for (Serializable o : list) {
            Row row = sheet.createRow(startRow++);
            fields.forEach(field -> setCellValue(row, field, o));
        }

    }

    private static void setCellValue(Row row, Field field, Serializable o) {
        Comment comment = ExcelUtils.getComment(field);
        Cell cell = row.getCell(comment.column()) == null ?
                row.createCell(comment.column()) : row.getCell(comment.column());
        cell.setCellType(CellType.STRING);
        try {
            cell.setCellValue(Objects.toString(field.get(o), ""));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public static <T extends Serializable> void export(Class<T> cls, List<T> list, OutputStream os) throws IOException, InvalidFormatException {
        ExcelTemplate template = ExcelUtils.getTemplate(cls);
        List<Field> fields = ExcelUtils.getFields(cls);
        try (Workbook book = generateWorkbook(template, fields)) {
            setContents(template, fields, list, book);
            book.write(os);
        }
    }

}
