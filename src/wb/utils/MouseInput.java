package wb.utils;

import wb.GameHandler;
import wb.gameObjects.projectiles.BasicRocket;
import wb.gameObjects.Worm;
import wb.hitboxes.Vector2f;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class MouseInput implements MouseListener, MouseMotionListener {

    private GameHandler gameHandler;

    public MouseInput(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (gameHandler.canShoot()) {
            gameHandler.shoot(e.getX(), e.getY());
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMovement(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMovement(e.getX(), e.getY());
    }

    private void mouseMovement(int x, int y) {
        for (Worm worm : gameHandler.getWorms()) {
            worm.setxMouse(x);
            worm.setyMouse(y);
        }
    }
}
