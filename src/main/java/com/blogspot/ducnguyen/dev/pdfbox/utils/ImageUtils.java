package com.blogspot.ducnguyen.dev.pdfbox.utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;

/**
 *
 * @author nvduc
 */
public class ImageUtils {

    private ImageUtils() {

    }

    /**
     * Return image rotated as tempFile
     *
     * @param imagePath
     * @param numquadrants
     * @return
     */
    public static File rotateImage(final File imagePath, int numquadrants) {
        try {
            BufferedImage bufferedImage = ImageIO.read(imagePath);
            bufferedImage = transform(bufferedImage, numquadrants);
            File outputfile = Files.createTempFile("temp", "png").toFile();
            ImageIO.write(bufferedImage, "png", outputfile);
            return outputfile;
        } catch (IOException ex) {

        }
        return null;
    }

    public static BufferedImage transform(BufferedImage image, int numquadrants) {
        int w0 = image.getWidth();
        int h0 = image.getHeight();
        int w1 = w0;
        int h1 = h0;

        int centerX = w0 / 2;
        int centerY = h0 / 2;

        if (numquadrants % 2 == 1) {
            w1 = h0;
            h1 = w0;
        }

        if (numquadrants % 4 == 1) {
            if (w0 > h0) {
                centerX = h0 / 2;
                centerY = h0 / 2;
            } else if (h0 > w0) {
                centerX = w0 / 2;
                centerY = w0 / 2;
            }
            // if h0 == w0, then use default
        } else if (numquadrants % 4 == 3) {
            if (w0 > h0) {
                centerX = w0 / 2;
                centerY = w0 / 2;
            } else if (h0 > w0) {
                centerX = h0 / 2;
                centerY = h0 / 2;
            }
            // if h0 == w0, then use default
        }

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToQuadrantRotation(numquadrants, centerX, centerY);

        AffineTransformOp opRotated = new AffineTransformOp(affineTransform,
                AffineTransformOp.TYPE_BILINEAR);

        BufferedImage transformedImage = new BufferedImage(w1, h1,
                image.getType());

        transformedImage = opRotated.filter(image, transformedImage);
        return transformedImage;
    }

}
