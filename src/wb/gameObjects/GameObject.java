package wb.gameObjects;

import java.awt.*;

public abstract class GameObject {

    protected int x, y, width, height;

    public abstract void tick();

    public abstract void render(Graphics g);

    public int getCenterX() {
        return x + width / 2;
    }

    public int getCenterY() {
        return y + height / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
