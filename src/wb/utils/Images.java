package wb.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {

    public static BufferedImage background;
    public static BufferedImage basicRocket;

    public static void loadImages() {

        try {
            background = ImageIO.read(new File("res/background.jpg"));
            basicRocket = ImageIO.read(new File("res/basicRocket.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
