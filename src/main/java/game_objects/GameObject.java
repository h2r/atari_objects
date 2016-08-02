package game_objects;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;
import org.bytedeco.javacpp.opencv_core;

import java.util.List;

/**
 * Created by maroderi on 7/27/16.
 */
public class GameObject implements ObjectInstance {

    String className;
    String name;

    public int width, height;
    public int x, y;
    public int dx, dy;

    public GameObject(Sprite sprite) {
        this(sprite, sprite.type);
    }
    public GameObject(Sprite sprite, String name) {
        this.className = sprite.type;
        this.name = name;

        opencv_core.Rect rect = sprite.boundingRect;
        this.width = rect.width();
        this.height = rect.height();
        this.x = rect.x();
        this.y = rect.y();

        dx = 0;
        dy = 0;
    }

    public void updateSprite(Sprite sprite) {
        opencv_core.Rect rect = sprite.boundingRect;
        int x = rect.x();
        int y = rect.y();

        dx = x - this.x;
        dy = y - this.y;

        this.x = x;
        this.y = y;
    }

    @Override
    public String className() {
        return className;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ObjectInstance copyWithName(String objectName) {
        return null;
    }

    @Override
    public List<Object> variableKeys() {
        return null;
    }

    @Override
    public Object get(Object variableKey) {
        return null;
    }

    @Override
    public State copy() {
        return null;
    }
}
