package es.ivan.acceso.ems.state;

import es.ivan.acceso.ems.Ems;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public abstract class State {

    @Getter @Setter private static State state = null;

    @Getter private final Ems ems;
    @Getter Dimension screenSize;

    public State() {
        this.ems = Ems.getInstance();
        this.screenSize = this.getEms().getDisplay().getScreenSize();
    }

    public abstract void tick();
    public abstract void render(Graphics g);
}
