package wb.hitboxes;

//import javafx.scene.shape.Circle;

import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.geom.Ellipse2D;

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
        Vector2f[] vectores = hitbox.getVectors();
        for (int i = 0; i < vectores.length; i++) {
            int next = i + 1;
            if (next == vectores.length) next = 0;

            Vector2f vc = vectores[i];
            Vector2f vn = vectores[next];

            boolean collision = collideLine(vc, vn);
            if (collision) return true;
        }
        return false;
    }

    private boolean collideLine(Vector2f vc, Vector2f vn) {
        // is either end INSIDE the circle?
        // if so, return true immediately
        boolean inside1 = collidePoint(vc);
        boolean inside2 = collidePoint(vn);
        if (inside1 || inside2) return true;

        float len = dist(vc, vn);

        float dot = (float) (( ((center.x-vc.x)*(vn.x-vc.x)) + ((center.y-vc.y)*(vn.y-vc.y)) ) / Math.pow(len,2));

        float closestX = vc.x + (dot * (vn.x-vc.x));
        float closestY = vc.y + (dot * (vn.y-vc.y));
        Vector2f vClosest = new Vector2f(closestX, closestY);

        boolean onSegment = linePoint(vc, vn, vClosest);
        if (!onSegment) return false;

        float distance = dist(vClosest, center);

        if (distance <= radius) {
            return true;
        }
        return false;
    }

    private boolean linePoint(Vector2f vc, Vector2f vn, Vector2f vp) {
        float d1 = dist(vp, vc);
        float d2 = dist(vp, vn);

        float lineLen = dist(vc, vn);

        return d1 + d2 >= lineLen && d1 + d2 <= lineLen;
    }

    private float dist(Vector2f v1, Vector2f v2) {
        return (float) Math.sqrt(Math.pow(v2.x - v1.x, 2) + Math.pow(v2.y - v1.y, 2));
    }

    @Override
    public boolean collidePoint(Vector2f v) {
        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        float distance = dist(v, center);

        // if the distance is less than the circle's
        // radius the point is inside!
        if (distance <= radius) {
            return true;
        }
        return false;
    }

    @Override
    public boolean collideCircle(CircleHitbox hitbox) {
        return false;
    }

    public Shape getShape() {
        return new Ellipse2D.Float(center.x - radius, center.y - radius, radius * 2, radius * 2);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}
