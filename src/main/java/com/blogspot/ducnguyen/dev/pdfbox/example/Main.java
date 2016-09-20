package com.blogspot.ducnguyen.dev.pdfbox.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author nvduc
 */
public class Main {

    private static String PDF_000 = "000.pdf";
    private static String PDF_090 = "090.pdf";
    private static String PDF_180 = "0180.pdf";
    private static String PDF_270 = "0270.pdf";
    private static String PDF_SIGN = "sign.jpg";

    public static void main(String[] args) throws IOException {
        File inputPDF = Paths.get("src", "resources", PDF_000).toFile();
        File signImage = Paths.get("src", "resources", PDF_SIGN).toFile();
        File outputPDF = Paths.get("target", "output" + PDF_000).toFile();
        addImageToPdf(inputPDF, signImage, outputPDF);
        System.out.println("com.blogspot.ducnguyen.dev.pdfbox.example.Main.main() -- DONE");
    }

    public static void addImageToPdf(File inputFile, File signImage, File outputFile)
            throws IOException {
        try ( // the document
                PDDocument doc = PDDocument.load(inputFile)) {

            //we will add the image to the first page.
            PDPage page = doc.getPage(0);
            float x = 10;
            float y = 10;
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            // createFromFile is the easiest way with an image file
            // if you already have the image in a BufferedImage, 
            // call LosslessFactory.createFromImage() instead
            PDImageXObject pdImage = PDImageXObject.createFromFileByContent(signImage, doc);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true);

            // contentStream.drawImage(ximage, 20, 20 );
            // better method inspired by http://stackoverflow.com/a/22318681/535646
            // reduce this value if the image is too large
            float scale = 1f;
            float imageWidth = pdImage.getWidth() * scale;
            float imageHeight = pdImage.getHeight() * scale;
            float correctX = x;
            float correctY = pageHeight - y - imageHeight;
            contentStream.drawImage(pdImage, correctX, correctY, imageWidth, imageHeight);
            contentStream.close();
            doc.save(outputFile);
        }
    }
}
