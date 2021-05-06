package com.mailapp.controller.persistence;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryptor {

    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public String encrypt(String text) {
        try {
            return encoder.encodeToString(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }
    }

    public String decrypt(String text) {
        try {
            return new String(decoder.decode(text.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }
    }

}
