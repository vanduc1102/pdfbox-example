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
    
    private final static String PDF_SAMPLE = getCurrentPath().concat(File.separator).concat("src").concat(File.separator).concat("resources").concat(File.separator).concat("pdf.pdf");
    
    public static void main(String[] args) throws IOException {
        System.out.println(PDF_SAMPLE);
        String imagePath = getCurrentPath().concat(File.separator).concat("src").concat(File.separator).concat("resources").concat(File.separator).concat("chelsea.jpg");
        addImageToPdf(PDF_SAMPLE, imagePath, createOutput("jpg"));
        String imagePngPath = getCurrentPath().concat(File.separator).concat("src").concat(File.separator).concat("resources").concat(File.separator).concat("chelsea.png");
        addImageToPdf(PDF_SAMPLE, imagePngPath, createOutput("png"));
    }
    
    public static String getCurrentPath() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }
    
    public static String createOutput(String prefix) {        
        return Paths.get(".").toAbsolutePath().normalize().toString().concat(File.separator).concat("target").concat(File.separator).concat(prefix+String.valueOf(System.currentTimeMillis())).concat(".pdf");
    }
    
    public static void addImageToPdf( String inputFile, String imagePath, String outputFile )
            throws IOException
    {
        try
        ( // the document
                PDDocument doc = PDDocument.load( new File(inputFile) )) {

            //we will add the image to the first page.
            PDPage page = doc.getPage(0);

            // createFromFile is the easiest way with an image file
            // if you already have the image in a BufferedImage, 
            // call LosslessFactory.createFromImage() instead
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
            PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true);

            // contentStream.drawImage(ximage, 20, 20 );
            // better method inspired by http://stackoverflow.com/a/22318681/535646
            // reduce this value if the image is too large
            float scale = 1f;
            contentStream.drawImage(pdImage, 20, 20, pdImage.getWidth()*scale, pdImage.getHeight()*scale);

            contentStream.close();
            doc.save( outputFile );
        }
    }
}
