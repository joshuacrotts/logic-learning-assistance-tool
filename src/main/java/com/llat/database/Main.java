package com.llat.database;

import java.util.ArrayList;
import java.util.List;


public class Main {

    private static final String password = "1234";

    public static void main(String[] args) {
        AWSDatabase db = new AWSDatabase();
        DatabaseAdapter Adapter = new DatabaseAdapter();
        String Status = null;
        int UserID = 0;
        List<String> history = new ArrayList<>();

        UserObject User = Adapter.Login("Bob98", "12345");
        if (User == null) {
            System.out.println("Wrong UserName or Password");
        } else {
            System.out.println(User);
            UserID = User.getUserId();
        }
    }
}
