package wb;

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

        add(game);
        setVisible(true);
    }

}
