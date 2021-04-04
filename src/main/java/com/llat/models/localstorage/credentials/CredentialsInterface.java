package com.llat.models.localstorage.credentials;

import com.llat.models.localstorage.LocalStorage;

public interface CredentialsInterface {

    /**
     *
     */
    void update(LocalStorage _obj, String _jsonFilePath);

    /**
     *
     */
    LocalStorage getData();

}
