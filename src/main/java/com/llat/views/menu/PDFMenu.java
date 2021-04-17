package com.llat.views.menu;

import com.llat.controller.Controller;
import javafx.scene.control.Menu;

public class PDFMenu {

    /**
     *
     */
    private final Controller controller;

    /**
     *
     */
    private final Menu pdfMenu;

    public PDFMenu(Controller controller) {
        this.controller = controller;
        this.pdfMenu = new Menu(controller.getUiObject().getMenuBar().getExport().getPdf().getLabel());
    }

    public Menu getMenu() {
        return this.pdfMenu;
    }

}
