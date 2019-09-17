package wb.gameObjects;

import wb.GameHandler;
import wb.hitboxes.CircleHitbox;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;

import java.awt.*;

public class TestCircleHitbox extends GameObject {

    private boolean collided = false;

    public TestCircleHitbox(GameHandler gameHandler, float x, float y) {
        super(gameHandler);
        location = new Vector2f(x, y);

        hitbox = new CircleHitbox(location, 50);
    }

    @Override
    public void tick() {

    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    @Override
    public void render(Graphics g) {
        if (!collided)
            g.setColor(Color.PINK);
        else
            g.setColor(Color.RED);

        ((Graphics2D) g).fill(hitbox.getShape());
    }
}
