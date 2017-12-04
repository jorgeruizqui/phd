package es.jor.phd.dblp.exporter.excel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import es.jor.phd.dblp.parser.entity.DBLPEntity;
import es.jor.phd.dblp.parser.entity.Hit;

public class ExcelFileExporter {

	/**
	 * DBLP Entity exporter to a file
	 * @param gexf Gexf graph information
	 * @param gexpFileName Gexf File Name
	 */
	public static void writeDblpEntityToFile(DBLPEntity dblp, String excelFileName) {

		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("DBLP Results");

        int rowNumber = 1;
        int colNumber = 1;

        Row row = sheet.createRow(rowNumber++);
        Cell cell = row.createCell(colNumber);
        cell.setCellValue("Results for query :" + dblp.getResult().getQuery());
        row = sheet.createRow(rowNumber++);
        cell = row.createCell(colNumber);
        cell.setCellValue("Total Results :" + dblp.getResult().getHits().getTotal());

        rowNumber++;rowNumber++;
        // Create header
        row = sheet.createRow(rowNumber++);
        cell = row.createCell(colNumber++);
        cell.setCellValue("Title");
        cell = row.createCell(colNumber++);
        cell.setCellValue("Type");
        cell = row.createCell(colNumber++);
        cell.setCellValue("Url");
        cell = row.createCell(colNumber++);
        cell.setCellValue("Year");
        cell = row.createCell(colNumber++);
        cell.setCellValue("Authors");
        cell = row.createCell(colNumber++);
        cell.setCellValue("Authors Names");

        colNumber = 1;
        for (Hit hit : dblp.getResult().getHits().getHit()) {
            row = sheet.createRow(rowNumber++);
        	cell = row.createCell(colNumber++);
            cell.setCellValue(hit.getInfo().getTitle());

        	cell = row.createCell(colNumber++);
            cell.setCellValue(hit.getInfo().getType());

        	cell = row.createCell(colNumber++);
            cell.setCellValue(hit.getInfo().getUrl());

        	cell = row.createCell(colNumber++);
            cell.setCellValue(hit.getInfo().getYear());

        	if (hit.getInfo().getAuthors() != null) {
        		cell = row.createCell(colNumber++);
        		cell.setCellValue(hit.getInfo().getAuthors().getName().size());
        		StringBuffer authorsNames = new StringBuffer("");
        		for(String authorName : hit.getInfo().getAuthors().getName()) {
        			authorsNames.append(authorName + "\n");
        		}
        		cell = row.createCell(colNumber++);
        		cell.setCellValue(authorsNames.toString());
        	}

            colNumber = 1;
        }


        try (FileOutputStream outputStream = new FileOutputStream(excelFileName)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
