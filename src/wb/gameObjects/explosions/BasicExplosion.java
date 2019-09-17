package wb.gameObjects.explosions;

import wb.GameHandler;
import wb.gameObjects.worms.Worm;
import wb.hitboxes.CircleHitbox;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BasicExplosion extends Explosion {

    private float alpha;
    private int radius, maxRadius, maxTimer, damage;
    private Team team;
    private List<Worm> hitWorms;

    public BasicExplosion(GameHandler gameHandler, Vector2f location, int maxRadius, int timer, int damage, Team team) {
        super(gameHandler);
        this.location = location;
        maxTimer = timer;
        this.timer = timer;
        this.team = team;
        this.damage = damage;
        this.maxRadius = maxRadius;
        radius = maxRadius / 4;
        alpha = 1;
        hitbox = new CircleHitbox(location, radius);
        hitWorms = new ArrayList<>();
    }


    @Override
    public void tick() {
        if (timer == 0)
            gameHandler.addToRemove(this);

        for (Worm worm : gameHandler.getWorms()) {
            if (worm.getTeam() != team && !hitWorms.contains(worm)) {
                if (hitbox.collide(worm.getHitbox())) {
                    worm.takeDamage(damage, team);
                    hitWorms.add(worm);
                }
            }
        }

        timer--;

        if (alpha > 0)
            alpha -= 1f / maxTimer;
        if (alpha < 0)
            alpha = 0;

        if (radius < maxRadius) {
            radius += maxRadius / 12f;
            ((CircleHitbox) hitbox).setRadius(radius);
        }


    }

    @Override
    public void render(Graphics g) {
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.WHITE);
        ((Graphics2D) g).fill(hitbox.getShape());
//        g.fillOval((int) location.x - width / 2, (int) location.y - height / 2, width, height);
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
