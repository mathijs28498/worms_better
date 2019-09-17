package wb.gameObjects.weapons;

import wb.Game;
import wb.GameHandler;
import wb.gameObjects.Ground;
import wb.gameObjects.worms.Worm;
import wb.hitboxes.CircleHitbox;
import wb.hitboxes.Hitbox;
import wb.utils.Images;
import wb.utils.Team;
import wb.utils.Values;

import java.awt.*;
import java.util.List;

public class Fatty extends Weapon {

    private float radius;


    public Fatty(GameHandler gameHandler, float x, float y, Team team, float power, float xDiff, float yDiff) {
        super(gameHandler, x, y, team, power, xDiff, yDiff);
        damage = 20;
        terrainDamage = 30;
        radius = 5;

        hitbox = new CircleHitbox(location, (int) radius);
    }

    private boolean isOutOfBounds() {
        int radius = ((CircleHitbox) hitbox).getRadius();
        if (location.x - radius * 2 > Game.WIDTH || location.x < 0)
            return true;
        return false;
    }

    @Override
    public void tick() {
        if (isOutOfBounds()) gameHandler.addToRemove(this);

        checkCollisionPlayer();
        checkCollisionGround();

        location.x += xVel;
        location.y += yVel;

        CircleHitbox circleHitbox = (CircleHitbox) hitbox;
        if (yVel > 0) {
            radius += 0.6;
            circleHitbox.setRadius((int) radius);
        }

        yVel += Values.GRAVITY;
    }

    private void checkCollisionPlayer() {
        List<Worm> worms = gameHandler.getWorms();
        for (Worm w : worms) {
            if (w.getTeam() != team) {
                Hitbox wormHitbox = w.getHitbox();
                if (hitbox.collide(wormHitbox)) {
                    gameHandler.addToRemove(this);
                    w.takeDamage(damage);
                }
            }
        }
    }

    private void checkCollisionGround() {
        Ground ground = gameHandler.getGround();
        Hitbox groundHitbox = ground.getHitbox();
        if (hitbox.collide(groundHitbox) || groundHitbox.collide(hitbox)) {
            gameHandler.addToRemove(this);
            ground.hit(terrainDamage, location.x);
        }
    }

    @Override
    public void render(Graphics g) {
//        g.setColor(Color.WHITE);
//        ((Graphics2D) g).fill(hitbox.getShape());

        g.drawImage(Images.fatty, (int) (location.x - radius + 1), (int) (location.y - radius + 1), (int) radius * 2, (int) radius * 2, null);


    }
}
