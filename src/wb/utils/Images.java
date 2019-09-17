package wb.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {

    public static BufferedImage background;
    public static BufferedImage basicRocket;
    public static BufferedImage cursor;
    public static BufferedImage fatty;
    public static BufferedImage homingBoy;

    public static void loadImages() {

        try {
            background = ImageIO.read(new File("res/background.jpg"));
            basicRocket = ImageIO.read(new File("res/basicRocket.png"));
            cursor = ImageIO.read(new File("res/cursor.png"));
            fatty = ImageIO.read(new File("res/fatty.png"));
            homingBoy = ImageIO.read(new File("res/homingBoy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
