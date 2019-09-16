package wb.gameObjects;

import wb.Game;
import wb.GameHandler;
import wb.hitboxes.Hitbox;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;

import java.awt.*;
import java.util.Random;

public class Ground extends GameObject {

    private int leftX, rightX, leftY, rightY, pos;
    private static int maxY = Game.HEIGHT - 50;

    public Ground(GameHandler gameHandler, int pos) {
        super(gameHandler);
        this.pos = pos;
        width = Game.WIDTH / 10;
        leftX = width * pos;
        rightX = width * (pos + 1);
        Random r = new Random();
        if (pos == 0 || pos == 9) {
            leftY = Game.HEIGHT - 100;
            rightY = Game.HEIGHT - 100;
        } else if (pos == 8) {
            leftY = gameHandler.getPlayGround().get(pos - 1).getRightY();
            rightY = Game.HEIGHT - 100;
        } else {
            leftY = gameHandler.getPlayGround().get(pos - 1).getRightY();
            rightY = (int) (Game.HEIGHT - (r.nextInt(200) + 70 * (4.5 - Math.abs(4.5 - pos))));
        }

        calcHitbox();
    }

    private void calcHitbox() {
        hitbox = new PolygonHitbox(
                new Vector2f(leftX, leftY),
                new Vector2f(rightX, rightY),
                new Vector2f(rightX, Game.HEIGHT),
                new Vector2f(leftX, Game.HEIGHT)
        );
    }

    public void hit(int damage, int x) {
        int leftDistance = Math.abs(leftX - x);
        int rightDistance = Math.abs(rightX - x);

        if (leftDistance < rightDistance) {
            lowerLeftY(damage);
        } else if (leftDistance > rightDistance) {
            lowerRightY(damage);
        } else {
            lowerLeftY(damage / 2);
            lowerRightY(damage / 2);
        }

        calcHitbox();
    }

    private void lowerLeftY(int damage) {
        if (leftY < maxY) {
            addLeftY(damage);
            if (pos > 0) {
                Ground g = gameHandler.getPlayGround().get(pos - 1);
                g.addRightY(damage);
                g.calcHitbox();
            }
        }
    }

    private void lowerRightY(int damage) {
        if (rightY < maxY) {
            addRightY(damage);
            if (pos < 9) {
                Ground g = gameHandler.getPlayGround().get(pos + 1);
                g.addLeftY(damage);
                g.calcHitbox();
            }
        }
    }


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.fill(((PolygonHitbox) hitbox).getPolygonFromVectors());
    }

    public int getLeftY() {
        return leftY;
    }

    public void addLeftY(int yDiff) {
        leftY += yDiff;
        if (leftY > maxY) leftY = maxY;
    }

    public int getRightY() {
        return rightY;
    }

    public void addRightY(int yDiff) {
        rightY += yDiff;
        if (rightY > maxY) rightY = maxY;
    }
}
