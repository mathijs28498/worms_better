package wb;

import wb.utils.Images;
import wb.utils.KeyInput;
import wb.utils.MouseInput;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1080, HEIGHT = WIDTH / 3 * 2;

    private Thread mainThread;
    private GameHandler gameHandler;
    private MouseInput mouseInput;

    private boolean running;


    private Game() {
        Images.loadImages();
        gameHandler = new GameHandler();
        new Window(this, WIDTH, HEIGHT);

        initCanvas();

        start();
    }

    private void initCanvas() {
        addKeyListener(new KeyInput(gameHandler));
        mouseInput = new MouseInput(gameHandler);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);

        requestFocus();
    }

    public void start() {
        if (running) return;

        running = true;
        mainThread = new Thread(this);
        mainThread.start();
    }

    public void stop() {
        try {
            mainThread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        gameHandler.tick();

    }

    private void render() {
        BufferStrategy bs= this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        gameHandler.render(g);

        g.dispose();
        bs.show();

    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
//                System.out.println("FPS: " + frames + " TICKS: " + updates);
                gameHandler.setFps(frames);
                frames = 0;
                updates = 0;
            }
        }

        stop();
    }

    public static void main(String[] args) {
        new Game();
    }
}
