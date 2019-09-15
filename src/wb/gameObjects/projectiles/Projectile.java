package wb.gameObjects.projectiles;

import wb.GameHandler;
import wb.gameObjects.GameObject;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

public abstract class Projectile extends GameObject {

    protected Team team;
    protected float xVel, yVel;

    public Projectile(GameHandler gameHandler, float x, float y, Team team, int vel, float xDiff, float yDiff) {
        super(gameHandler);
        location = new Vector2f(x, y);
        this.team = team;

        float mul = (float) (vel / Math.sqrt(xDiff * xDiff + yDiff * yDiff));
        xVel = xDiff * mul;
        yVel = yDiff * mul;
    }
}
