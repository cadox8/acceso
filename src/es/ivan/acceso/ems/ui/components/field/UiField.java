package es.ivan.acceso.ems.ui.components.field;

import es.ivan.acceso.ems.ui.UiComponent;
import es.ivan.acceso.ems.ui.components.text.UiText;
import es.ivan.acceso.ems.ui.helpers.ClickListener;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class UiField extends UiComponent {

    private final FieldType type;

    private ClickListener clicker;

    private String text = "";
    private int maxCharacters = 50;

    public UiField() {
        this(FieldType.TEXT);
    }

    public UiField(FieldType type) {
        super();
        this.type = type;
        this.clicker = () -> this.getEms().getKeyManager().setWritingTo(this);
    }

    @Override
    public void tick() {
        if (this.getEms().getKeyManager().getWritingTo() == null) return;
    }

    @Override
    public void render(Graphics g) {
        final UiText text = new UiText(getText());
        text.setUIDimension(getUIDimension());
        text.render(g);
    }

    @Override
    public void onClick() {
        clicker.onClick();
    }

    private boolean canWrite(Graphics g, String text) {
        return g.getFontMetrics(g.getFont()).stringWidth(text) > getUIDimension().getWidth() || text.length() < maxCharacters;
    }

    public enum FieldType {
        TEXT, PASSWORD
    }
}
