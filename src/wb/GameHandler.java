package wb;

import wb.gameObjects.GameObject;
import wb.gameObjects.Ground;
import wb.gameObjects.Worm;
import wb.utils.Team;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private List<Worm> worms;
    private List<GameObject> projectiles;
    private List<GameObject> playGround;
    private BufferedImage background;
    private Team currentTurn;

    public GameHandler() {
        worms = new ArrayList<>();
        projectiles = new ArrayList<>();
        playGround = new ArrayList<>();
        currentTurn = Team.ONE;

        try {
            background = ImageIO.read(new File("res/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addInitGameObjects();


    }

    private void addInitGameObjects() {
        playGround.add(new Ground());

        worms.add(new Worm(50, Game.HEIGHT - 200, Team.ONE, this));
        worms.add(new Worm(Game.WIDTH - 100, Game.HEIGHT - 200, Team.TWO, this));
    }


    public void tick() {
        for (GameObject go : worms) {
            go.tick();
        }
        for (GameObject go : projectiles) {
            go.tick();
        }
        for (GameObject go : playGround) {
            go.tick();
        }
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);

        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).render(g);
        }
        for (GameObject go : playGround) {
            go.render(g);
        }
        for (GameObject go : worms) {
            go.render(g);
        }
    }

    public void addProjectile(GameObject go) {
        projectiles.add(go);
    }

    public List<Worm> getWorms() {
        return worms;
    }

    public void setCurrentTurn(Team currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }
}
