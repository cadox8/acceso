package es.ivan.acceso.ems.ui.components.base;

import es.ivan.acceso.ems.ui.UiComponent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class UiBlock extends UiComponent {

    @Getter @Setter private Color background;

    @Getter @Setter private boolean rounded = false;

    @Getter private int roundRadius = 35;

    @Getter @Setter private boolean filled;

    public UiBlock() {
        this(Color.DARK_GRAY, true);
    }
    public UiBlock(Color background) {
        this(background, true);
    }
    public UiBlock(Color background, boolean filled) {
        super();
        this.background = background;
        this.filled = filled;
    }

    @Override
    public void tick() {}

    @Override
    public void render(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(this.background);
        final Rectangle r = this.getUIDimension().getBounds();

        if (this.isRounded()) {
            final RoundRectangle2D r2 = new RoundRectangle2D.Double(r.getX(), r.getY(), r.getWidth(), r.getHeight(), roundRadius, roundRadius);
            g2.draw(r2);
            if (this.isFilled()) g2.fill(r2);
        } else {
            g2.draw(r);
            if (this.isFilled()) g2.fill(r);
        }
    }

    @Override
    public void onClick() {
    }

    public void setRoundRadius(int roundRadius) {
        if (roundRadius < 0 || roundRadius > 100) throw new IllegalArgumentException("The round radius must be between 0 and 100 (" + roundRadius + ")");
        this.roundRadius = roundRadius;
        this.setRounded(true);
    }
}
