package wb.gameObjects.explosions;

import wb.GameHandler;
import wb.hitboxes.Vector2f;

import java.awt.*;

public class BasicExplosion extends Explosion {

    private float alpha;
    private int maxWidth, maxHeight, maxTimer;

    public BasicExplosion(GameHandler gameHandler, Vector2f location, int maxWidth, int maxHeight, int timer) {
        super(gameHandler);
        this.location = location;
        maxTimer = timer;
        this.timer = timer;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        width = maxWidth / 4;
        height = maxHeight / 4;
        alpha = 1;
    }


    @Override
    public void tick() {
        if (timer == 0)
            gameHandler.addToRemove(this);

        timer--;

        if (alpha > 0)
            alpha -= 1f / maxTimer;
        if (alpha < 0)
            alpha = 0;

        if (width < maxWidth)
            width += maxWidth / 12f;
        if (height < maxHeight)
            height += maxHeight / 12f;


    }

    @Override
    public void render(Graphics g) {
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.WHITE);
        g.fillOval((int) location.x - width / 2, (int) location.y - height / 2, width, height);
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
