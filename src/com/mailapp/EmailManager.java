package com.mailapp;

import com.mailapp.controller.services.FetchFoldersService;
import com.mailapp.model.EmailAccount;
import com.mailapp.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {


    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public TreeItem<String> getEmailTreeRoot() {
        return foldersRoot;
    }
    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);

    }
}
