package com.mailapp.controller;

import com.mailapp.EmailManager;
import com.mailapp.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController extends BaseController{

    @FXML
    private TreeView<?> emailTreeView;

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<?> emailTableView;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void optionsAction() {

    }

}
