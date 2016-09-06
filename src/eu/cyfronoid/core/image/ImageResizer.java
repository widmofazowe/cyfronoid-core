package eu.cyfronoid.core.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.base.Preconditions;

public class ImageResizer {
    private BufferedImage inputImage;
    private BufferedImage outputImage;

    /**
     * Constructor of a ImageResizer class
     * @param inputImagePath Path of the original image
     * @throws IOException
     */
    public ImageResizer(String inputImagePath) throws IOException {
        this(new File(inputImagePath));
    }

    /**
     * Constructor of a ImageResizer class
     * @param inputFile File of the original image
     * @throws IOException
     */
    public ImageResizer(File inputFile) throws IOException {
        inputImage = ImageIO.read(inputFile);
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     */
    public void resize(double percent) {
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(scaledWidth, scaledHeight);
    }

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     */
    public void resize(int scaledWidth, int scaledHeight) {
        outputImage = new BufferedImage(scaledWidth,
                 scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
    }

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param outputImagePath Path to save the resized image
     * @throws IOException
     */
    public void save(String outputImagePath) throws IOException {
        Preconditions.checkNotNull(outputImage);
        ImageConverter imageConverter = extractExtensionFromFileName(outputImagePath);
        imageConverter.writeImage(outputImagePath, outputImage);
    }

    private ImageConverter extractExtensionFromFileName(String outputImagePath) {
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
        return ImageConverter.getByFormatName(formatName);
    }

    public static BufferedImage resize(int targetWidth, int targetHeight, Image src) {
        double scaleW = (double) targetWidth / (double) src.getWidth(null);
        double scaleH = (double) targetHeight / (double) src.getHeight(null);

        double scale = scaleW < scaleH ? scaleW : scaleH;

        BufferedImage result = new BufferedImage((int) (src.getWidth(null) * scale), (int) (src.getHeight(null) * scale),
                BufferedImage.TYPE_4BYTE_ABGR | BufferedImage.SCALE_SMOOTH);

        Graphics2D g2d = result.createGraphics();
        g2d.drawImage(src, 0, 0, result.getWidth(), result.getHeight(), null);
        g2d.dispose();

        return result;
    }

}
