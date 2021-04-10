package com.mailapp.view;

import com.mailapp.EmailManager;
import com.mailapp.controller.BaseController;
import com.mailapp.controller.LoginWindowController;
import com.mailapp.controller.MainWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewFactory {
    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }
    private boolean mainViewInitialized = false;

    public boolean isMainViewInitialized(){
        return mainViewInitialized;
    }

    public void showLoginWindow(){
        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        initializeStage(controller);

    }
    public void showMainWindow(){
        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controller);
        mainViewInitialized = true;
    }
    private void initializeStage(BaseController baseController){
        URL urlWindow = getClass().getResource(baseController.getFxmlName());
        FXMLLoader fxmlLoader = new FXMLLoader(urlWindow);
        fxmlLoader.setController(baseController);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void closeStage(Stage stageToClose){
        stageToClose.close();
    }
}
