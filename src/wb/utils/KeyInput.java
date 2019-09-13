package wb.utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class KeyInput implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case VK_ESCAPE:
                System.exit(0);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
