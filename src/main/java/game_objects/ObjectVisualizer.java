package game_objects;

import burlap.mdp.core.state.State;
import burlap.visualizer.StatePainter;
import burlap.visualizer.Visualizer;
import edu.brown.cs.burlap.ALEDomainConstants;
import edu.brown.cs.burlap.ALEState;

import java.awt.*;
import java.util.List;

/**
 * Created by maroderi on 7/20/16.
 */
public class ObjectVisualizer {

    public static Visualizer create(List<SpriteTemplate> templates) {
        Visualizer vis = new Visualizer();
        vis.addStatePainter(new ObjectPainter(templates));
        vis.setBGColor(Color.BLACK);
        return vis;
    }

    static class ObjectPainter implements StatePainter {

        List<SpriteTemplate> templates;

        public ObjectPainter(List<SpriteTemplate> templates) {
            super();
            this.templates = templates;
        }

        @Override
        public void paint(Graphics2D g2, State s, float cWidth, float cHeight) {
            ALEState aleState = (ALEState)s;

            List<Sprite> sprites = SpriteFinder.findSprites(aleState, templates);
            float xRatio = cWidth/ALEDomainConstants.ALEScreenWidth;
            float yRatio = cHeight/ALEDomainConstants.ALEScreenHeight;

            for (Sprite sprite : sprites) {
                float x = (sprite.center.x - sprite.width/2)*xRatio;
                float y = (sprite.center.y - sprite.height/2)*yRatio;
                float width = sprite.width*xRatio;
                float height = sprite.height*yRatio;

                g2.setColor(sprite.color);
                g2.fillRect((int)x, (int)y, (int)width, (int)height);
            }
        }
    }
}
