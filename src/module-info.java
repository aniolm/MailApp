module MailApp {

    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    opens com.mailapp;
    opens com.mailapp.view;
    opens com.mailapp.controller;
}