package com.blogspot.ducnguyen.dev.pdfbox.example;

import com.blogspot.ducnguyen.dev.pdfbox.utils.PDFUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
        String testFile = PDF_270;
        File inputPDF = Paths.get("src", "resources", testFile).toFile();
        File signImage = Paths.get("src", "resources", PDF_SIGN).toFile();
        File outputPDF = Paths.get("target", "output" + testFile).toFile();
        PDFUtils.addImageToPdf(inputPDF, signImage, outputPDF);
    }
}
