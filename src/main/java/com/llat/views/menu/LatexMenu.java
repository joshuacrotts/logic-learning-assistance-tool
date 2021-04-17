package com.llat.views.menu;

import com.llat.controller.Controller;
import javafx.scene.control.Menu;

public class LatexMenu {
    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final Menu latexMenu;

    public LatexMenu(Controller controller) {
        this.controller = controller;
        this.latexMenu = new Menu(controller.getUiObject().getMenuBar().getExport().getPdf().getLabel());
    }

    public Menu getMenu() {
        return this.latexMenu;
    }
}
