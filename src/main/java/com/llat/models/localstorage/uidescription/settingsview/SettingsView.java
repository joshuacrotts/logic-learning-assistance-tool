package com.llat.models.localstorage.uidescription.settingsview;

public class SettingsView {
    public Categories categories;
    public Confirmation confirmation;
    public String cancel;
    public String save;

    public SettingsView(Categories categories, Confirmation confirmation, String cancel, String save) {
        this.categories = categories;
        this.confirmation = confirmation;
        this.cancel = cancel;
        this.save = save;
    }

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Confirmation getConfirmation() {
        return this.confirmation;
    }

    public void setConfirmation(Confirmation confirmation) {
        this.confirmation = confirmation;
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
                ", confirmation=" + this.confirmation +
                ", cancel='" + this.cancel + '\'' +
                ", save='" + this.save + '\'' +
                '}';
    }
}
