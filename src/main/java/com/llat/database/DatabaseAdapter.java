package com.llat.database;

public class DatabaseAdapter implements DatabaseInterface {

    DatabaseInterface db = new GoogleCloudDatabase();

    @Override
    public UserObject Login(String Username, String Password) {
        return db.Login(Username, Password);
    }

    @Override
    public void Register(String _userName, String _password, String _firstName, String _lastName) {
        db.Register(_userName, _password, _firstName, _lastName);

    }

    @Override
    public void UpdateTheme(int id, String Theme) {
        db.UpdateTheme(id, Theme);

    }

    @Override
    public void UpdateLanguage(int id, String Language) {
        db.UpdateLanguage(id, Language);

    }
}
