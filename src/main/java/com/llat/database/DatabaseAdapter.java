package com.llat.database;

import com.llat.controller.Controller;

import java.util.List;

public class DatabaseAdapter implements DatabaseInterface {

    public static final int REGISTERED_SUCCESSFULLY = 0;
    public static final int REGISTERED_EMPTY_INPUT = 1;
    public static final int REGISTERED_DUP_USER = 2;
    public static final int DATABASE_ERROR = 3;
    private final DatabaseInterface db = new AWSDatabase();
    private Controller controller;
    private UserObject user;

    public Controller Controller() {
        return this.controller;
    }

    @Override
    public UserObject Login(String Username, String Password) {
        return this.db.Login(Username, Password);
    }

    @Override
    public int Register(String _userName, String _password, String _firstName, String _lastName) {
        return this.db.Register(_userName, _password, _firstName, _lastName);
    }

    @Override
    public void UpdateTheme(int id, String Theme) {
        this.db.UpdateTheme(id, Theme);
    }

    @Override
    public void UpdateLanguage(int id, String Language) {
        this.db.UpdateLanguage(id, Language);
    }

    @Override
    public void InsertQuery(int id, String Text) {
        this.db.InsertQuery(id, Text);
    }

    @Override
    public List<String> UpdateHistory(int id) {
        return this.db.UpdateHistory(id);
    }
}
