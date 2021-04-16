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
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Confirmation getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Confirmation confirmation) {
        this.confirmation = confirmation;
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

}
