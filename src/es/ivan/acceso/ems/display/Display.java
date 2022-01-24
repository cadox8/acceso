package es.ivan.acceso.ems.display;

import es.ivan.acceso.utils.Utils;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class Display {

    @Getter private JFrame frame;
    @Getter private Canvas canvas;

    @Getter private final Toolkit toolkit;

    private final String title;
    private final Dimension dimension;

    public Display(String title) {
        this(title, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
    }

    public Display(String title, int width, int height) {
        this.title = "Base de datos - " + title;
        this.dimension = new Dimension(width, height);

        this.toolkit = Toolkit.getDefaultToolkit();

        this.createDisplay();
    }

    public Dimension getScreenSize() {
        return this.frame.getSize();
    }

    private void createDisplay() {
        this.frame = new JFrame(title);
        this.frame.setSize(this.dimension);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(this.dimension);
        this.canvas.setMaximumSize(this.dimension);
        this.canvas.setMinimumSize(this.dimension);
        this.canvas.setFocusable(false);

        this.frame.add(canvas);
    }

    public Display withIcon(String path) {
        frame.setIconImage(Utils.loadImage(path));
        return this;
    }
}
