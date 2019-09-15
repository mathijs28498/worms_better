package wb.hitboxes;

public class Vector2f {
    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(float angle) {
        x = (float) (x * Math.cos(angle) - y * Math.sin(angle));
        y = (float) (y * Math.cos(angle) + x * Math.sin(angle));
    }

    public void add(Vector2f v) {
        x += v.x;
        y += v.y;
    }

    public static Vector2f addRotated(Vector2f v1, Vector2f v2, float angle) {
        return new Vector2f((float) (v1.x + v2.x * Math.cos(angle) - v2.y * Math.sin(angle)),
                            (float) (v1.y + v2.y * Math.cos(angle) + v2.x * Math.sin(angle)));
    }
}
