package wb.gameObjects.projectiles;

import wb.Game;
import wb.GameHandler;
import wb.gameObjects.Worm;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

import java.awt.*;
import java.util.List;

public class BasicSquareProjectile extends Projectile {

    private PolygonHitbox hitbox;
    private Vector2f[] originalVectors;
    private int damage;

    public BasicSquareProjectile(GameHandler gameHandler, float x, float y, Team team, float xDiff, float yDiff) {
        super(gameHandler, x , y, team, 20, xDiff, yDiff);
        damage = 20;
        width = 10;
        height = 20;
        originalVectors = new Vector2f[]{
                new Vector2f(-width / 2f, -height / 2f),
                new Vector2f( width / 2f, -height / 2f),
                new Vector2f( width / 2f,  height / 2f),
                new Vector2f(-width / 2f,  height / 2f)
        };
        hitbox = new PolygonHitbox();
        calcAngleHitbox();
    }

    private void calcAngleHitbox() {
        float angle = (float) (Math.atan(yVel / xVel) + Math.toRadians(90));

        hitbox.setVectors(
                Vector2f.addRotated(vector, originalVectors[0], angle),
                Vector2f.addRotated(vector, originalVectors[1], angle),
                Vector2f.addRotated(vector, originalVectors[2], angle),
                Vector2f.addRotated(vector, originalVectors[3], angle)
        );
    }

    @Override
    public void tick() {
        List<Worm> worms = gameHandler.getWorms();
        for (Worm w : worms) {
            if (w.getTeam() != team) {
                PolygonHitbox wormHitbox = w.getHitbox();
                if (hitbox.collidePolygon(wormHitbox) || wormHitbox.collidePolygon(hitbox)) {
                    w.takeDamage(damage);
                    gameHandler.addToRemove(this);
                }
            }
        }

        vector.x += xVel;
        vector.y += yVel;

        calcAngleHitbox();

        yVel += 0.3;


        if (isOutOfBounds()) gameHandler.addToRemove(this);
    }

    private boolean isOutOfBounds() {
        for (Vector2f v : hitbox.getVectors()) {
            if (v.x > 0 && v.x < Game.WIDTH && v.x > 0 && v.y < Game.HEIGHT) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.PINK);
        g2d.fill(hitbox.getPolygonFromVectors());

//            old rotating code
//        g2d.rotate(Math.atan(yVel / xVel), x, y);
//
//        g2d.setColor(Color.ORANGE);
//        g2d.fill(new Rectangle((int) x, (int) y,width,height));
//
//        g2d.dispose();
    }
}
