package com.mailapp.controller;

import com.mailapp.EmailManager;
import com.mailapp.controller.services.MessageReaderService;
import com.mailapp.model.EmailMessage;
import com.mailapp.view.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController  extends BaseController implements Initializable {

    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    @FXML
    private WebView webView;

    @FXML
    private Label subjectLabel;

    @FXML
    private Label senderLabel;

    @FXML
    private HBox hboxAttachments;

    @FXML
    private Label attachmentsLabel;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        loadAttachments(emailMessage);

        MessageReaderService messageReaderService = new MessageReaderService(webView.getEngine());
        messageReaderService.setEmailMessage(emailMessage);
        messageReaderService.restart();
    }

    private void loadAttachments(EmailMessage emailMessage)  {
        if (emailMessage.isHasAttachments()){
            for(MimeBodyPart mimeBodyPart: emailMessage.getAttachmentList()){
                try {
                    Button button = new AttachmentButton(mimeBodyPart);
                    hboxAttachments.getChildren().add(button);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            attachmentsLabel.setText("");
        }
    }

    private class AttachmentButton extends Button {

        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachmentButton(MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName();

            setOnAction( e -> downloadAttachment());
        }

        private void downloadAttachment(){
            colorBlue();
            Service service= new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
            service.setOnSucceeded(e->{
                colorGreen();
            });
        }

        private void colorBlue(){
            this.setStyle("-fx-background-color: blue");
        }
        private void colorGreen(){
            this.setStyle("-fx-background-color: green");
        }



    }
}

