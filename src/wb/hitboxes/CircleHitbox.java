package wb.hitboxes;

public class CircleHitbox implements Hitbox {

    private Vector2f center;
    private int radius;

    public CircleHitbox(Vector2f center, int radius) {
        this.center = center;
        this.radius = radius;
    }



    @Override
    public boolean collide(Hitbox hitbox) {
        if (hitbox instanceof PolygonHitbox)
            return collidePolygon((PolygonHitbox) hitbox);
        else if (hitbox instanceof CircleHitbox)
            return collideCircle((CircleHitbox) hitbox);
        return false;
    }

    @Override
    public boolean collidePolygon(PolygonHitbox hitbox) {
        return false;
    }

    @Override
    public boolean collidePoint(Vector2f v) {
        return false;
    }

    @Override
    public boolean collideCircle(CircleHitbox hitbox) {
        return false;
    }
}
