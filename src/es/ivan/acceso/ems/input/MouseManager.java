package es.ivan.acceso.ems.input;

import es.ivan.acceso.ems.ui.UiManager;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.*;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {

    @Getter private boolean leftPressed, rightPressed;
    @Getter private int mouseX, mouseY, mouseXClick, mouseYClick;
    @Getter @Setter private UiManager uiManager;

    //Mouse
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftPressed = true;
        if (e.getButton() == MouseEvent.BUTTON3) rightPressed = true;

        mouseXClick = e.getX();
        mouseYClick = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) leftPressed = false;
        if (e.getButton() == MouseEvent.BUTTON3) rightPressed = false;

        mouseXClick = 0;
        mouseYClick = 0;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (uiManager != null) uiManager.onMouseMove(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (uiManager != null) uiManager.onMouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseXClick = e.getX();
        mouseYClick = e.getY();

        if (uiManager != null) uiManager.onMouseClicked(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    //Wheel
    public void mouseWheelMoved(MouseWheelEvent e) {}
}
