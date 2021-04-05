package com.mailapp.controller;

        import com.mailapp.EmailManager;
        import com.mailapp.controller.services.LoginService;
        import com.mailapp.model.EmailAccount;
        import com.mailapp.view.ViewFactory;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;

        import javax.mail.NoSuchProviderException;

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
    void loginButtonAction() throws NoSuchProviderException {
        if(fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount,  emailManager);
            EmailLoginResult emailLoginResult = loginService.login();

            switch (emailLoginResult) {
                case SUCCESS:
                    System.out.println("login successful" + emailAccount);
                    viewFactory.showMainWindow();
                    Stage stage = (Stage) errorText.getScene().getWindow();
                    viewFactory.closeStage(stage);
            }
        }

    }

    private boolean fieldsAreValid() {
        return true;
    }




}