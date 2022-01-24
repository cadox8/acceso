package es.ivan.acceso.ems.ui;

import es.ivan.acceso.ems.Ems;
import es.ivan.acceso.ems.ui.helpers.UiDimension;
import lombok.Data;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

@Data
public abstract class UiComponent {

    protected final Ems ems;

    private final long componentID;

    protected UiDimension UIDimension;

    private boolean draggable = false;
    protected boolean hovering = false;
    protected boolean allowHover = true;
    protected boolean enabled = true;

    protected Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 15);

    protected int maxWidth = -1;
    protected int marginX, marginY;

    public UiComponent() {
        this.ems = Ems.getInstance();
        this.componentID = new Random().nextLong();

        this.setUIDimension(new UiDimension());

        this.marginX = 5;
        this.marginY = 10;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract void onClick();


    public void updateFontSize(float fontSize) {
        this.setFont(this.getFont().deriveFont(fontSize));
    }

    // --- ---

    public void onMouseMove(MouseEvent e) {
        hovering = getUIDimension().getBounds().contains(e.getX(), e.getY());
    }

    public void onMouseDragged(MouseEvent e) {
        if (hovering && isDraggable()) {
            getUIDimension().setX(getUIDimension().getX() + (e.getX() - getUIDimension().getX()));
            getUIDimension().setY(getUIDimension().getY() + (e.getY() - getUIDimension().getY()));
        }
    }

    public void onMouseClicked(MouseEvent e) {
        if (hovering) onClick();
    }

    protected void drawImage(Graphics g, BufferedImage[] images) {
        if (images.length > 2) throw new IllegalArgumentException("Images must be 2");

        if (hovering && allowHover) {
            if (images.length == 1) {
                g.drawImage(images[0], getUIDimension().getX(), getUIDimension().getY(), getUIDimension().getWidth() + 5, getUIDimension().getHeight() + 5,null);
            } else {
                g.drawImage(images[1], getUIDimension().getX(), getUIDimension().getY(), getUIDimension().getWidth(), getUIDimension().getHeight(),null);
            }
        } else {
            g.drawImage(images[0], getUIDimension().getX(), getUIDimension().getY(), getUIDimension().getWidth(), getUIDimension().getHeight(),null);
        }
    }

    //
    public UiDimension getUIDimension() {
        return UIDimension == null ? UIDimension = new UiDimension() : UIDimension;
    }
    public void setUIDimension(UiDimension UIDimension) {
        this.UIDimension = UIDimension;
        setMaxWidth(UIDimension.getMaxWidth());
    }
}
