package game_objects;

import edu.brown.cs.burlap.ALEDomainConstants;
import org.bytedeco.javacpp.opencv_core;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maroderi on 7/20/16.
 */
public class ObjectConstants {
    // Pong Object Types
    public static final String PONG_PADDLE_TYPE = "pong_paddle";
    public static final String PONG_ENEMY_PADDLE_TYPE = "pong_enemy_paddle";
    public static final String PONG_BALL_TYPE = "pong_ball";

    // Pong Colors
    public static Color PONG_PLAYER_PADDLE_COLOR = new Color(92, 186, 92);
    public static Color PONG_ENEMY_PADDLE_COLOR = new Color(213, 130, 74);
    public static Color PONG_BALL_COLOR = new Color(236, 236, 236);

    // Pong Sprites
    public static SpriteTemplate PONG_PLAYER_PADDLE_TEMPLATE =
            new SpriteTemplate(PONG_PADDLE_TYPE, PONG_PLAYER_PADDLE_COLOR, new Rectangle(0, 20, ALEDomainConstants.ALEScreenWidth, ALEDomainConstants.ALEScreenHeight - 20));
    public static SpriteTemplate PONG_ENEMY_PADDLE_TEMPLATE =
            new SpriteTemplate(PONG_ENEMY_PADDLE_TYPE, PONG_ENEMY_PADDLE_COLOR, new Rectangle(0, 20, ALEDomainConstants.ALEScreenWidth, ALEDomainConstants.ALEScreenHeight - 20));
    public static SpriteTemplate PONG_BALL_TEMPLATE =
            new SpriteTemplate(PONG_BALL_TYPE, PONG_BALL_COLOR, new opencv_core.Size(0, 0), new opencv_core.Size(3, 10));
    public static List<SpriteTemplate> PONG_SPRITE_TEMPLATES = Arrays.asList(
            PONG_PLAYER_PADDLE_TEMPLATE,
            PONG_ENEMY_PADDLE_TEMPLATE,
            PONG_BALL_TEMPLATE);
}
