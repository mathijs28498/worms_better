package wb.gameObjects;

import wb.GameHandler;
import wb.hitboxes.CircleHitbox;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;

import java.awt.*;

public class TestPolygonHitbox extends GameObject {

    private boolean collided = false;
    private TestCircleHitbox testCircleHitbox;

    public TestPolygonHitbox(GameHandler gameHandler, float x, float y, TestCircleHitbox testCircleHitbox) {
        super(gameHandler);
        location = new Vector2f(x, y);
        this.testCircleHitbox = testCircleHitbox;

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

    @Override
    public void setLocation(Vector2f location) {
        this.location = location;
        calcVectors();

        testCircleHitbox.setCollided(testCircleHitbox.getHitbox().collide(hitbox));

    }

    private void calcVectors() {
        Vector2f[] vectors = new Vector2f[] {
                new Vector2f(location.x, location.y),
                new Vector2f(location.x + 10, location.y),
                new Vector2f(location.x + 50, location.y + 45),
                new Vector2f(location.x, location.y + 45)
        };

        ((PolygonHitbox) hitbox).setVectors(vectors);
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
//        if (!collided)
//            g.setColor(Color.PINK);
//        else
//            g.setColor(Color.RED);

        ((Graphics2D) g).fill(hitbox.getShape());
    }

}
