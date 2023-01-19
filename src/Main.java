import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements Runnable {
    static Environment gameEnvironment;
    static Canvas gameCanvas;
    static HUD hud = new HUD();

    public static void main(String[] args) {
        Images.initializeImages();

        gameCanvas = new Canvas();

        Player mario = new Player(new Point(22,22,8), gameEnvironment);

        gameEnvironment = createBiancoHills(mario); //createGelatoBeach(mario); //createBiancoHills(mario);

        gameEnvironment.actors.add(new ActorLibrary.Shadow(mario, gameEnvironment));

        gameEnvironment.render();

        JFrame frame = createFrame();

        frame.setContentPane(gameCanvas);

        HUD.Meter coinMeter =  new HUD.Meter(Images.getImage("coinIcon"));
        HUD.Meter blueCoinMeter =  new HUD.Meter(Images.getImage("blueCoinIcon"));

        hud.addMeter("coin", coinMeter);
        hud.addMeter("blueCoin", blueCoinMeter);

        hud.meterToShow = blueCoinMeter;

        Main newMain = new Main();
        newMain.run();
    }

    public static Environment createBiancoHills(Player player) {
        Environment biancoHills = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);
        EnvironmentBuilder biancoBuilder = new EnvironmentBuilder(biancoHills);

        biancoBuilder.getFloor().makeGrass();

        biancoBuilder.getArea(0,100, 25,30).makeWater();
        biancoBuilder.getArea(0,100, 30, 100, 1,2).makeGrass();
        biancoBuilder.getArea(0,100,0,25, 0,2).makeGrass();
        biancoBuilder.getArea(0,100,30, 31, 1,2).fillType(biancoBuilder.grassEdge);
        biancoBuilder.getArea(0,100, 25,26,0,1).makeCliff();

        biancoBuilder.getArea(10,14, 26,31,1,2).fillType(biancoBuilder.bridge);
        biancoBuilder.getArea(18,22, 26,31,1,2).fillType(biancoBuilder.bridge);

        biancoBuilder.getArea(9,15, 31,40, 1,2).makePath();
        biancoBuilder.getArea(17,23, 31,40, 1,2).makePath();


        biancoBuilder.getArea(10,17, 40,45, 2,6).makeHouse();
        biancoBuilder.getArea(20,25, 40,45, 2,7).makeHouse();
        biancoBuilder.getArea(8, 27, 38, 47,1,2).makePath();



        biancoBuilder.getArea(30,37, 0,40, 2,6).fillType(biancoBuilder.wall);
        biancoBuilder.getArea(0, 100, 20,22,2, 6).fillType(biancoBuilder.wall);


        biancoBuilder.getArea(11,16, 39,40, 2,3).placeCoins();
        biancoBuilder.getArea(14, 19, 28,29, 2,4).placeShadowDelicateCoinArch();

        biancoBuilder.getArea(11, 20, 51,61, 2,3).placeGoop();
        biancoBuilder.getArea(10, 21, 49,61, 1,2).makePath();


        biancoBuilder.getArea(35, 50, 40,47, 1,2).makePath();



        biancoBuilder.getArea().finalizeArea();

        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey",new Point(25+2, 44,2), null, 25+2, 28+2,2));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey",new Point(29+2, 44,2), null, 28+2, 31+2,2));
        biancoHills.addActorWithShadow(new ActorLibrary.Enemy("pokey",new Point(33+2, 44,2), null, 31+2, 34+2,2));




        //gameEnvironment.addActor(new ActorLibrary.Goop(new Point(12, 34, 2), gameEnvironment));

        return biancoHills;
    }

    public static Environment createCoronaMountain(Player player) {
        Environment coronaMountain = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);

        EnvironmentBuilder coronaBuilder = new EnvironmentBuilder(coronaMountain);


        coronaBuilder.getFloor().fillType(coronaBuilder.lava);

        coronaBuilder.getArea(19,25,10,16,1,6).makePlatform();

        coronaBuilder.getArea(20,26,20,24,1,6).makePlatform();

        coronaBuilder.getArea(22,30,29,32,1,6).makePlatform();

        coronaBuilder.getArea(26,32,37,41,1,6).makePlatform();


        coronaBuilder.getArea().finalizeArea();

        return coronaMountain;
    }

    public static Environment createGelatoBeach(Player player) {
        Environment gelatoBeach = new Environment(player, new Tile[100][100][10], null, gameCanvas, hud);
        EnvironmentBuilder gelatoBuilder = new EnvironmentBuilder(gelatoBeach);

        gelatoBuilder.getArea(0,100,0,30,0,1).fillType(gelatoBuilder.sand);
        gelatoBuilder.getArea(0,100,30,31,0,1).fillType(gelatoBuilder.beach);
        gelatoBuilder.getArea(0,100,31,100,0,1).fillType(gelatoBuilder.water);


        gelatoBeach.addActorWithShadow(new ActorLibrary.Enemy("cataquack", new Point(30, 27, 1), gelatoBeach, 10, 40,1));

        gelatoBeach.addActorWithShadow(new ActorLibrary.Enemy("cataquack", new Point(35, 23, 1), gelatoBeach, 10, 40,1));


        gelatoBuilder.getArea().finalizeArea();

        return gelatoBeach;
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
            gameEnvironment.runFrame();
            gameEnvironment.render();

            hud.render(gameEnvironment.player.location, gameCanvas);

            Application.frameCount++;

            // System.out.println(gameEnvironment.player.location.toString());

            try {
                Thread.sleep(40);
            } catch (InterruptedException ignored) {}
        }
    }
}
