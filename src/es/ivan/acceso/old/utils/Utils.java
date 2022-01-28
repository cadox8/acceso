package es.ivan.acceso.old.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Utils {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(Utils.class.getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(20);
        }
        return null;
    }
}
