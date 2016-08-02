package game_objects;

import edu.brown.cs.burlap.ALEState;
import org.bytedeco.javacpp.BytePointer;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Created by maroderi on 7/20/16.
 */
public class SpriteFinder {

    public static List<Sprite> findSprites(ALEState state, List<SpriteTemplate> templates) {
        List<Sprite> sprites = new ArrayList<>();
        for (SpriteTemplate template : templates) {
            sprites.addAll(findSprites(state, template));
        }
        return sprites;
    }

    public static List<Sprite> findSprites(ALEState state, SpriteTemplate template) {
        Mat mask = new Mat(state.getScreen().size(), CV_8UC1);

        Color c = template.color;
        Scalar s = RGB(c.getRed(), c.getGreen(), c.getBlue());
        Mat colorMat = new Mat(1, 1, CV_64FC3, s);

        inRange(state.getScreen(), colorMat, colorMat, mask);

        // Find contours
        ArrayList<Sprite> sprites = new ArrayList<>();
        MatVector contours = new MatVector();
        findContours(mask, contours, new Mat(), RETR_LIST, CHAIN_APPROX_TC89_KCOS);
        for (int i = 0; i < contours.size(); i++) {
            Mat contour = contours.get(i);

            Rect rect = boundingRect(contour);
            if (template.rectMatches(rect)) {
                Sprite sprite = new Sprite(template, rect);
                sprites.add(sprite);
            }
        }

        return sprites;
    }

    public static List<Color> findAllColors(ALEState state) {
        List<Color> colors = new ArrayList<>();

        Mat mat = state.getScreen();
        BytePointer data = mat.data().position(0).limit(mat.rows()*mat.cols()*mat.channels());
        ByteBuffer buffer = data.asBuffer();
        for (int r = 0; r < mat.rows(); r++) {
            for (int c = 0; c < mat.cols(); c++) {
                Color color = new Color(buffer.get() & 0xFF, buffer.get() & 0xFF, buffer.get() & 0xFF);
                if (!colors.contains(color)) {
                    colors.add(color);
                }
            }
        }

        return colors;
    }
}
