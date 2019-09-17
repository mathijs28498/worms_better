package wb.gameObjects.weapons;

import wb.GameHandler;
import wb.gameObjects.GameObject;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

public abstract class Weapon extends GameObject {

    protected Team team;
    protected float xVel, yVel, vel;
    protected int damage, terrainDamage;

    public Weapon(GameHandler gameHandler, float x, float y, Team team, float power, float xDiff, float yDiff) {
        super(gameHandler);
        location = new Vector2f(x, y);
        this.team = team;

        vel = 5 + 20 * power;

        calcVels(vel, xDiff, yDiff);

        xVel += gameHandler.getWind();
    }

    protected void calcVels(float vel, float xDiff, float yDiff) {
        float mul = (float) (vel / Math.sqrt(xDiff * xDiff + yDiff * yDiff));
        xVel = xDiff * mul;
        yVel = yDiff * mul;
    }
}
