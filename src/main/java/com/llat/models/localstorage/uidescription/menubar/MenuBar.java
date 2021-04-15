package com.llat.models.localstorage.uidescription.menubar;

public class MenuBar {
    public File file;
    public Help help;

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Help getHelp() {
        return this.help;
    }

    public void setHelp(Help help) {
        this.help = help;
    }

    @Override
    public String toString() {
        return "MenuBar{" +
                "file=" + this.file +
                ", help=" + this.help +
                '}';
    }
}
