package io;

import edu.brown.cs.burlap.screen.ScreenConverter;
import org.bytedeco.javacpp.opencv_core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by maroderi on 8/2/16.
 */
public class OpenCVIO {

    public static void saveMat(opencv_core.Mat mat, String fileName) {
        ScreenConverter converter = new ScreenConverter();

        BufferedImage img = converter.convert(mat);
        File outputfile = new File(fileName);
        try {
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
