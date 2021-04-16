package com.llat.models.localstorage.uidescription.menubar;

public class MenuBar {
    public File file;
    public Export export;
    public Help help;

    public MenuBar(File file, Export export, Help help) {
        this.file = file;
        this.export = export;
        this.help = help;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Export getExport() {
        return export;
    }

    public void setExport(Export export) {
        this.export = export;
    }

    public Help getHelp() {
        return help;
    }

    public void setHelp(Help help) {
        this.help = help;
    }

    @Override
    public String toString() {
        return "MenuBar{" +
                "file=" + file +
                ", export=" + export +
                ", help=" + help +
                '}';
    }
}
