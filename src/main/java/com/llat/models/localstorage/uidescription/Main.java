package com.llat.models.localstorage.uidescription;

public class Main {
    public static void main(String[] args) {
        UIObjectAdaptor uioa = new UIObjectAdaptor();
        UIObject uio = (UIObject) uioa.getData();

        System.out.println(uio);

    }
}
