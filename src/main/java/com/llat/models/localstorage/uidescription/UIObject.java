package com.llat.models.localstorage.uidescription;

import com.llat.models.localstorage.LocalStorage;
import com.llat.models.localstorage.uidescription.mainview.MainView;
import com.llat.models.localstorage.uidescription.menubar.MenuBar;
import com.llat.models.localstorage.uidescription.settingsview.SettingsView;

public class UIObject extends LocalStorage {
    public MenuBar menuBar;
    public MainView mainView;
    public SettingsView settingsView;
    public LoginViewObject loginView;
    public RegisterView registerView;

    public UIObject(MenuBar menuBar, MainView mainView, SettingsView settingsView, LoginViewObject loginViewObject, RegisterView registerView) {
        this.menuBar = menuBar;
        this.mainView = mainView;
        this.settingsView = settingsView;
        this.loginView = loginViewObject;
        this.registerView = registerView;
    }

    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    public void setMenuBar(MenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public MainView getMainView() {
        return this.mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public SettingsView getSettingsView() {
        return this.settingsView;
    }

    public void setSettingsView(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    public LoginViewObject getLoginView() {
        return this.loginView;
    }

    public void setLoginView(LoginViewObject loginViewObject) {
        this.loginView = loginViewObject;
    }

    public RegisterView getRegisterView() {
        return this.registerView;
    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public String toString() {
        return "UIObject{" +
                "menuBar=" + this.menuBar +
                ", mainView=" + this.mainView +
                ", settingsView=" + this.settingsView +
                ", loginView=" + this.loginView +
                ", registerView=" + this.registerView +
                '}';
    }
}
