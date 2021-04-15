package com.llat.models.localstorage.uidescription.menubar;

public class File {
    public String label;
    public NewProject newProject;
    public OpenProject openProject;
    public Export export;
    public Login login;
    public Register register;
    public Settings settings;
    public Exit exit;

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public NewProject getNewProject() {
        return this.newProject;
    }

    public void setNewProject(NewProject newProject) {
        this.newProject = newProject;
    }

    public OpenProject getOpenProject() {
        return this.openProject;
    }

    public void setOpenProject(OpenProject openProject) {
        this.openProject = openProject;
    }

    public Export getExport() {
        return this.export;
    }

    public void setExport(Export export) {
        this.export = export;
    }

    public Login getLogin() {
        return this.login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Register getRegister() {
        return this.register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Exit getExit() {
        return this.exit;
    }

    public void setExit(Exit exit) {
        this.exit = exit;
    }

    @Override
    public String toString() {
        return "File{" +
                "label='" + this.label + '\'' +
                ", newProject=" + this.newProject +
                ", openProject=" + this.openProject +
                ", export=" + this.export +
                ", login=" + this.login +
                ", register=" + this.register +
                ", settings=" + this.settings +
                ", exit=" + this.exit +
                '}';
    }
}
