package com.llat.api.login;

import java.util.HashMap;

public interface LoginInterface {

    HashMap<String, String> userInfo();

    void RevokeAccess();
}