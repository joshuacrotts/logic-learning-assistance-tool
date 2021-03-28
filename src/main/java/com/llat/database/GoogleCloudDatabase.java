package com.llat.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoogleCloudDatabase implements DatabaseInterface {
    public static final String CREDENTIALS_STRING = "jdbc:mysql://35.202.75.240:3306/llat";

    static Connection connection = null;

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

    public void Register(String _userName, String _password, String _firstName, String _lastName) {
        int id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO User (UserName, Password, LName, FName) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, _userName);
            statement.setString(2, _password);
            statement.setString(3, _lastName);
            statement.setString(4, _firstName);
            statement.executeUpdate();


            String sql2 = "SELECT UserID FROM User WHERE UserName = ?";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setString(1, _userName);
            ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()) {
                id = rs2.getInt(1);
            }

            String Def_language = "INSERT INTO Language (UserID, Language)  VALUES ( ?, 'En')";
            PreparedStatement statement3 = connection.prepareStatement(Def_language);
            statement3.setInt(1, id);
            statement3.executeUpdate();

            String Def_Theme = "INSERT INTO Theme (UserID, Theme)  VALUES ( ?, 'Default')";
            PreparedStatement statement4 = connection.prepareStatement(Def_Theme);
            statement4.setInt(1, id);
            statement4.executeUpdate();

            System.out.println("Account Succesfully Created!");
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            /*System.out.println("UserName Already Taken. Please Try New Username.");*/
        } catch (NullPointerException e) {

        }
    }


    public void UpdateTheme(int id, String Theme) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            String sql = "UPDATE Theme SET Theme = ? WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Theme);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void UpdateLanguage(int id, String Language) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            String sql = "UPDATE Language SET Language = ?  WHERE UserID = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Language);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public UserObject Login(String Username, String Password) {
        UserObject user = null;
        List<String> history = new ArrayList<>();
        int id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            String sql = "SELECT T.UserID, UserName,Password,FName,Lname,Theme,Language FROM User INNER Join Theme T on User.UserID = T.UserID\n" +
                    "INNER JOIN Language L on User.UserID = L.UserID WHERE UserName = ? and Password = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Username);
            statement.setString(2, Password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new UserObject(rs.getInt("UserID"), rs.getString("UserName"), rs.getString("Password"), rs.getString("Lname"), rs.getString("Fname"), rs.getString("Theme"), rs.getString("Language"));

            }

            String sql2 = "SELECT UserID FROM User WHERE UserName = ?";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setString(1, Username);
            ResultSet rs2 = statement2.executeQuery();

            while (rs2.next()) {
                id = rs2.getInt(1);
            }


            String sql3 = "Select TextInput from Query_History where UserID = ? ORDER BY QueryID desc limit 10;";
            PreparedStatement HistoryStatement = connection.prepareStatement(sql3);
            HistoryStatement.setInt(1, id);
            ResultSet rs3 = HistoryStatement.executeQuery();
            while (rs3.next()) {
                history.add(rs3.getString(1));
            }
            user.setHistory(history);

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
