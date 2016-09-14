package com.blogspot.ducnguyen.dev.pdfbox.example;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author nvduc
 */
public class Main {
    public static void main(String[] args) throws IOException
    {
        long timestamp = System.currentTimeMillis();
        String filename = "test-"+timestamp+".pdf";
        String message = "Hello World at : "+ timestamp;
        
        PDDocument doc = new PDDocument();
        try
        {
            PDPage page = new PDPage();
            doc.addPage(page);
            
            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(message);
            contents.endText();
            contents.close();
            
            doc.save(filename);
        }
        finally
        {
            doc.close();
        }
    }
}
