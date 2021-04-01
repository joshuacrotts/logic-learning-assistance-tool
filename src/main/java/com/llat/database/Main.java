package com.llat.database;
import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.chart.PieChart;


public class Main {

    private static String password = "1234";

    public static void main(String[] args) {
        GoogleCloudDatabase db = new GoogleCloudDatabase();
        DatabaseAdapter Adapter = new DatabaseAdapter();
        int UserID = 0;


        /*Adapter.Register( "Kevin43","12345","Kevin","B");*/
        UserObject User = Adapter.Login("George43", "54321");
        System.out.println(User);
        if(User == null){
            System.out.println("Wrong UserName or Password");
        }
        else {
            UserID = User.getUserId();
        }


/*        Adapter.UpdateQuery(UserID, "Test1");
        Adapter.UpdateQuery(UserID, "Test2");
        Adapter.UpdateQuery(UserID, "Test3");
        Adapter.UpdateQuery(UserID, "Test4");
        Adapter.UpdateQuery(UserID, "Test5");
        Adapter.UpdateQuery(UserID, "Test6");
        Adapter.UpdateQuery(UserID, "Test7");
        Adapter.UpdateQuery(UserID, "Test8");
        Adapter.UpdateQuery(UserID, "Test9");
        Adapter.UpdateQuery(UserID, "Test10");
        Adapter.UpdateQuery(UserID, "Test11");
        Adapter.UpdateQuery(UserID, "Test12");
        Adapter.UpdateQuery(UserID, "Test13");
        Adapter.UpdateQuery(UserID, "Test14");
        Adapter.UpdateQuery(UserID, "Test15");*/








    }
}
