module LLAT {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
	requires org.antlr.antlr4.runtime;
    requires com.google.gson;
    requires google.oauth.client;
    requires google.oauth.client.java6;
    requires google.oauth.client.jetty;
    requires google.api.client;
    requires google.http.client;
    requires google.http.client.jackson2;
    requires google.api.services.oauth2.v2.rev131;
    requires httpcore;
    requires httpclient;


    opens com.llat.models.settings; //this is a fucking bitch!
    opens com.llat.controller to javafx.fxml;
    exports com.llat.main;
}