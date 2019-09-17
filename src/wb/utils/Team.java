package wb.utils;

import java.awt.*;

public enum Team {
    ONE(Color.BLUE),
    TWO(Color.ORANGE);

    private Color color;

    Team(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
