package wb.utils;

import wb.Game;
import wb.GameHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class KeyInput implements KeyListener {

    private GameHandler gameHandler;
    private Game game;

    public KeyInput(GameHandler gameHandler) {
        this.game = game;
        this.gameHandler = gameHandler;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();


        switch (keyCode) {
            case VK_E:
                if (gameHandler.isWaitingTurn())
                    gameHandler.getWormTurn().nextWeapon();
                break;
            case VK_Q:
                if (gameHandler.isWaitingTurn())
                    gameHandler.getWormTurn().prevWeapon();
                break;
            case VK_R:
                if (gameHandler.isGameWon())
                    gameHandler.reset();
                break;
            case VK_ESCAPE:
                System.exit(0);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
