package com.mailapp.controller;

        import com.mailapp.EmailManager;
        import com.mailapp.view.ViewFactory;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;

public class LoginWindowController extends BaseController{

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailAddressField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorText;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        viewFactory.showMainWindow();
        Stage stage = (Stage) errorText.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

}