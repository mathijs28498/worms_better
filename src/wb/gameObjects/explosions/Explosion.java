package wb.gameObjects.explosions;

import wb.GameHandler;
import wb.gameObjects.GameObject;

public abstract class Explosion extends GameObject {

    protected int timer;

    public Explosion(GameHandler gameHandler) {
        super(gameHandler);
    }
}
