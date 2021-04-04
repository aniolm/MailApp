package com.mailapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL urlLoginWindow = getClass().getResource("view/MainWindow.fxml");
        Parent parent = FXMLLoader.load(urlLoginWindow);

        Scene scene = new Scene(parent, 350, 180);
        stage.setScene(scene);

        stage.show();

        stage.show();

    }
}
