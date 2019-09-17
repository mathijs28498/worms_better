package wb.gameObjects;

import wb.Game;
import wb.GameHandler;
import wb.hitboxes.PolygonHitbox;
import wb.hitboxes.Vector2f;

import java.awt.*;
import java.util.Random;

public class Ground extends GameObject {

    private static int maxY = Game.HEIGHT - 20;
    private int xStart, yBot, topAmount;
    float widthTopPiece;
    private Random r;


    public Ground(GameHandler gameHandler, int xStart, int yBot, int width, int topAmount) {
        super(gameHandler);
        this.xStart = xStart;
        this.yBot = yBot;
        this.width = width;
        this.topAmount = topAmount;
        widthTopPiece = width / topAmount;

        r = new Random();

        initHitbox();
    }

    private void initHitbox() {
        Vector2f[] vectors = new Vector2f[topAmount + 3];

        for (int i = 0; i <= topAmount; i++) {
            if (i <= 1 || i >= topAmount - 1)
                vectors[i] = new Vector2f(widthTopPiece * i + xStart, Game.HEIGHT - 100);
            else {
                float middle = topAmount / 2f;
                vectors[i] = new Vector2f(widthTopPiece * i + xStart, (float) (Game.HEIGHT - (r.nextInt(200) + 70 * (middle - Math.abs(middle - i)))));
            }
        }

        vectors[topAmount + 1] = new Vector2f(width + xStart, yBot);
        vectors[topAmount + 2] = new Vector2f(xStart, yBot);

        hitbox = new PolygonHitbox(vectors);
    }

    public void hit(int damage, float x) {
        PolygonHitbox polyHitbox = (PolygonHitbox) hitbox;
        Vector2f[] vectors = polyHitbox.getVectors();

        Vector2f vectorLeft = null, vectorRight = null;

        for (int i = 1; i <= topAmount; i++) {
            int compare = (int) (x - (xStart + i * widthTopPiece));
            if (compare == 0) {
                vectorLeft = vectors[i];
                break;
            } else if (compare < 0) {
                vectorLeft = vectors[i - 1];
                vectorRight = vectors[i];
                break;
            }
        }

        if (vectorRight == null) {
            vectorLeft.y = vectorLeft.y + damage;
        } else if (vectorLeft.y < maxY || vectorRight.y < maxY) {
            float totalDistance = Math.abs(vectorLeft.x - vectorRight.x);
            float percentageLeft = Math.abs(vectorLeft.x - x) / totalDistance;
            float percentageRight = Math.abs(vectorRight.x - x) / totalDistance;
            float leftDamage = (1 - percentageLeft) * damage;
            float rightDamage = (1 - percentageRight) * damage;
            if (vectorLeft.y < maxY) {
                vectorLeft.y = vectorLeft.y + leftDamage;
                if (vectorLeft.y > maxY) vectorLeft.y = maxY;
            }
            if (vectorRight.y < maxY) {
                vectorRight.y = vectorRight.y +  rightDamage;
                if (vectorRight.y > maxY) vectorRight.y = maxY;
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
        g2d.fill(hitbox.getShape());
    }

}
