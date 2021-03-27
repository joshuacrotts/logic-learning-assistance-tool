package com.llat.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainDatabase {
    public static final String CREDENTIALS_STRING = "jdbc:mysql://35.202.75.240:3306/llat";

    static Connection connection = null;

    public static void main(String[] args) {
        MainDatabase db = new MainDatabase();
//        UserObject x = db.getUser(2);
//        System.out.println(x);
        db.UpdateLanguage(6, "Spanish");

    }


    public UserObject getUser(int UserId) {
        UserObject user = null;
        List<String> history = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT T.UserID,\n" +
                    "       UserName,\n" +
                    "       Password,\n" +
                    "       FName,\n" +
                    "       LName,\n" +
                    "       Theme,\n" +
                    "       Language\n" +
                    "From User\n" +
                    "         INNER Join Theme T on User.UserID = T.UserID and T.UserID = " + UserId + "\n" +
                    "         INNER JOIN Language L on User.UserID = L.UserID and User.UserID =" + UserId + ";");

            while (rs.next()) {
                user = new UserObject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
            ResultSet rs2 = stmt.executeQuery("SELECT TextInput from Query_History where UserID = " + UserId + ";");
            while (rs2.next()) {
                history.add(rs2.getString(1));
            }
            user.setHistory(history);
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void createUser(String _userName, String _password, String _firstName, String _lastName) {
        int id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            Statement stmt = connection.createStatement();
            String execute = "INSERT INTO User (UserName, Password, LName, FName) VALUES (\'" + _userName + "\', \'" + _password + "\', \'" + _lastName + "\', \'" + _firstName + "\');";
            stmt.executeUpdate(execute);
            ResultSet rs2 = stmt.executeQuery("select UserID from User where UserName = \'" + _userName + "\';");
            while (rs2.next()) {
                id = rs2.getInt(1);
            }
            stmt.executeUpdate("insert into Language (UserID, Language)  values (" + id + ", 'En');");

            stmt.executeUpdate("insert into Theme (UserID, Theme)  values (" + id + ", 'Default');");
            connection.close();

        } catch (ClassNotFoundException | SQLException  | NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void UpdateTheme(int id, String Theme) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            Statement stmt = connection.createStatement();
            String execute = "UPDATE Theme SET Theme = \'" + Theme + "\' WHERE UserID = " + id + ";";
            stmt.executeUpdate(execute);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


        public void UpdateLanguage(int id, String Language){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
                Statement stmt = connection.createStatement();
                String execute = "UPDATE Language SET Language = \'" + Language + "\' WHERE UserID = " + id + ";";
                stmt.executeUpdate(execute);


            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

    }
