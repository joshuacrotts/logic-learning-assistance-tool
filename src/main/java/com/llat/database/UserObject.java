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
        this.UserId = userId;
        this.UserName = userName;
        this.Pword = pword;
        this.Lname = lname;
        this.Fname = fname;
        this.Theme = theme;
        this.Language = language;
        this.History = history;
    }

    public UserObject(int userId, String userName, String fname, String lname, String pword, String theme, String language) {
        this.UserId = userId;
        this.UserName = userName;
        this.Pword = pword;
        this.Lname = lname;
        this.Fname = fname;
        this.Theme = theme;
        this.Language = language;
    }

    public UserObject(String userName, String fname, String lname, String pword) {
        this.UserName = userName;
        this.Pword = pword;
        this.Fname = fname;
        this.Lname = lname;
    }

    public UserObject(String userName, String pass) {
        this.UserName = userName;
        this.Pword = pass;
    }

    public int getUserId() {
        return this.UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getPword() {
        return this.Pword;
    }

    public void setPword(String pword) {
        this.Pword = pword;
    }

    public String getLname() {
        return this.Lname;
    }

    public void setLname(String lname) {
        this.Lname = lname;
    }

    public String getFname() {
        return this.Fname;
    }

    public void setFname(String fname) {
        this.Fname = fname;
    }

    public String getTheme() {
        return this.Theme;
    }

    public void setTheme(String theme) {
        this.Theme = theme;
    }

    public String getLanguage() {
        return this.Language;
    }

    public void setLanguage(String language) {
        this.Language = language;
    }

    public List<String> getHistory() {
        return this.History;
    }

    public void setHistory(List<String> history) {
        this.History = history;
    }

    @Override
    public String toString() {
        return "UserObject{" +
                "UserId=" + this.UserId +
                ", UserName='" + this.UserName + '\'' +
                ", Pword='" + this.Pword + '\'' +
                ", Lname='" + this.Lname + '\'' +
                ", Fname='" + this.Fname + '\'' +
                ", Theme='" + this.Theme + '\'' +
                ", Language='" + this.Language + '\'' +
                ", History=" + this.History +
                '}';
    }
}
