package com.llat.database;

import com.llat.controller.Controller;

import java.util.List;

public class DatabaseAdapter implements DatabaseInterface {
    public static final int REGISTERED_SUCCESSFULLY = 0;
    public static final int REGISTERED_EMPTY_INPUT = 1;
    public static final int REGISTERED_DUP_USER = 2;
    public static final int DATABASE_ERROR = 3;
    DatabaseInterpeter di = new DatabaseInterpeter(this);

    Controller controller;
    private DatabaseInterface db = new AWSDatabase();


    @Override
    public UserObject Login(String Username, String Password) {
        return db.Login(Username, Password);
    }

    @Override
    public int Register(String _userName, String _password, String _firstName, String _lastName) {
        return db.Register(_userName, _password, _firstName, _lastName);
    }

    @Override
    public void UpdateTheme(int id, String Theme) {
        db.UpdateTheme(id, Theme);
    }

    @Override
    public void UpdateLanguage(int id, String Language) {
        db.UpdateLanguage(id, Language);
    }

    @Override
    public void InsertQuery(int id, String Text) {
        db.InsertQuery(id, Text);
    }

    @Override
    public List<String> UpdateHistory(int id) {
        return db.UpdateHistory(id);
    }
}
