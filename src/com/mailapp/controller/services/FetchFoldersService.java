package com.mailapp.controller.services;

import com.mailapp.model.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Store;

public class FetchFoldersService extends Service<Void> {
    public FetchFoldersService(Store store,EmailTreeItem<String> foldersRoot) {
        this.store = store;
        this.foldersRoot = foldersRoot;
    }

    private Store store;
    private EmailTreeItem<String> foldersRoot;

    @Override
    protected Task createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        }
        ;
    }

    private void fetchFolders() throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list();
        handleFolders(folders, foldersRoot);
    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        for(Folder folder: folders) {
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            foldersRoot.getChildren().add(emailTreeItem);
            foldersRoot.setExpanded(true);
            fetchMessagesInFolder(folder, emailTreeItem);
            if(folder.getType() == Folder.HOLDS_FOLDERS) {
                Folder[] subfolders = folder.list();
                handleFolders(subfolders, emailTreeItem);
            }
        }
    }

    private void fetchMessagesInFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        Service fetchMessagesService = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if(folder.getType() != Folder.HOLDS_FOLDERS){
                            folder.open(Folder.READ_WRITE);
                            int folderSize = folder.getMessageCount();
                            for(int i = folderSize; i > 0; i -- ){
                                emailTreeItem.addEmail(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }
        };
        fetchMessagesService.start();
    }


}
