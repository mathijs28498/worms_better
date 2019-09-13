package wb.gameObjects;

import wb.Game;

import java.awt.*;

public class Ground extends GameObject {

    private int width, height;

    public Ground() {
        width = Game.WIDTH;
        height = 100;
        x = 0;
        y = Game.HEIGHT - height;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x,y,width,height);
    }
}
