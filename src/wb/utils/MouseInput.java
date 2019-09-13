package wb.utils;

import wb.Game;
import wb.GameHandler;
import wb.gameObjects.GameObject;
import wb.gameObjects.Projectile;
import wb.gameObjects.Worm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Random;

public class MouseInput implements MouseListener, MouseMotionListener {

    private GameHandler gameHandler;
    private List<Worm> worms;
    private Random r;

    public MouseInput(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        worms = gameHandler.getWorms();
        r = new Random();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        Team ct = gameHandler.getCurrentTurn();

        for (Worm worm : worms) {
            if (worm.getTeam() == ct) {
                int x = worm.getCenterX();
                int y = worm.getCenterY();
                gameHandler.addProjectile(new Projectile(x, y, ct, r.nextInt(15) + 5, e.getX() - x, e.getY() - y));
            }
        }

        if (ct == Team.ONE)
            gameHandler.setCurrentTurn(Team.TWO);
        else
            gameHandler.setCurrentTurn(Team.ONE);

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
        for (Worm worm : worms) {
            worm.setxMouse(x);
            worm.setyMouse(y);
        }
    }
}
