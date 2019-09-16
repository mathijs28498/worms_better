package wb;

import sun.awt.CustomCursor;
import wb.utils.Images;
import wb.utils.KeyInput;
import wb.utils.MouseInput;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window(Game game, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setFocusable(true);

        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(Images.cursor, new Point(0,0), "customCursor");
//        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        getContentPane().setCursor(cursor);

        add(game);
        setVisible(true);
    }

}
