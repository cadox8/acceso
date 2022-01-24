package es.ivan.acceso.ems.state;

import es.ivan.acceso.ems.ui.UiManager;
import es.ivan.acceso.ems.ui.components.base.UiBlock;
import es.ivan.acceso.ems.ui.components.text.UiText;
import es.ivan.acceso.ems.ui.helpers.UiDimension;
import es.ivan.acceso.utils.Log;

import java.awt.*;

public class LoginState extends State {

    private final UiManager uiManager;

    public LoginState() {
        this.uiManager = new UiManager();

        final UiBlock background = new UiBlock();
        background.setUIDimension(new UiDimension(0, 0, this.getScreenSize().width, this.getScreenSize().height));

        final UiText test = new UiText("ENTRAR");
        test.updateFontSize(30);
        test.setTextColor(Color.WHITE);
        test.setUIDimension(new UiDimension(this.getScreenSize().width / 2 - 100, 60, 50, 300));

        this.uiManager.addObject(background);
        this.uiManager.addObject(test);

        this.getEms().getMouseManager().setUiManager(this.uiManager);
    }

    @Override
    public void tick() {
        this.uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        this.uiManager.render(g);
    }
}
