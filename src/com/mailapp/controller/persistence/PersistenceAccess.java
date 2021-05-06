package com.mailapp.controller.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenceAccess {
    private String VALID_ACCOUNTS_lOCATION = System.getProperty("user.home") + File.separator + "validAccounts.ser";

    public List<ValidAccount> loadFromPersistence(){
        List<ValidAccount> resultList = new ArrayList<ValidAccount>();
        try {
            FileInputStream fileInputStream = new FileInputStream(VALID_ACCOUNTS_lOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<ValidAccount> persistedList = (List<ValidAccount>) objectInputStream.readObject();
            resultList.addAll(persistedList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
    public void saveToPersistence(List<ValidAccount> validAccounts){
        try {
            File file = new File(VALID_ACCOUNTS_lOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(validAccounts);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Valid account saved in:" + VALID_ACCOUNTS_lOCATION);
    }
}
