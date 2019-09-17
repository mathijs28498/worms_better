package wb.gameObjects.weapons;

import wb.Game;
import wb.GameHandler;
import wb.gameObjects.Ground;
import wb.gameObjects.worms.Worm;
import wb.gameObjects.explosions.BasicExplosion;
import wb.hitboxes.Hitbox;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;
import wb.utils.Images;
import wb.utils.Team;
import wb.utils.Values;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

public class BasicRocket extends Weapon {

    private Vector2f[] originalVectors;
    private int expDamage;
    private boolean destroyed;

    public BasicRocket(GameHandler gameHandler, float x, float y, Team team, float power, float xDiff, float yDiff) {
        super(gameHandler, x , y, team, power, xDiff, yDiff);
        damage = 30;
        terrainDamage = 20;
        expDamage = 10;
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

        ((PolygonHitbox) hitbox).setVectors(
                Vector2f.addRotated(location, originalVectors[0], angle),
                Vector2f.addRotated(location, originalVectors[1], angle),
                Vector2f.addRotated(location, originalVectors[2], angle),
                Vector2f.addRotated(location, originalVectors[3], angle)
        );
    }

    @Override
    public void tick() {
        if (!destroyed) checkCollisionGround();
        if (!destroyed) checkCollisionPlayer();

        if (!destroyed) {
            location.x += xVel;
            location.y += yVel;

            calcAngleHitbox();

            yVel += Values.GRAVITY;

            if (isOutOfBounds()) gameHandler.addToRemove(this);
        }
    }

    private void checkCollisionPlayer() {
        List<Worm> worms = gameHandler.getWorms();
        for (Worm w : worms) {
            if (w.getTeam() != team) {
                Hitbox wormHitbox = w.getHitbox();
                if (hitbox.collide(wormHitbox) || wormHitbox.collide(hitbox)) {
                    explosionPlusDeletion();
                    w.takeDamage(damage);
                    destroyed = true;
                }
            }
        }
    }

    private void checkCollisionGround() {
        Ground ground = gameHandler.getGround();
        Hitbox groundHitbox = ground.getHitbox();
        if (hitbox.collide(groundHitbox) || groundHitbox.collide(hitbox)) {
            explosionPlusDeletion();
            ground.hit(terrainDamage, location.x);
            destroyed = true;
        }
    }

    private void explosionPlusDeletion() {
        gameHandler.addExplosion(new BasicExplosion(gameHandler, location, 25, 60, expDamage, team));
        gameHandler.addToRemove(this);
    }

    private boolean isOutOfBounds() {
        for (Vector2f v : ((PolygonHitbox) hitbox).getVectors()) {
            if (v.x > 0 && v.x < Game.WIDTH && v.x > 0) return false;
        }

        return true;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(Color.PINK);
//        g2d.fill(hitbox.getShape());


        float angle = 0;
        if (xVel > 0)
            angle = (float) (Math.atan(yVel / xVel) + Math.toRadians(90));
        else if (xVel < 0)
            angle = (float) (Math.atan(yVel / xVel) + Math.toRadians(270));

        BufferedImage image = Images.basicRocket;
        AffineTransform tx = AffineTransform.getRotateInstance(angle, image.getWidth() / 2, image.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

//             Drawing the rotated image at the required drawing locations
        g2d.drawImage(op.filter(image, null), (int) location.x - image.getWidth() / 2, (int) location.y - image.getHeight() / 2, null);
    }
}
