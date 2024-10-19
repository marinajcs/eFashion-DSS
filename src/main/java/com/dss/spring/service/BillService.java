package com.dss.spring.service;

import com.dss.spring.model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Paragraph title = new Paragraph("Invoice", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Font fontDate = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph date = new Paragraph("Date: " + LocalDate.now().toString(), fontDate);
            date.setAlignment(Element.ALIGN_RIGHT);
            document.add(date);

            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 2, 1, 2});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            table.addCell(new PdfPCell(new Phrase("Product Name", headFont)));
            table.addCell(new PdfPCell(new Phrase("Price", headFont)));
            table.addCell(new PdfPCell(new Phrase("Quantity", headFont)));
            table.addCell(new PdfPCell(new Phrase("Total", headFont)));

            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();
                double total = product.getPrice() * quantity;

                table.addCell(new PdfPCell(new Phrase(product.getName())));
                table.addCell(new PdfPCell(new Phrase("$" + product.getPrice())));
                table.addCell(new PdfPCell(new Phrase(quantity.toString())));
                table.addCell(new PdfPCell(new Phrase("$" + total)));
            }

            document.add(table);

            Paragraph totalParagraph = new Paragraph("Total Amount: $" + totalAmount, fontTitle);
            totalParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParagraph);

            document.close();
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
