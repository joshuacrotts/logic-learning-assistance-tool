package com.llat.models.localstorage.uidescription.menubar;

public class MenuBar {
    public File file;
    public Help help;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
                ", help=" + help +
                '}';
    }
}
