package wb.gameObjects;

import wb.GameHandler;
import wb.hitboxes.Hitbox;
import wb.hitboxes.Vector2f;

import java.awt.*;

public abstract class GameObject {

    protected Vector2f location;
    protected int width, height;
    protected GameHandler gameHandler;
    protected Hitbox hitbox;

    public GameObject(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public Vector2f getLocation() {
        return location;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setLocation(Vector2f location) {
        this.location = location;
    }
}
