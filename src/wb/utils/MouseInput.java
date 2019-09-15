package wb.utils;

import wb.GameHandler;
import wb.gameObjects.projectiles.BasicSquareProjectile;
import wb.gameObjects.Worm;
import wb.gameObjects.projectiles.Projectile;
import wb.hitboxes.Vector2f;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class MouseInput implements MouseListener, MouseMotionListener {

    private GameHandler gameHandler;
    private List<Worm> worms;

    public MouseInput(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        worms = gameHandler.getWorms();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (gameHandler.canShoot()) {
            Team ct = gameHandler.getCurrentTurn();

            for (Worm worm : worms) {
                if (worm.getTeam() == ct) {
                    Vector2f vector = worm.getLocation();
                    gameHandler.addProjectile(new BasicSquareProjectile(gameHandler, vector.x, vector.y, ct, e.getX() - vector.x, e.getY() - vector.y));
                }
            }

            if (ct == Team.ONE)
                gameHandler.setCurrentTurn(Team.TWO);
            else
                gameHandler.setCurrentTurn(Team.ONE);
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
        for (Worm worm : worms) {
            worm.setxMouse(x);
            worm.setyMouse(y);
        }
    }
}
