package es.ivan.acceso.ems.ui.components.button;

import es.ivan.acceso.ems.ui.components.base.UiBlock;
import es.ivan.acceso.ems.ui.components.text.UiTextButton;
import es.ivan.acceso.ems.ui.helpers.ClickListener;
import lombok.Getter;

import java.awt.*;

public class UiButton extends UiTextButton {

    @Getter private final UiBlock block;

    /**
     * Generates a Button Object
     */
    public UiButton(String text, ClickListener clicker) {
        super(text, clicker);
        this.setTextColor(Color.RED);

        this.block = new UiBlock(Color.YELLOW);
        this.block.setUIDimension(this.getUIDimension());
    }

    @Override
    public void render(Graphics g) {
        g.setFont(this.getFont());
        block.render(g);
        super.render(g);
    }
}
