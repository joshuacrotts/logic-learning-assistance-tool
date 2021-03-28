package com.llat.database;

public class Main {

    public static void main(String[] args) {
        GoogleCloudDatabase db = new GoogleCloudDatabase();
/*        db.UpdateLanguage(6, "French");
       UserObject x = db.getUser(6);
        System.out.println(x);*/
        /*db.createUser( "John2341","12345","John","Smith");*/
        UserObject x = db.Login("Hdog","12345");
        System.out.println(x);


    }
}
