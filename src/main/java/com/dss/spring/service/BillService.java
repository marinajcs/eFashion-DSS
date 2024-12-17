package com.dss.spring.service;

import com.dss.spring.model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class BillService {
	
	public ByteArrayInputStream generateBillPdf(Map<Product, Integer> products, double totalAmount) {

		Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {
	        PdfWriter.getInstance(document, out);
	        document.open();

	        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
	        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
	        Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
	        Font fontTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
	        Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.GRAY);

	        Paragraph title = new Paragraph("INVOICE", fontTitle);
	        title.setAlignment(Element.ALIGN_CENTER);
	        document.add(title);
	        
	        document.add(Chunk.NEWLINE);

	        Paragraph companyInfo = new Paragraph("eFashion Industries \nGranada, Spain \nContact: efashion-info@efashion.com", fontBody);
	        companyInfo.setAlignment(Element.ALIGN_LEFT);
	        document.add(companyInfo);

	        document.add(Chunk.NEWLINE);

	        Paragraph date = new Paragraph("Date: " + LocalDate.now().toString(), fontBody);
	        date.setAlignment(Element.ALIGN_RIGHT);
	        document.add(date);

	        document.add(Chunk.NEWLINE); 

	        PdfPTable table = new PdfPTable(4);
	        table.setWidthPercentage(100);
	        table.setWidths(new int[]{3, 2, 1, 2});
	        table.setSpacingBefore(10f);
	        table.setSpacingAfter(10f);
	        
	        BaseColor darkGreen = new BaseColor(0, 100, 0);

	        PdfPCell hcell;
	        hcell = new PdfPCell(new Phrase("Product name", fontHeader));
	        hcell.setBackgroundColor(darkGreen);
	        hcell.setPadding(5);
	        table.addCell(hcell);

	        hcell = new PdfPCell(new Phrase("Price", fontHeader));
	        hcell.setBackgroundColor(darkGreen);
	        hcell.setPadding(5);
	        table.addCell(hcell);

	        hcell = new PdfPCell(new Phrase("Quantity", fontHeader));
	        hcell.setBackgroundColor(darkGreen);
	        hcell.setPadding(5);
	        table.addCell(hcell);

	        hcell = new PdfPCell(new Phrase("Total", fontHeader));
	        hcell.setBackgroundColor(darkGreen);
	        hcell.setPadding(5);
	        table.addCell(hcell);

	        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
	            Product product = entry.getKey();
	            Integer quantity = entry.getValue();
	            double total = product.getPrice() * quantity;

	            PdfPCell cell;

	            cell = new PdfPCell(new Phrase(product.getName(), fontBody));
	            cell.setPadding(5);
	            table.addCell(cell);

	            cell = new PdfPCell(new Phrase(String.format("$%.2f", product.getPrice()), fontBody));
	            cell.setPadding(5);
	            table.addCell(cell);

	            cell = new PdfPCell(new Phrase(quantity.toString(), fontBody));
	            cell.setPadding(5);
	            table.addCell(cell);

	            cell = new PdfPCell(new Phrase(String.format("$%.2f", total), fontBody));
	            cell.setPadding(5);
	            table.addCell(cell);
	        }
	        document.add(table);

	        Paragraph totalParagraph = new Paragraph("Total amount: $" + String.format("%.2f", totalAmount), fontTotal);
	        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
	        totalParagraph.setSpacingBefore(20);
	        document.add(totalParagraph);

	        document.add(Chunk.NEWLINE);

	        Paragraph footer = new Paragraph("Thank you for your purchase in eFashion.com!", fontFooter);
	        footer.setAlignment(Element.ALIGN_CENTER);
	        footer.setSpacingBefore(30);
	        document.add(footer);

	        document.close();
	    } catch (DocumentException ex) {
	        ex.printStackTrace();
	    }

	    return new ByteArrayInputStream(out.toByteArray());
    }
	
	public String exportDatabase() throws SQLException, IOException {
        String url = "jdbc:h2:file:./data/testdb;DB_CLOSE_ON_EXIT=FALSE"; 
        String user = "sa";
        String password = "password"; 

        try (Connection connection = DriverManager.getConnection(url, user, password);
               FileWriter writer = new FileWriter("export.sql")) {
               Statement statement = connection.createStatement();
               String sql = "SCRIPT TO 'export.sql'";
               statement.execute(sql);
           }
        return "export.sql"; 
     }
}
