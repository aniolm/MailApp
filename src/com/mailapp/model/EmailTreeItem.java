package com.mailapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem {

    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadMessagesCount;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
        this.unreadMessagesCount = 0;
    }

    public ObservableList<EmailMessage> getEmailMessages(){
        return emailMessages;
    }

    public void addEmail(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
            EmailMessage emailMessage = new EmailMessage(
                    message.getSubject(),
                    message.getFrom()[0].toString(),
                    message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                    message.getSize(),
                    message.getSentDate(),
                    messageIsRead,
                    message
            );
            emailMessages.add(emailMessage);
            if(!messageIsRead){
                incrementUnreadMessageCount();
            }

    }
    public void incrementUnreadMessageCount(){
        unreadMessagesCount ++;
        updateName();

    }

    private void updateName(){
        if(unreadMessagesCount > 0) {
            this.setValue((java.lang.String)(name + "(" + unreadMessagesCount + ")"));
        }else {
            this.setValue(name);
        }
    }
}
