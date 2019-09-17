package wb.gameObjects.worms;

import wb.Game;
import wb.GameHandler;
import wb.gameObjects.GameObject;
import wb.gameObjects.weapons.BasicRocket;
import wb.gameObjects.weapons.Fatty;
import wb.gameObjects.weapons.WeaponType;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

import java.awt.*;
import java.util.Random;

public class Worm extends GameObject {

    private Team team;
    private int maxHP;
    private float hp;
    private Vector2f mouse = new Vector2f(0,0);
    private Vector2f[] picOOffset, picOutline, picROffset, picReal;
    private int picHeight = 160, picMaxWidth = 20, picMinWidth = 3;
    private float picAngle;
    private float power, maxPower = picHeight;
    private Vector2f shootLocation;
    private WeaponType currentWeaponType;
    private boolean isDead;
    private Color color;

    public Worm(GameHandler gameHandler, float x, float y, Team team) {
        super(gameHandler);
        this.team = team;
        location = new Vector2f(x, y);
        width = 40;
        height = 70;
        maxHP = 100;
        hp = maxHP;
        shootLocation = new Vector2f((x < Game.WIDTH / 2f) ? x + width / 2f : x - width / 2f, y - 20);
        currentWeaponType = WeaponType.FATTY;
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

        color = team.getColor();
        gameHandler.addToWormCounter(team);
    }

    @Override
    public void tick() {
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

        g2d.setColor(color);
        g2d.fill(hitbox.getShape());

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
            float xDiff = mouse.x - shootLocation.x;
            float yDiff = mouse.y - shootLocation.y;
            if (mouse.x < shootLocation.x)
                picAngle = (float) (Math.atan(yDiff / xDiff) + Math.toRadians(270));
            else
                picAngle = (float) (Math.atan(yDiff / xDiff) + Math.toRadians(90));

            picOutline[i] = Vector2f.addRotated(shootLocation, picOOffset[i], picAngle);
        }
    }

    private void calcPICReal() {

        float a = Math.abs(mouse.x - shootLocation.x);
        float b = Math.abs(mouse.y - shootLocation.y);
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
                picReal[i] = Vector2f.addRotated(shootLocation, picROffset[i], angle);
            }
        }
    }

    public void shoot() {
        float realPower = power / maxPower;
        float xDiff = mouse.x - shootLocation.x;
        float yDiff = mouse.y - shootLocation.y;

        switch (currentWeaponType) {
            case BASICROCKET:
                gameHandler.addWeapon(new BasicRocket(gameHandler, shootLocation.x, shootLocation.y, team, realPower,xDiff, yDiff));
                break;
            case FATTY:
                gameHandler.addWeapon(new Fatty(gameHandler, shootLocation.x, shootLocation.y, team, realPower, xDiff, yDiff));
                break;
        }
    }

    public void takeDamage(int damage, Team team) {
        if (!isDead) {
            hp -= damage;
            gameHandler.addDamageText(new DamageText(gameHandler, damage, 50, location.x, location.y - width / 2f - 35, team));
            if (hp <= 0) {
                isDead = true;
                gameHandler.addToRemove(this);
            }
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

    public Vector2f getShootLocation() {
        return shootLocation;
    }
}
