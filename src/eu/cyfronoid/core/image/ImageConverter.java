package eu.cyfronoid.core.image;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum ImageConverter {
    JPEG,
    PNG,
    BMP,
    WBMP,
    GIF,
    ;
    private final String formatName;

    ImageConverter() {
        this.formatName = name().toLowerCase();
    }

    /**
     * Converts an image to another format
     *
     * @param inputImagePath Path of the source image
     * @param outputImagePath Path of the destination image
     * @param formatName the format to be converted to, one of: jpeg, png,
     * bmp, wbmp, and gif
     * @return true if successful, false otherwise
     * @throws IOException if errors occur during writing
     */
    public boolean convertFormat(String inputImagePath,
            String outputImagePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputStream);

        boolean result = writeImage(outputImagePath, inputImage);

        inputStream.close();

        return result;
    }

    public boolean writeImage(String outputImagePath,
            BufferedImage inputImage) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(outputImagePath);
        boolean result = ImageIO.write(inputImage, formatName, outputStream);
        outputStream.close();
        return result;
    }

    public static ImageConverter getByFormatName(String formatName) {
        return ImageConverter.valueOf(formatName.toUpperCase());
    }

}
