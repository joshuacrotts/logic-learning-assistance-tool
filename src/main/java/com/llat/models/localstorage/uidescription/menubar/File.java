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
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public NewProject getNewProject() {
        return newProject;
    }

    public void setNewProject(NewProject newProject) {
        this.newProject = newProject;
    }

    public OpenProject getOpenProject() {
        return openProject;
    }

    public void setOpenProject(OpenProject openProject) {
        this.openProject = openProject;
    }

    public Export getExport() {
        return export;
    }

    public void setExport(Export export) {
        this.export = export;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Exit getExit() {
        return exit;
    }

    public void setExit(Exit exit) {
        this.exit = exit;
    }

    @Override
    public String toString() {
        return "File{" +
                "label='" + label + '\'' +
                ", newProject=" + newProject +
                ", openProject=" + openProject +
                ", export=" + export +
                ", login=" + login +
                ", register=" + register +
                ", settings=" + settings +
                ", exit=" + exit +
                '}';
    }
}
