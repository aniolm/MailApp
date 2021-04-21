package com.mailapp.controller;

import com.mailapp.EmailManager;
import com.mailapp.controller.services.EmailSenderService;
import com.mailapp.model.EmailAccount;
import com.mailapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmailComposeController extends BaseController  implements Initializable {
    @FXML
    private TextField recipientTxtField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorTextField;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    void sendEmailAction() {
        EmailSenderService emailSenderService = new EmailSenderService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTxtField.getText(),
                htmlEditor.getHtmlText()
        );
        emailSenderService.start();
        emailSenderService.setOnSucceeded(event -> {
            EmailSendingResult emailSendingResult = emailSenderService.getValue();

            switch (emailSendingResult) {
                case SUCCESS:
                    System.out.println("E-mail sent");

                    Stage stage = (Stage) errorTextField.getScene().getWindow();
                    viewFactory.closeStage(stage);
                    break;
                case ACCESS_DENIED:
                    errorTextField.setText("Problem with authentication");
                    break;
                case NETWORK_ERROR:
                    errorTextField.setText("Network problem");
                    break;
                case UNEXPECTED_ERROR:
                    errorTextField.setText("Unexpected error");
                    break;
            }
             });
    }

    public EmailComposeController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
    }
}
