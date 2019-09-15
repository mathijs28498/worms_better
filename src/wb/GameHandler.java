package wb;

import wb.gameObjects.GameObject;
import wb.gameObjects.Ground;
import wb.gameObjects.TestHitbox;
import wb.gameObjects.Worm;
import wb.gameObjects.projectiles.Projectile;
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
    private List<Projectile> projectiles;
    private List<GameObject> playGround;
    private BufferedImage background;
    private Team currentTurn;
    private List<GameObject> objectsToRemove;

    public GameHandler() {
        worms = new ArrayList<>();
        projectiles = new ArrayList<>();
        playGround = new ArrayList<>();
        objectsToRemove = new ArrayList<>();
        currentTurn = Team.ONE;

        try {
            background = ImageIO.read(new File("res/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addInitGameObjects();
    }

    private void addInitGameObjects() {
        playGround.add(new Ground(this));

        worms.add(new Worm(this, 50, Game.HEIGHT - 125, Team.ONE));
        worms.add(new Worm(this, Game.WIDTH - 100, Game.HEIGHT - 125, Team.TWO));
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
        removeGameObjects();
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

    private void removeGameObjects() {
        for (GameObject go: objectsToRemove) {
            if (!projectiles.remove(go))
                worms.remove(go);
        }

        objectsToRemove = new ArrayList<>();
    }

    public void addToRemove(GameObject go) {
        objectsToRemove.add(go);
    }

    public void addProjectile(Projectile go) {
        projectiles.add(go);
    }

    public List<Worm> getWorms() {
        return worms;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setCurrentTurn(Team currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }
}
