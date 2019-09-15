package wb.hitboxes;

import java.awt.*;

public class PolygonHitbox {

    private Vector2f[] vectors;

    public PolygonHitbox(Vector2f... vectors) {
        this.vectors = vectors;
    }

    public boolean collidePolygon(PolygonHitbox ph) {
        Vector2f[] vectors = ph.getVectors();
        for (int i = 0; i < vectors.length; i++) {
            if (collidePoint(vectors[i])) return true;
        }
        return false;
    }

    public boolean collidePoint(Vector2f v) {
        boolean res = false;
        for (int i = 0; i < vectors.length; i++) {
            int next = i + 1;
            if (next == vectors.length) next = 0;
            Vector2f vc = vectors[i];
            Vector2f vn = vectors[next];
            if (((vc.y >= v.y && vn.y < v.y) || (vc.y < v.y && vn.y >= v.y)) &&
                    (v.x < (vn.x-vc.x)*(v.y-vc.y) / (vn.y-vc.y)+vc.x)) {
                res = !res;
            }
        }

        return res;
    }

    public Vector2f[] getVectors() {
        return vectors;
    }

    public Polygon getPolygonFromVectors() {
        Polygon res = new Polygon();

        for (Vector2f v : vectors) {
            res.addPoint((int) v.x, (int) v.y);
        }

        return res;
    }

    public void setVectors(Vector2f... vectors) {
        this.vectors = vectors;
    }

}
