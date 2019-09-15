package wb.gameObjects;

import wb.GameHandler;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;

import java.awt.*;

public class TestHitbox extends GameObject {

    private PolygonHitbox hitbox;
    private boolean collided = false;

    public TestHitbox(GameHandler gameHandler, float x, float y) {
        super(gameHandler);
        location = new Vector2f(x, y);

        Vector2f[] vectors = new Vector2f[] {
                new Vector2f(x, y),
                new Vector2f(x + 10, y),
                new Vector2f(x + 50, y + 45),
                new Vector2f(x, y + 45)
        };

        hitbox = new PolygonHitbox(vectors);
    }

    @Override
    public void tick() {

    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public PolygonHitbox getHitbox() {
        return hitbox;
    }

    @Override
    public void render(Graphics g) {
        if (!collided)
            g.setColor(Color.PINK);
        else
            g.setColor(Color.RED);

        Polygon polygon = new Polygon();
        for (Vector2f v : hitbox.getVectors()) {
            polygon.addPoint((int) v.x, (int) v.y);
        }

        ((Graphics2D) g).fill(polygon);
    }
}
