package com.llat.database;

import java.util.List;

public class DatabaseAdapter implements DatabaseInterface {

    DatabaseInterface db = new GoogleCloudDatabase();

    @Override
    public UserObject Login(String Username, String Password) {

        return db.Login(Username, Password);
    }

    @Override
    public String Register(String _userName, String _password, String _firstName, String _lastName) {
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
