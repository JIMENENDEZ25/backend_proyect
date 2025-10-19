/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author jimem
 */
public class Encryption {
    public static String hashMD5(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] message = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1, message);

        String hash = bigInt.toString(16);
        while (hash.length() < 32) {
            hash = "0" + hash;
        }
        return hash;
    }
}
