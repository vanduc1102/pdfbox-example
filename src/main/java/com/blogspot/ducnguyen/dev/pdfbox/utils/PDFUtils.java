package com.blogspot.ducnguyen.dev.pdfbox.utils;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author nvduc
 */
public class PDFUtils {
    private PDFUtils(){
        
    }
    
    public static void addImageToPdf(File inputFile, File signImage, File outputFile)
            throws IOException {
        try ( // the document
                PDDocument doc = PDDocument.load(inputFile)) {

            //we will add the image to the first page.
            PDPage page = doc.getPage(0);
            float x = 20;
            float y = 10;
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            int degree = getPageRotation(inputFile, 0) % 360;
            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(signImage, doc);
            float correctX = x;
            float correctY = y;
            float imageWidth = pdImage.getWidth();
            float imageHeight = pdImage.getHeight();
            switch (degree) {
                case 0:
                    imageWidth = pdImage.getWidth();
                    imageHeight = pdImage.getHeight();
                    correctX = x;
                    correctY = pageHeight - y - imageHeight;
                    break;
                case 90:
                    correctX = y;
                    correctY = x;
                    signImage = ImageUtils.rotateImage(signImage, 3);
                    pdImage = PDImageXObject.createFromFileByContent(signImage, doc);
                    imageWidth = pdImage.getWidth();
                    imageHeight = pdImage.getHeight();
                    break;
                case 180:
                    correctX = pageWidth - x - imageWidth;
                    correctY = y;
                    signImage = ImageUtils.rotateImage(signImage, 2);
                    pdImage = PDImageXObject.createFromFileByContent(signImage, doc);
                    imageWidth = pdImage.getWidth();
                    imageHeight = pdImage.getHeight();
                    break;
                case 270:
                    correctX = pageWidth - imageHeight - y;
                    correctY = pageHeight - imageWidth - x;
                    signImage = ImageUtils.rotateImage(signImage, 1);
                    pdImage = PDImageXObject.createFromFileByContent(signImage, doc);
                    imageWidth = pdImage.getWidth();
                    imageHeight = pdImage.getHeight();
                    break;
                default:
                    imageWidth = pdImage.getWidth();
                    imageHeight = pdImage.getHeight();
                    correctX = x;
                    correctY = y;
                    break;
                // Implement in a similar fashion
            }
            pdImage = PDImageXObject.createFromFileByContent(signImage, doc);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.drawImage(pdImage, correctX, correctY, imageWidth, imageHeight);
            contentStream.close();
            doc.save(outputFile);
        }
    }
    
    public static int getPageRotation(File pdfInput, int page) {
        PDDocument document = null;
        try {
            document = PDDocument.load(pdfInput);
            PDPage pageObject = document.getPage(page);
            return pageObject.getRotation();
        } catch (IOException ex) {
            System.out.println("com.blogspot.ducnguyen.dev.pdfbox.example.Main.getPageRotation()" + ex.getMessage());
        } finally {

        }
        return 0;
    }
}
