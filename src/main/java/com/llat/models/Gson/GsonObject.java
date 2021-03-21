package com.llat.models.Gson;

public class GsonObject<AnyType> {
    AnyType gsonObject;
    public GsonObject(AnyType _obj) {
        this.gsonObject = _obj;
    }
    public AnyType getObject(){ return this.gsonObject;}
}
