package com.az3ez.samplemeezan;

import android.util.Base64;
import android.util.Log;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zeeshanhanif-pc on 2/8/2016.
 */
public class Security {

    private static final String TAG = "Security";
    private static Security security;
    private String key;
    private String iv;

    private Security(String key, String iv){
        this.key = key;
        this.iv = iv;
    }

    public static Security getInstance(String key, String iv){
        if(security == null){
            security = new Security(key, iv);
        }
        return security;
    }


    // Working with DotNet 'RijndaelManaged'
    public String decrypt(String ciphertext)throws AppSecurityException{
        byte[] plainTextWithoutBase64 = Base64.decode(ciphertext.getBytes(), Base64.NO_PADDING);
        byte[] keyBytes = key.getBytes();
        byte[] ivBytes = iv.getBytes();
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(ivBytes));
            byte[] plaintextBytes = cipher.doFinal(plainTextWithoutBase64);
            String plaintext = new String(plaintextBytes);
            Log.d(TAG,"Plain = "+plaintext );
            return plaintext;
            //JsonElement jsonElement = new JsonParser().parse(plaintext);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new AppSecurityException(e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new AppSecurityException(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new AppSecurityException(e.getMessage());
        } catch (BadPaddingException e) {
            throw new AppSecurityException(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new AppSecurityException(e.getMessage());
        } catch (InvalidKeyException e) {
            throw new AppSecurityException(e.getMessage());
        }
    }

    // Working with DotNet 'RijndaelManaged'
    public String encrypt(String plainText)throws AppSecurityException{

        byte[] plainTextBytes = plainText.getBytes();
        byte[] keyBytes = key.getBytes(); //Where you get this from is beyond the scope of this post
        byte[] ivBytes = iv.getBytes() ; //Ditto
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(ivBytes));
            byte[] ciphertextBytes = cipher.doFinal(plainTextBytes);
            String ciphertext = new String(ciphertextBytes);
            Log.d(TAG, "Plain = " + ciphertext);
            String ciphertextBase64 = Base64.encodeToString(ciphertextBytes, Base64.DEFAULT);
            Log.d(TAG, "Plain = " + ciphertextBase64);
            //JsonElement jsonElement = new JsonParser().parse(plaintext);
            return ciphertextBase64;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new AppSecurityException(e.getMessage());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new AppSecurityException(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new AppSecurityException(e.getMessage());
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new AppSecurityException(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new AppSecurityException(e.getMessage());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new AppSecurityException(e.getMessage());
        }
    }
}
