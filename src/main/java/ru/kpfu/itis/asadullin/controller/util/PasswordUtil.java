package ru.kpfu.itis.asadullin.controller.util;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String encrypt(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPassword(String inputPassword, String storedPassword) {
        String hashedInputPassword = encrypt(inputPassword);
        return hashedInputPassword.equals(storedPassword);
    }
}
