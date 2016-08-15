package com.example.muhammadmuzammil.callblocker;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.util.ArrayList;

/**
 * Created by Muhammad Muzammil on 8/12/2016.
 */

public class BlockNumbersHandler {

//    ArrayList<String> blockedNumbersList = new ArrayList<>();
    private String blockedNumber = "";

    private static BlockNumbersHandler singleton;
    private PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public static BlockNumbersHandler getInstance(){
        if (singleton == null)
            singleton = new BlockNumbersHandler();
        return singleton;
    }

    private boolean isXNumberBlocked(String x){
        return blockedNumber.equals(x);
    }

    public String getBlockedNumber() {
        return blockedNumber;
    }

    public void setBlockedNumber(String blockedNumber) {
        this.blockedNumber = blockedNumber;
    }
}
