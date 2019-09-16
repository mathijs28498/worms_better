package wb.hud;

import wb.Game;
import wb.GameHandler;

import java.awt.*;

public class HUD {

    private GameHandler gameHandler;

    private Font winningFontBig;
    private Font winningFontSmall;

    private Font fpsFont;

    private Font windFont;

    public HUD(GameHandler gameHandler) {
        this.gameHandler = gameHandler;

        winningFontBig = new Font("Helvetica", Font.BOLD, 50);
        winningFontSmall = new Font("Helvetica", Font.BOLD, 25);
        fpsFont = new Font("Courier", Font.PLAIN, 15);
        windFont = new Font("Courier", Font.BOLD, 18);
    }

    public void render(Graphics g) {
        if (gameHandler.isGameWon()) {
            switch (gameHandler.getWinner()) {
                case ONE:
                    g.setColor(Color.BLUE);
                    break;
                case TWO:
                    g.setColor(Color.ORANGE);
                    break;
            }
            g.setFont(winningFontBig);
            String winningString = gameHandler.getWinningString();
            g.drawString(winningString, (Game.WIDTH - g.getFontMetrics().stringWidth(winningString)) / 2, Game.HEIGHT / 2);

            g.setFont(winningFontSmall);
            String restartString = "Restart by pressing 'R'";
            g.drawString(restartString, (Game.WIDTH - g.getFontMetrics().stringWidth(restartString)) / 2, Game.HEIGHT / 2 + 32);
        }

        g.setColor(Color.BLACK);
        g.setFont(fpsFont);
        String fpsString = "fps: " + gameHandler.getFps();
        g.drawString(fpsString, Game.WIDTH - g.getFontMetrics().stringWidth(fpsString) - 20, 20);

        g.setColor(Color.WHITE);
        g.setFont(windFont);

        int wind = (int) gameHandler.getWind();
        String windString = (wind < 0 ? "< " : "") + Math.abs(wind) + (wind > 0 ? " >" : "");

        if (wind <= 0) g.drawString(windString, Game.WIDTH / 2 - g.getFontMetrics().stringWidth(windString), 30);
        else g.drawString(windString, Game.WIDTH / 2 - g.getFontMetrics().stringWidth("0"), 30);
    }
}
