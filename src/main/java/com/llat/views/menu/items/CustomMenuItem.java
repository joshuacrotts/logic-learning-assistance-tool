package com.llat.views.menu.items;

import com.llat.models.localstorage.ItemObject;
import javafx.scene.control.MenuItem;

public class CustomMenuItem extends MenuItem {

    private ItemObject content;

    public CustomMenuItem(ItemObject content) {
        this.setText(content.getName());
        this.content = content;
    }

    public ItemObject getContent() {
        return this.content;
    }

    public void setContent(ItemObject content) {
        this.content = content;
    }
}