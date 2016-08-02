package game_objects;

import java.awt.*;

import static org.bytedeco.javacpp.opencv_core.Rect;
import static org.bytedeco.javacpp.opencv_core.Size;

/**
 * Created by maroderi on 7/20/16.
 */
public class SpriteTemplate {
    public String spriteType;
    public Color color;

    public Size minSize;
    public Size maxSize;

    public Rectangle range = new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public SpriteTemplate(String spriteType, Color color) {
        this.spriteType = spriteType;
        this.color = color;
    }

    public SpriteTemplate(String spriteType, Color color, Size minSize, Size maxSize) {
        this.spriteType = spriteType;
        this.color = color;

        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public SpriteTemplate(String spriteType, Color color, Rectangle range) {
        this.spriteType = spriteType;
        this.color = color;

        this.range = range;
    }

    public boolean rectMatches(Rect rect) {
        Rectangle r = new Rectangle(rect.x(), rect.y(), rect.width(), rect.height());
        if (minSize != null && (rect.width() < minSize.width() || rect.height() < minSize.height())) {
            return false;
        }
        if (maxSize != null && (rect.width() > maxSize.width() || rect.height() > maxSize.height())) {
            return false;
        }
        if (range != null && !range.contains(new Rectangle(rect.x(), rect.y(), rect.width(), rect.height()))) {
            return false;
        }
        return true;
    }
}
