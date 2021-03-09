package com.llat.main;

import com.llat.api.login.GoogleLogin;
import com.llat.models.utilities.SystemEnvironment;

import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String CLIENT_ID = SystemEnvironment.getSystemEnvironmentVariable("CLIENT_ID");
        String CLIENT_SECRET = SystemEnvironment.getSystemEnvironmentVariable("CLIENT_SECRET");
        System.out.println(CLIENT_ID);
        System.out.println(CLIENT_SECRET);
//        GoogleLogin gl = new GoogleLogin();
//
//        System.out.println(gl.userInfo());
//        gl.RevokeAccess();

    }
}
