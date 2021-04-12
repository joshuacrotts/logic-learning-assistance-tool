package com.llat.models.localstorage.credentials;

import com.llat.models.gson.GsonIO;
import com.llat.models.localstorage.LocalStorage;

public class CredentialsAdaptor implements CredentialsInterface {

    /**
     *
     */
    private static final String CREDENTIAL = "credentials.json";

    /**
     *
     */
    private final CredentialsInterface obj = new GsonIO(CREDENTIAL, UserCredentialsObject.class);

    @Override
    public void update(LocalStorage _obj, String _jsonFilePath) {
        this.obj.update(_obj, _jsonFilePath);
    }

    public void update(LocalStorage _obj) {
        this.obj.update(_obj, CREDENTIAL);
    }

    @Override
    public LocalStorage getData() {
        return this.obj.getData();
    }
}
