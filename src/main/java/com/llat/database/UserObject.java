package com.llat.database;

import java.util.List;

public class UserObject {

    private int UserId;
    private String UserName;
    private String Pword;
    private String Lname;
    private String Fname;
    private String Theme;
    private String Language;
    private List<String> History;

    public UserObject(int userId, String userName, String pword, String lname, String fname, String theme, String language, List<String> history) {
        UserId = userId;
        UserName = userName;
        Pword = pword;
        Lname = lname;
        Fname = fname;
        Theme = theme;
        Language = language;
        History = history;
    }

    public UserObject(int userId, String userName, String pword, String lname, String fname, String theme, String language) {
        UserId = userId;
        UserName = userName;
        Pword = pword;
        Lname = lname;
        Fname = fname;
        Theme = theme;
        Language = language;
    }

    public UserObject(String userName, String pword, String fname, String lname){
        UserName = userName;
        UserName = userName;
        Pword = pword;
        Fname = fname;
        Lname = lname;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPword() {
        return Pword;
    }

    public void setPword(String pword) {
        Pword = pword;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getTheme() {
        return Theme;
    }

    public void setTheme(String theme) {
        Theme = theme;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public List<String> getHistory() {
        return History;
    }

    public void setHistory(List<String> history) {
        History = history;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "UserId=" + UserId +
                ", UserName='" + UserName + '\'' +
                ", Pword='" + Pword + '\'' +
                ", Lname='" + Lname + '\'' +
                ", Fname='" + Fname + '\'' +
                ", Theme='" + Theme + '\'' +
                ", Language='" + Language + '\'' +
                ", History=" + History +
                '}';
    }
}
