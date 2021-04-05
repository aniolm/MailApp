module MailApp {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;

    opens com.mailapp;
    opens com.mailapp.view;
    opens com.mailapp.controller;
}