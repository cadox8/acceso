package es.ivan.acceso.ems.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UiManager {

    private final ArrayList<UiComponent> objects;

    /**
     * The constructor of the class
     */
    public UiManager() {
        objects = new ArrayList<>();
    }

    public synchronized void addObject(UiComponent object) {
        objects.add(object);
    }

    /**
     * Gets an specific object
     *
     * @param componentID The ID of the component to search
     */
    public UiComponent getObject(long componentID) {
        return objects.stream().filter(c -> c.getComponentID() == componentID).findAny().orElse(null);
    }

    public synchronized void tick() {
        objects.stream().filter(UiComponent::isEnabled).forEach(UiComponent::tick);
    }

    public synchronized void render(Graphics g) {
        objects.stream().filter(UiComponent::isEnabled).forEach(o -> o.render(g));
    }

    public void onMouseDragged(MouseEvent e) {
        objects.forEach(o -> o.onMouseDragged(e));
    }
    public void onMouseMove(MouseEvent e) {
        objects.forEach(o -> o.onMouseMove(e));
    }
    public void onMouseClicked(MouseEvent e) {
        objects.forEach(o -> o.onMouseClicked(e));
    }

    public ArrayList<UiComponent> getObjects() {
        return this.objects;
    }
}
