package com.llat.api.login;

import java.util.HashMap;

public class LoginAdaptor implements LoginInterface {
    private static LoginInterface login = new GoogleLogin();

    public HashMap<String, String> userInfo() {
        return login.userInfo();
    }

    public void RevokeAccess() {
        login.RevokeAccess();
    }
}