package wb.gameObjects;

import wb.utils.Team;

import java.awt.*;

public class Projectile extends GameObject {

    private Team team;
    private double xVel, yVel;

    public Projectile(int x, int y, Team team, int vel, int xDiff, int yDiff) {
        this.x = x - 5;
        this.y = y - 5;
        this.team = team;

        double mul = vel / Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        xVel = xDiff * mul;
        yVel = yDiff * mul;

        width = 10;
        height = 10;
    }


    @Override
    public void tick() {
        x += xVel;
        y += yVel;

        yVel += 0.1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect(x,y,width,height);
    }
}
