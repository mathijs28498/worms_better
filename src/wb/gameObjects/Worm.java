package wb.gameObjects;

import wb.GameHandler;
import wb.utils.Team;

import java.awt.*;

public class Worm extends GameObject {

    private Team team;
    private GameHandler gameHandler;
    private int xMouse, yMouse, maxHP;
    private double hp;

    public Worm(int x, int y, Team team, GameHandler gameHandler) {
        this.x = x;
        this.y = y;
        this.team = team;
        this.gameHandler = gameHandler;
        width = 50;
        height = 100;
        maxHP = 100;
        hp = maxHP;
    }

    @Override
    public void tick() {
        hp -= 0.1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x + width /2 - 50, y - 30, 100, 10);

        g.setColor(Color.GREEN);
        g.fillRect(x + width /2 - 50, y - 30, (int) (hp / maxHP * 100), 10);

        g.setColor(Color.WHITE);
        g.drawRect(x - 1 + width /2 - 50, y - 30, 100, 10);


        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,height);

        if (gameHandler.getCurrentTurn()
                == team) {
            g.setColor(Color.RED);
            g.drawLine(getCenterX(), getCenterY(), xMouse, yMouse);
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
