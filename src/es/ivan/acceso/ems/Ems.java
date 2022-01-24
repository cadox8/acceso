package es.ivan.acceso.ems;

import es.ivan.acceso.ems.database.Database;
import es.ivan.acceso.ems.display.Display;
import es.ivan.acceso.ems.input.KeyManager;
import es.ivan.acceso.ems.input.MouseManager;
import es.ivan.acceso.ems.state.LoginState;
import es.ivan.acceso.ems.state.State;
import es.ivan.acceso.utils.Log;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.sql.SQLException;

public class Ems implements Runnable {

    @Getter private static Ems instance;

    private boolean running = false;
    private Thread thread;

    // --- ---

    @Getter private final Display display;
    @Getter private final KeyManager keyManager;
    @Getter private final MouseManager mouseManager;

    public Ems() {
        instance = this;
        this.display = new Display("Base de Datos");
        this.keyManager = new KeyManager();
        this.mouseManager = new MouseManager();
    }

    public void load() {
        this.display.getFrame().addKeyListener(keyManager);
        this.display.getFrame().addMouseListener(mouseManager);
        this.display.getFrame().addMouseMotionListener(mouseManager);
        this.display.getCanvas().addMouseListener(mouseManager);
        this.display.getCanvas().addMouseMotionListener(mouseManager);
        this.display.getCanvas().addMouseWheelListener(mouseManager);

        State.setState(new LoginState());

        try {
            Log.debug(new Database("localhost", "root", "", "examen").openConnection().toString());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        this.keyManager.tick();
        if (State.getState() != null) State.getState().tick();
    }

    private void render() {
        final BufferStrategy bs = this.display.getCanvas().getBufferStrategy();
        if (bs == null) {
            this.display.getCanvas().createBufferStrategy(3);
            return;
        }
        final Graphics g = bs.getDrawGraphics();
        g.clearRect(0, 0, this.display.getScreenSize().width, this.display.getScreenSize().height);

        if (State.getState() != null) State.getState().render(g);

        bs.show();
        g.dispose();
    }

    public void run() {
        int fps = 60;
        double timePerTick = (1000000000D / fps);
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (this.running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        if (this.running) return;
        this.running = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    public synchronized void stop() {
        if (!this.running) return;
        this.running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
