package com.llat.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class GoogleCloudDatabase implements DatabaseInterface {
    public static final String CREDENTIALS_STRING = "jdbc:mysql://35.202.75.240:3306/llat";

    static Connection connection = null;

    //Method that creates a user Account and stores in database and sets default values to theme and language.
    public void Register(String _userName, String _password, String _firstName, String _lastName) {
        int id = 0;
        String bcryptHashString = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");

            bcryptHashString = BCrypt.withDefaults().hashToString(12, _password.toCharArray());

            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO User (UserName, Password, LName, FName) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, _userName);
            statement.setString(2, bcryptHashString);
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

    // Method that Updates Theme and stores in database for user.
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


    //Method that Updates Language and stores in database for user.
    public void UpdateLanguage(int id, String Language) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            String sql = "UPDATE Language SET Language = ?  WHERE UserID = ? ;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Language);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Method that validates Login credidentials, if Correct, will return User data. If Incorrect, will return null.
    public UserObject Login(String Username, String Password) {
        UserObject user = null;
        String bcryptHashString = null;
        List<String> history = new ArrayList<>();
        int id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");

            String PassSql = "SELECT Password FROM User where UserName = ?";
            PreparedStatement VerifyPass = connection.prepareStatement(PassSql);
            VerifyPass.setString(1,Username);
            ResultSet rs4 = VerifyPass.executeQuery();
            while (rs4.next()) {
                bcryptHashString = rs4.getString(1);
            }

            BCrypt.Result result = BCrypt.verifyer().verify(Password.toCharArray(), bcryptHashString);

            if(!result.verified){
                return null;
            }



            String sql = "SELECT T.UserID, UserName,Password,FName,Lname,Theme,Language FROM User INNER Join Theme T on User.UserID = T.UserID\n" +
                    "INNER JOIN Language L on User.UserID = L.UserID WHERE UserName = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Username);

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
            //user.setHistory(history);

            if(!history.isEmpty()){
                user.setHistory(history);
            }


            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // updates and inserts user text history and stores in data, up to 10 rows per user.
    public void UpdateQuery(int id, String Text){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CREDENTIALS_STRING, "root", "12345");
            String sql = "INSERT INTO Query_History(USERID, TEXTINPUT) VALUES(?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, Text);
            statement.executeUpdate();

            String sql2 = "DELETE FROM Query_History\n" +
                    "WHERE QueryID NOT IN (\n" +
                    "    SELECT QueryID\n" +
                    "    FROM(\n" +
                    "        SELECT *\n" +
                    "        FROM Query_History q1\n" +
                    "        WHERE (\n" +
                    "            SELECT COUNT(*)\n" +
                    "            FROM Query_History q2\n" +
                    "            WHERE q1.UserID = q2.UserID\n" +
                    "                AND q1.QueryID <= q2.QueryID\n" +
                    "                  ) <= 10\n" +
                    "            ) foo\n" +
                    "    )";
            PreparedStatement s2 = connection.prepareStatement(sql2);
            s2.executeUpdate();


            connection.close();


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

}
