package com.llat.database;

import java.util.List;

public interface DatabaseInterface {

    UserObject Login(String Username, String Password);

    String Register(String _userName, String _password, String _firstName, String _lastName);

    void UpdateTheme(int id, String Theme);

    void UpdateLanguage(int id, String Language);

    void InsertQuery(int id, String Text);

    List<String> UpdateHistory(int id);


}
