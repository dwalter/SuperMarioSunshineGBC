import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements Runnable {

    static Environment gameEnvironment;
    static Canvas gameCanvas;

    public static void main(String[] args) {
        Images.initializeImages();

        gameCanvas = new Canvas();

        Player mario = new Player(new Point(0,29,3), null);

        gameEnvironment = createBiancoHills(mario);

        mario.environment = gameEnvironment;

        gameEnvironment.render();

        JFrame frame = createFrame();

        frame.setContentPane(gameCanvas);

        Main newMain = new Main();
        newMain.run();
    }

    public static Environment createBiancoHills(Player player) {
        Environment biancoHills = new Environment(player, new Tile[100][100][10], new Actor[0], gameCanvas);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeGrass();

        biancoBuilder.getArea(0,100, 25,30).makeWater();
        biancoBuilder.getArea(0,100, 30, 100, 1,2).makeGrass();
        biancoBuilder.getArea(0,100,0,25, 0,2).makeGrass();
        biancoBuilder.getArea(0,100,30, 31, 1,2).fillType(biancoBuilder.grassEdge);
        biancoBuilder.getArea(0,100, 25,26,0,1).makeCliff();
        biancoBuilder.getArea(10,14, 26,31,1,2).fillType(biancoBuilder.bridge);
        biancoBuilder.getArea(10,17, 40,45, 2,6).makeHouse();


        biancoBuilder.getArea().finalizeArea();
        return biancoHills;
    }

    public static JFrame createFrame() {
        Application frame = new Application("Super Mario Sunshine GBC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(new Dimension(160*5,144*5));
        frame.setResizable(false);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getExtendedKeyCode();
                Application.keyData.setPressed(key);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getExtendedKeyCode();
                Application.keyData.setReleased(key);

            }
        });
        return frame;
    }

    @Override
    public void run() {
        boolean running = true;
        while (running) {
            gameEnvironment.player.move();
            gameEnvironment.render();

            try {
                Thread.sleep(40);
            } catch (InterruptedException ignored) {}
        }
    }
}
