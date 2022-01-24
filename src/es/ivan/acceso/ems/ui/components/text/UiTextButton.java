package es.ivan.acceso.ems.ui.components.text;

import es.ivan.acceso.ems.ui.helpers.ClickListener;

public class UiTextButton extends UiText {

    private ClickListener clicker;

    public UiTextButton(String text, ClickListener clicker) {
        super(text);

        this.clicker = clicker;
    }

    @Override
    public void onClick() {
        this.clicker.onClick();
    }
}
