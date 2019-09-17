package wb.gameObjects.worms;

import wb.GameHandler;
import wb.gameObjects.GameObject;
import wb.hitboxes.Vector2f;
import wb.utils.Team;

import java.awt.*;
import java.util.Random;

public class DamageText extends GameObject {

    private String damage;
    private int timer, currentTicks;
    private Font font;

    public DamageText(GameHandler gameHandler, int damage, int timer, float x, float y) {
        super(gameHandler);
        this.damage = String.valueOf(damage);
        this.timer = timer;

        Random r = new Random();
        float xReal = r.nextInt(30) - 15 + x;
        float yReal = r.nextInt(30) - 15 + y;

        location = new Vector2f(xReal, yReal);
        font = new Font("Dialog", Font.BOLD, 15);
    }

    @Override
    public void tick() {
        if (currentTicks >= timer)
            gameHandler.addToRemove(this);

        currentTicks++;
        location.y -= 0.5;
    }

    @Override
    public void render(Graphics g) {
        float alpha = (timer - currentTicks + 0f) / timer;
        if (alpha < 0) alpha = 0;

        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(damage, (int) location.x - g.getFontMetrics().stringWidth(damage) / 2, (int) location.y);
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }



}
