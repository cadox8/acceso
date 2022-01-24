package es.ivan.acceso.ems.input;

import es.ivan.acceso.ems.ui.components.field.UiField;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class KeyManager implements KeyListener {

    private boolean[] keys, justPressed, cantPress;

    @Getter @Setter private UiField writingTo;

    public KeyManager() {
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];

        this.writingTo = null;
    }

    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else {
                if (justPressed[i]) {
                    cantPress[i] = true;
                    justPressed[i] = false;
                }
            }
            if (!cantPress[i] && keys[i]) justPressed[i] = true;
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) return false;
        return keys[keyCode];
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) return false;
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) return;
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) return;
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (writingTo == null) return;
        if (e.getKeyChar() == 8) {
            if (writingTo.getText().toCharArray().length <= 0) return;
            writingTo.setText(String.valueOf(Arrays.copyOfRange(writingTo.getText().toCharArray(), 0, writingTo.getText().toCharArray().length - 1)));
            return;
        }
        if (this.writingTo.getType() == UiField.FieldType.PASSWORD) {
            final StringBuilder password = new StringBuilder();
            for (int i = 0; i < writingTo.getText().length() + 1; i++) password.append("*");
            this.writingTo.setText(password.toString());
        } else {
            writingTo.setText(writingTo.getText() + e.getKeyChar());
        }
    }
}
