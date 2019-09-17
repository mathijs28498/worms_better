package wb.hitboxes;

import java.awt.*;

public interface Hitbox {

    boolean collide(Hitbox hitbox);

    boolean collidePolygon(PolygonHitbox hitbox);

    boolean collidePoint(Vector2f v);

    boolean collideCircle(CircleHitbox hitbox);

    Shape getShape();
}
