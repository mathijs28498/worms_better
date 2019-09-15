package wb.gameObjects;

import wb.GameHandler;
import wb.hitboxes.Vector2f;

import java.awt.*;

public abstract class GameObject {

    protected Vector2f vector;
    protected int width, height;
    protected GameHandler gameHandler;

    public GameObject(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public Vector2f getVector() {
        return vector;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
