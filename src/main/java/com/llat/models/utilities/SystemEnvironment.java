package com.llat.models.utilities;


public class SystemEnvironment {
    public static String getSystemEnvironmentVariable(String variableName){
        return System.getenv().get(variableName);

    }
}
