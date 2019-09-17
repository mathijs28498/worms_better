package wb;

import wb.gameObjects.GameObject;
import wb.gameObjects.Ground;
import wb.gameObjects.TestCircleHitbox;
import wb.gameObjects.TestPolygonHitbox;
import wb.gameObjects.worms.DamageText;
import wb.gameObjects.worms.Worm;
import wb.gameObjects.explosions.Explosion;
import wb.gameObjects.weapons.Weapon;
import wb.hitboxes.Vector2f;
import wb.hud.HUD;
import wb.utils.Images;
import wb.utils.Team;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameHandler {

    private List<Worm> worms;
    private List<Weapon> weapons;
    private List<Ground> playGround;
    private List<Explosion> explosions;
    private List<GameObject> objectsToRemove;
    private List<DamageText> damageTexts;

    private int wind;
    private Random r;

    private Team currentTurn;
    private int turnCounter;

    private boolean gameWon;
    private int teamOneCounter, teamTwoCounter;
    private Team winner;

    private HUD hud;

    private String winningString;
    private int fps;

    private TestCircleHitbox testCircleHitbox;
    private TestPolygonHitbox testPolygonHitbox;

    public GameHandler() {
        worms = new ArrayList<>();
        weapons = new ArrayList<>();
        playGround = new ArrayList<>();
        explosions = new ArrayList<>();
        objectsToRemove = new ArrayList<>();
        damageTexts = new ArrayList<>();

        currentTurn = Team.ONE;
        r = new Random();
        turnCounter = 0;

        hud = new HUD(this);

        addInitGameObjects();

        wind = r.nextInt(7) - 3;

//        testCircleHitbox = new TestCircleHitbox(this, 500, 300);
//        testPolygonHitbox = new TestPolygonHitbox(this, 0, 0, testCircleHitbox);
    }

    private void addInitGameObjects() {
        for (int i = 0; i < 10; i++) {
            playGround.add(new Ground(this, i));
        }

        worms.add(new Worm(this, 54, Game.HEIGHT - 135, Team.ONE));
        worms.add(new Worm(this, Game.WIDTH - 54, Game.HEIGHT - 135, Team.TWO));
    }


    public void tick() {
        for (GameObject go : worms) {
            go.tick();
        }
        for (GameObject go : weapons) {
            go.tick();
        }
        for (GameObject go : playGround) {
            go.tick();
        }
        for (GameObject go : explosions) {
            go.tick();
        }
        for (DamageText dt : damageTexts) {
            dt.tick();
        }

        if (!gameWon) {
            removeGameObjects();
        }
    }

    public void render(Graphics g) {
        g.drawImage(Images.background, 0, 0, Game.WIDTH, Game.HEIGHT, null);

        for (int i = 0; i < weapons.size(); i++) {
            weapons.get(i).render(g);
        }
        for (GameObject go : playGround) {
            go.render(g);
        }
        for (GameObject go : worms) {
            go.render(g);
        }
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).render(g);
        }

        for (DamageText dt : damageTexts) {
            dt.render(g);
        }

        hud.render(g);
    }

    private void removeGameObjects() {
        for (GameObject go: objectsToRemove) {
            if (go instanceof Weapon)
                weapons.remove(go);
            else if (go instanceof Explosion)
                explosions.remove(go);
            else if (go instanceof DamageText)
                damageTexts.remove(go);
            else if (go instanceof Worm) {
                worms.remove(go);
                subtractFromWormCounter(((Worm) go).getTeam());
            }
        }

        checkGameWon();
    }

    private void checkGameWon() {
        if (teamOneCounter == 0 || teamTwoCounter == 0) {
            gameWon = true;
            if (teamOneCounter == 0) winner = Team.TWO;
            else winner = Team.ONE;

            if (turnCounter < 200) winningString = "WINNER!!!!";
            else if (turnCounter < 250) winningString = "TOOK YOU LONG ENOUGH";
            else winningString = "FINALLY...";
        }
    }

    public void shoot() {
        for (Worm worm : worms) {
            if (worm.getTeam() == currentTurn) {
                worm.shoot();
//                Vector2f vector = worm.getShootLocation();
//                float power = worm.getPower() / worm.getMaxPower();
//                addWeapon(new BasicRocket(this, vector.x, vector.y, currentTurn, power,x - vector.x, y - vector.y));
            }
        }

        if (currentTurn == Team.ONE)
            currentTurn = Team.TWO;
        else
            currentTurn = Team.ONE;

        wind = r.nextInt(7) - 3;
        turnCounter++;
    }

    public boolean canShoot() {
        return !gameWon && weapons.size() == 0 && explosions.size() == 0;
    }

    public float getWind() {
        return wind;
    }

    public Team getWinner() {
        return winner;
    }

    public String getWinningString() {
        return winningString;
    }

    public void subtractFromWormCounter(Team team) {
        switch (team) {
            case ONE:
                teamOneCounter--;
                break;
            case TWO:
                teamTwoCounter--;
                break;
        }
    }

    public void addToWormCounter(Team team) {
        switch (team) {
            case ONE:
                teamOneCounter++;
                break;
            case TWO:
                teamTwoCounter++;
                break;
        }
    }

    public void reset() {
        gameWon = false;

        worms = new ArrayList<>();
        weapons = new ArrayList<>();
        playGround = new ArrayList<>();
        explosions = new ArrayList<>();
        objectsToRemove = new ArrayList<>();
        currentTurn = Team.ONE;
        turnCounter = 0;

        teamOneCounter = 0;
        teamTwoCounter = 0;

        addInitGameObjects();

        wind = r.nextInt(7) - 3;
    }

    public TestPolygonHitbox getTestPolygonHitbox() {
        return testPolygonHitbox;
    }

    public void addToRemove(GameObject go) {
        objectsToRemove.add(go);
    }

    public void addWeapon(Weapon go) {
        weapons.add(go);
    }

    public void addDamageText(DamageText go) {
        damageTexts.add(go);
    }

    public void addExplosion(Explosion go) {
        explosions.add(go);
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getFps() {
        return fps;
    }

    public List<Worm> getWorms() {
        return worms;
    }

    public List<Ground> getPlayGround() {
        return playGround;
    }

    public void setCurrentTurn(Team currentTurn) {
        this.currentTurn = currentTurn;
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }
}
