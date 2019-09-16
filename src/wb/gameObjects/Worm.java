package wb.gameObjects;

import wb.GameHandler;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

import java.awt.*;

public class Worm extends GameObject {

    private Team team;
    private int xMouse, yMouse, maxHP;
    private float hp;

    public Worm(GameHandler gameHandler, float x, float y, Team team) {
        super(gameHandler);
        location = new Vector2f(x, y);
        this.team = team;
        width = 25;
        height = 50;
        maxHP = 100;
        hp = maxHP;
        hitbox = new PolygonHitbox(
                new Vector2f(x - width / 2f, y - height / 2f),
                new Vector2f(x + width / 2f, y - height / 2f),
                new Vector2f(x + width / 2f, y + height / 2f),
                new Vector2f(x - width / 2f, y + height / 2f)
        );

        gameHandler.addToWormCounter(team);
    }

    @Override
    public void tick() {
//        hp -= 0.1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect((int) (location.x - 30), (int) (location.y - height / 2 - 20), 60, 10);

        g.setColor(Color.GREEN);
        g.fillRect((int) (location.x - 30), (int) (location.y - height / 2 - 20), (int) (hp / maxHP * 60), 10);
        g.setColor(Color.WHITE);
        g.drawRect((int) (location.x - 1 - 30), (int) (location.y - height / 2 - 20), 60, 10);

        Graphics2D g2d = (Graphics2D) g;

        if (team == Team.ONE) g2d.setColor(Color.BLUE);
        else g2d.setColor(Color.ORANGE);
        g2d.fill(((PolygonHitbox) hitbox).getPolygonFromVectors());

        if (gameHandler.canShoot() && gameHandler.getCurrentTurn() == team) {
            g.setColor(Color.RED);
            g.drawLine((int) location.x, (int) location.y, xMouse, yMouse);
        }
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            gameHandler.addToRemove(this);
        }
    }

    public void setxMouse(int xMouse) {
        this.xMouse = xMouse;
    }

    public void setyMouse(int yMouse) {
        this.yMouse = yMouse;
    }

    public Team getTeam() {
        return team;
    }
}
