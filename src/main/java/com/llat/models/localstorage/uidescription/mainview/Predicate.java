package com.llat.models.localstorage.uidescription.mainview;

import com.llat.models.localstorage.LocalStorage;
import com.llat.models.symbols.Existential;
import com.llat.models.symbols.Universal;

public class Predicate extends LocalStorage {
    private Universal universal;
    private Existential existential;

    public Predicate(Universal universal, Existential existential) {
        this.universal = universal;
        this.existential = existential;
    }

    public Universal getUniversal() {
        return this.universal;
    }

    public void setUniversal(Universal universal) {
        this.universal = universal;
    }

    public Existential getExistential() {
        return this.existential;
    }

    public void setExistential(Existential existential) {
        this.existential = existential;
    }

    @Override
    public String toString() {
        return "com.llat.models.localstorage.Predicate{" +
                "universal=" + this.universal +
                ", existential=" + this.existential +
                '}';
    }
}
