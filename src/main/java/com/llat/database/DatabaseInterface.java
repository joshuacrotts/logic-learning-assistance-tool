package com.llat.database;

public interface DatabaseInterface {

    UserObject Login(String Username, String Password);

    void Register(String _userName, String _password, String _firstName, String _lastName);

    void UpdateTheme(int id, String Theme);

    void UpdateLanguage(int id, String Language);
}
