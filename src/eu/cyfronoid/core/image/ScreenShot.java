package eu.cyfronoid.core.image;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScreenShot {
    private String outputDirectory;
    private Robot robot;

    public ScreenShot(String outputDirectory) throws AWTException {
        this.outputDirectory = outputDirectory;
        robot = new Robot();
    }

    //TODO: Rolling fileName
    public void capture() throws IOException {
        String format = "jpg";
        String fileName = outputDirectory + "/" + "FullScreenshot." + format;

        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
        ImageIO.write(screenFullImage, format, new File(fileName));
    }
}
