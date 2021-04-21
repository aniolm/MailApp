package com.mailapp.controller.services;

import com.mailapp.EmailManager;
import com.mailapp.controller.EmailLoginResult;
import com.mailapp.model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;


public class LoginService extends Service<EmailLoginResult> {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    private EmailLoginResult login() throws NoSuchProviderException {
       Authenticator authenticator = new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(emailAccount.getAddress(), emailAccount.getPassword());
            }
        };

        try {
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            emailAccount.setSession(session);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getAddress(), emailAccount.getPassword());
            emailAccount.setStore(store);
            emailManager.addEmailAccount(emailAccount);

        } catch(NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginResult.NETWORK_ERROR;
        } catch (AuthenticationFailedException e){
            e.printStackTrace();
            return EmailLoginResult.ACCESS_DENIED;
        } catch (MessagingException e) {
            e.printStackTrace();
            return EmailLoginResult.UNEXPECTED_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return EmailLoginResult.UNEXPECTED_ERROR;
        }
        return EmailLoginResult.SUCCESS;
    }

    @Override
    protected Task<EmailLoginResult> createTask() {
        return new Task<EmailLoginResult> () {

            @Override
            protected EmailLoginResult call() throws Exception {
                return login();
            }
        };

    }
}
