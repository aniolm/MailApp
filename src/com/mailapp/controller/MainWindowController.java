package com.mailapp.controller;

import com.mailapp.EmailManager;
import com.mailapp.model.EmailMessage;
import com.mailapp.model.EmailTreeItem;
import com.mailapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;

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
    private TableColumn<EmailMessage, Integer> sizeCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

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
        sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage,Integer>("size")));
        dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage,Date>("date")));
    }

    private void setUpEmailsTreeView() {
        emailTreeView.setRoot(emailManager.getEmailTreeRoot());
        emailTreeView.setShowRoot(false);
    }
}
