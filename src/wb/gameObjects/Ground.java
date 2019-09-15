package wb.gameObjects;

import wb.Game;
import wb.GameHandler;
import wb.hitboxes.Vector2f;

import java.awt.*;

public class Ground extends GameObject {

    private int width, height;

    public Ground(GameHandler gameHandler) {
        super(gameHandler);
        width = Game.WIDTH;
        height = 100;
        location = new Vector2f(0, Game.HEIGHT - height);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) location.x,(int) location.y,width,height);
    }
}
