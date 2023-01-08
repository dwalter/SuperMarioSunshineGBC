import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

public class Images {

    static HashMap<String, BufferedImage> images = new HashMap<>();
    static String[] imageNames = {
            "water1",
            "cliffT",
            "cliffM",
            "cliffB",
            "grass",
            "grassDetail",
            "pathTR",
            "pathTL",
            "pathTM",
            "pathMR",
            "pathML",
            "pathMM",
            "pathBR",
            "pathBL",
            "pathBM",
            "lpathTR",
            "lpathTL",
            "lpathBR",
            "lpathBL",
            "cliffS",
            "grassEdge",
            "bridgeL",
            "bridgeM",
            "bridgeR",
            "roofMM",
            "wallMR",
            "wallML",
            "wallMM",
            "wallBR",
            "wallBL",
            "wallBM",
            "awningL",
            "awningM",
            "awningR",

            "door",
            "window",
            "roofMR",
            "roofML",
            "roofTM",
            "roofTR",
            "roofTL",
            "roofBM",
            "roofBR",
            "roofBL",
            "marioD1",
            "marioD2",
            "marioD3",
            "marioD4",
            "marioU1",
            "marioU2",
            "marioU3",
            "marioU4",
            "marioR1",
            "marioR2",
            "marioR3",
            "marioR4",
            "marioL1",
            "marioL2",
            "marioL3",
            "marioL4",
            "shadow",
            "waterDrop",
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "coinIcon",
            "xIcon",
            "blueCoinIcon",
            "shineIcon",
            "h2oIcon",
            "fludd10",
            "fludd9",
            "fludd8",
            "fludd7",
            "fludd6",
            "fludd5",
            "fludd4",
            "fludd3",
            "fludd2",
            "fludd1",
            "life8",
            "life7",
            "life6",
            "life5",
            "life4",
            "life3",
            "life2",
            "life1",
            "life0",
            "coin1",
            "coin2",
            "coin3",
            "coin4",
            "wallTopTM",
            "wallTopTR",
            "wallTopTL",
            "wallTopMM",
            "wallTopMR",
            "wallTopML",
            "wallTopBM",
            "wallTopBR",
            "wallTopBL",
            "goop"
    };

    static Image getImage(String s) {
        if (!images.containsKey(s)) {
            throw new IllegalArgumentException("There is no image called " + s);
        }
        return images.get(s);
    }

    static void initializeImages() {
        for (String name : imageNames) {
            try {
                images.put(name, ImageIO.read(new File("src/resources/"+name+".png")));
            } catch (IOException e) {
                System.out.println(name+".png not found");
                e.printStackTrace();
            }
        }
    }
}
