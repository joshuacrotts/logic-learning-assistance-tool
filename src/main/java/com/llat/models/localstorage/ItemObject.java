package com.llat.models.localstorage;

public abstract class ItemObject {

    private String name;
    private String code;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ItemObject{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
