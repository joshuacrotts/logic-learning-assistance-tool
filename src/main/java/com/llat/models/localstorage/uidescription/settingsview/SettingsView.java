package com.llat.models.localstorage.uidescription.settingsview;

public class SettingsView {
    public Categories categories;
    public String cancel;
    public String save;

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public String getCancel() {
        return this.cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getSave() {
        return this.save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    @Override
    public String toString() {
        return "SettingsView{" +
                "categories=" + this.categories +
                ", cancel='" + this.cancel + '\'' +
                ", save='" + this.save + '\'' +
                '}';
    }
}
