package wb.gameObjects.projectiles;

import wb.Game;
import wb.GameHandler;
import wb.gameObjects.GameObject;
import wb.gameObjects.Ground;
import wb.gameObjects.Worm;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;
import wb.utils.Images;
import wb.utils.Team;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class BasicRocket extends Projectile {

    private PolygonHitbox hitbox;
    private Vector2f[] originalVectors;
    private int damage;

    public BasicRocket(GameHandler gameHandler, float x, float y, Team team, float xDiff, float yDiff) {
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

        xVel += gameHandler.getWind();
    }

    private void calcAngleHitbox() {
        float angle = (float) (Math.atan(yVel / xVel) + Math.toRadians(90));

        hitbox.setVectors(
                Vector2f.addRotated(location, originalVectors[0], angle),
                Vector2f.addRotated(location, originalVectors[1], angle),
                Vector2f.addRotated(location, originalVectors[2], angle),
                Vector2f.addRotated(location, originalVectors[3], angle)
        );
    }

    @Override
    public void tick() {
        checkCollisionPlayer();
        checkCollisionGround();

        location.x += xVel;
        location.y += yVel;

        calcAngleHitbox();

        yVel += 0.3;


        if (isOutOfBounds()) {
            gameHandler.addToRemove(this);
        }
    }

    private void checkCollisionPlayer() {
        List<Worm> worms = gameHandler.getWorms();
        for (Worm w : worms) {
            if (w.getTeam() != team) {
                PolygonHitbox wormHitbox = w.getHitbox();
                if (hitbox.collidePolygon(wormHitbox) || wormHitbox.collidePolygon(hitbox)) {
                    w.takeDamage(damage);
                    gameHandler.addExplosion(new BasicExplosion(gameHandler, location, 50, 50, 60));
                    gameHandler.addToRemove(this);
                }
            }
        }
    }

    private void checkCollisionGround() {
        List<Ground> playGround = gameHandler.getPlayGround();

        for (Ground g : playGround) {
            PolygonHitbox groundHitbox = g.getHitbox();
            if (hitbox.collidePolygon(groundHitbox) || groundHitbox.collidePolygon(hitbox)) {
                gameHandler.addExplosion(new BasicExplosion(gameHandler, location, 50, 50, 60));
                gameHandler.addToRemove(this);
                g.hit(damage, (int) location.x);
            }
        }
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
//        g2d.setColor(Color.PINK);
//        g2d.fill(hitbox.getPolygonFromVectors());


        float angle = 0;
        if (xVel > 0)
            angle = (float) (Math.atan(yVel / xVel) + Math.toRadians(90));
        else if (xVel < 0)
            angle = (float) (Math.atan(yVel / xVel) + Math.toRadians(270));

        BufferedImage image = Images.basicRocket;
        AffineTransform tx = AffineTransform.getRotateInstance(angle, image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

            // Drawing the rotated image at the required drawing locations
        g2d.drawImage(op.filter(image, null), (int) location.x - image.getWidth(), (int) location.y - image.getHeight(), null);
    }
}