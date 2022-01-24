package es.ivan.acceso.ems.ui.components.text;

import es.ivan.acceso.ems.ui.UiComponent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class UiText extends UiComponent {

    protected Color textColor = Color.BLACK;
    protected String text;

    /**
     * Generates a Text Object
     */
    public UiText(String text) {
        super();

        this.text = text;
    }

    @Override
    public void tick() {}

    @Override
    public void render(Graphics g) {
        drawString(g);
    }

    @Override
    public void onClick() {}

    private void drawString(Graphics g) {
        g.setColor(this.textColor);
        g.setFont(this.getFont());
        g.drawString(this.text, this.getUIDimension().getX() + 5, this.getUIDimension().getY() + g.getFont().getSize());
    }
}
