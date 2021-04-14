package com.llat.models.localstorage.uidescription.settingsview;

public class SettingsView {
    public Categories categories;
    public String cancel;
    public String save;

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    @Override
    public String toString() {
        return "SettingsView{" +
                "categories=" + categories +
                ", cancel='" + cancel + '\'' +
                ", save='" + save + '\'' +
                '}';
    }
}
