package com.llat.database;

import java.util.ArrayList;
import java.util.List;


public class Main {

    private static String password = "1234";

    public static void main(String[] args) {
        GoogleCloudDatabase db = new GoogleCloudDatabase();
        DatabaseAdapter Adapter = new DatabaseAdapter();
        String Status = null;
        int UserID = 0;
        List<String> history = new ArrayList<>();


/*        Status = Adapter.Register("George96", "12345", "George", "C");
        System.out.println(Status);*/
       UserObject User = Adapter.Login("Bob98", "12345");
        if(User == null){
            System.out.println("Wrong UserName or Password");
        }
        else {
            System.out.println(User);
            UserID = User.getUserId();
        }


/*      Adapter.InsertQuery(UserID, "(B -> D)");



        history = Adapter.UpdateHistory(UserID);
        System.out.println(history);*/


    }
}
