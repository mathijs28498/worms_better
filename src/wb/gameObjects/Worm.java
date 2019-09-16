package wb.gameObjects;

import wb.GameHandler;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class Worm extends GameObject {

    private Team team;
    private int maxHP;
    private float hp;
    private Vector2f mouse = new Vector2f(0,0);
    private Vector2f[] picOOffset, picOutline, picROffset, picReal;
    private int picHeight = 160, picMaxWidth = 20, picMinWidth = 3;
    private float picAngle;
    private float power, maxPower = picHeight;

    public Worm(GameHandler gameHandler, float x, float y, Team team) {
        super(gameHandler);
        location = new Vector2f(x, y);
        this.team = team;
        width = 40;
        height = 70;
        maxHP = 100;
        hp = maxHP;
        hitbox = new PolygonHitbox(
                new Vector2f(x - width / 2f, y - height / 2f),
                new Vector2f(x + width / 2f, y - height / 2f),
                new Vector2f(x + width / 2f, y + height / 2f),
                new Vector2f(x - width / 2f, y + height / 2f)
        );

        picOOffset = new Vector2f[] {
                new Vector2f(-picMinWidth, 0),
                new Vector2f(-picMaxWidth, -picHeight),
                new Vector2f(picMaxWidth, -picHeight),
                new Vector2f(picMinWidth, 0)
        };
        picOutline = new Vector2f[4];
        picReal = new Vector2f[4];
        calcPICOutline();
        calcPICReal();

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
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
            g.setColor(Color.GRAY);
            Polygon polygonOutline = new Polygon();
            for (Vector2f v : picOutline) polygonOutline.addPoint((int) v.x, (int) v.y);
            g2d.fill(polygonOutline);

            g.setColor(Color.RED);
            Polygon polygonReal = new Polygon();
            for (Vector2f v : picReal) polygonReal.addPoint((int) v.x, (int) v.y);
            g2d.fill(polygonReal);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

            g.setColor(Color.WHITE);
            g2d.draw(polygonOutline);
        }
    }

    private void calcPICOutline() {
        for (int i = 0; i < picOOffset.length; i++) {
            float xDiff = mouse.x - location.x;
            float yDiff = mouse.y - location.y;
            if (mouse.x < location.x)
                picAngle = (float) (Math.atan(yDiff / xDiff) + Math.toRadians(270));
            else
                picAngle = (float) (Math.atan(yDiff / xDiff) + Math.toRadians(90));

            picOutline[i] = Vector2f.addRotated(location, picOOffset[i], picAngle);
        }
    }

    private void calcPICReal() {

        float a = Math.abs(mouse.x - location.x);
        float b = Math.abs(mouse.y - location.y);
        power = (float) Math.sqrt(a * a + b * b);
        if (power > picHeight) power = picHeight;

        if (power == picHeight) {
            System.arraycopy(picOutline, 0, picReal, 0, picReal.length);
        } else {
            float newPicMaxWidth = picMaxWidth * power / picHeight;
            picROffset = new Vector2f[] {
                    new Vector2f(-picMinWidth, 0),
                    new Vector2f(-newPicMaxWidth, power),
                    new Vector2f(newPicMaxWidth, power),
                    new Vector2f(picMinWidth, 0)
            };

            for (int i = 0; i < picROffset.length; i++) {
                float angle = (float) (picAngle + Math.toRadians(180));
                picReal[i] = Vector2f.addRotated(location, picROffset[i], angle);
            }
        }


    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            gameHandler.addToRemove(this);
        }
    }

    public void setMouse(float x, float y) {
        mouse = new Vector2f(x, y);
        calcPICOutline();
        calcPICReal();
    }

    public Team getTeam() {
        return team;
    }

    public float getPower() {
        return power;
    }

    public float getMaxPower() {
        return maxPower;
    }
}
