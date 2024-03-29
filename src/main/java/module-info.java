module LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.antlr.antlr4.runtime;
    requires com.google.gson;
    requires org.abego.treelayout.core;
    requires bcrypt;
    requires java.desktop;

    opens com.llat.models.localstorage;
    opens com.llat.models.localstorage.credentials;
    opens com.llat.models.localstorage.settings;
    opens com.llat.models.localstorage.settings.language;
    opens com.llat.models.localstorage.settings.theme;
    opens com.llat.models.localstorage.uidescription;
    opens com.llat.models.localstorage.uidescription.menubar;
    opens com.llat.models.localstorage.uidescription.settingsview;
    opens com.llat.models.localstorage.uidescription.mainview;
    opens com.llat.models.symbols;
    opens com.llat.models.gson;
    opens com.llat.tools;
    opens com.llat.controller;

    //opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}