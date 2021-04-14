package com.mailapp.controller;

import com.mailapp.EmailManager;
import com.mailapp.controller.services.MessageReaderService;
import com.mailapp.model.EmailMessage;
import com.mailapp.model.EmailTreeItem;
import com.mailapp.model.SizeInteger;
import com.mailapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TreeView<String> emailTreeView;

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<EmailMessage> emailTableView;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    private MessageReaderService messageReaderService;

    @FXML
    void optionsAction() {

    }
    @FXML
    void addAccountAction() {
        viewFactory.showLoginWindow();
    }

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUpEmailsTreeView();
        setUpEmailListView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageReaderService();
        setUpMessageSelection();
    }

    private void setUpMessageSelection() {
        emailTableView.setOnMouseClicked(e->{
          EmailMessage emailMessage = emailTableView.getSelectionModel().getSelectedItem();
            if (emailMessage != null){
                messageReaderService.setEmailMessage(emailMessage);
                messageReaderService.restart();
            }
        });
    }

    private void setUpMessageReaderService() {
        messageReaderService = new MessageReaderService(emailWebView.getEngine());
    }

    private void setUpBoldRows() {
        emailTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item, empty);
                        if(item != null){
                            if (item.isRead()){
                                setStyle("");
                            }else{
                                setStyle("-fx-font-weight: bold");
                            }

                        }
                    }
                };
            }
        });
    }

    private void setUpFolderSelection() {
        emailTreeView.setOnMouseClicked(e->{
            EmailTreeItem<String> selectedFolder = (EmailTreeItem<String>)emailTreeView.getSelectionModel().getSelectedItem();
            if (selectedFolder != null){
                emailTableView.setItems(selectedFolder.getEmailMessages());
            }
        });
    }

    private void setUpEmailListView() {
        senderCol.setCellValueFactory((new PropertyValueFactory<EmailMessage,String>("sender")));
        subjectCol.setCellValueFactory((new PropertyValueFactory<EmailMessage,String>("subject")));
        recipientCol.setCellValueFactory((new PropertyValueFactory<EmailMessage,String>("recipient")));
        sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage,SizeInteger>("size")));
        dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage,Date>("date")));
    }

    private void setUpEmailsTreeView() {
        emailTreeView.setRoot(emailManager.getEmailTreeRoot());
        emailTreeView.setShowRoot(false);
    }
}
