package game_objects;

import java.awt.*;

import static org.bytedeco.javacpp.opencv_core.Rect;

/**
 * Created by maroderi on 7/20/16.
 */
public class Sprite {

    public String type;
    public Rect boundingRect;
    public Color color;
    public java.awt.Point center;
    public int width;
    public int height;

    public Sprite(SpriteTemplate template, Rect boundingRect) {
        this(template.spriteType, template.color, boundingRect);
    }
    public Sprite(String type, Color color, Rect boundingRect) {
        this.type = type;
        this.boundingRect = boundingRect;
        this.color = color;

        this.width = boundingRect.width();
        this.height = boundingRect.height();
        this.center = new java.awt.Point(
                boundingRect.tl().x() + width/2,
                boundingRect.tl().y() + height/2);
    }
}
