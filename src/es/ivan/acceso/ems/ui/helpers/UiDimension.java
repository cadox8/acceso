package es.ivan.acceso.ems.ui.helpers;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class UiDimension {

    @Getter @Setter private int x = 0, y = 0, width = 0, height = 0;

    private int refX = 0, refY = 0;

    public UiDimension() {}

    public UiDimension(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getMaxWidth() {
        return x + width;
    }

    public int getMaxHeight() {
        return y + height;
    }

    public UiDimension add(int x, int y, int width, int height) {
        this.x += x;
        this.y += y;
        this.width += width;
        this.height += height;
        return this;
    }

    public UiDimension set(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public UiDimension addX(int x) {
        setX(getX() + x);
        return this;
    }
    public UiDimension addY(int y) {
        setY(getY() + y);
        return this;
    }
}
