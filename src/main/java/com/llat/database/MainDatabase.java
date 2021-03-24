package com.llat.database;
import java.sql.*;

public class MainDatabase {
    public static final String CREDENTIALS_STRING = "jdbc:mysql://35.202.75.240:3306/llat";

    static Connection connection = null;

    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(" SELECT * FROM User;");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)
                        +" "+ rs.getString(4)+"  "+rs.getString(5));
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
