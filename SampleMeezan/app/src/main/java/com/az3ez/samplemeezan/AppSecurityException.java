package com.az3ez.samplemeezan;

/**
 * Created by zeeshanhanif-pc on 2/8/2016.
 */
public class AppSecurityException extends Exception {

    public AppSecurityException(String detailMessage) {
        super(detailMessage);
    }

    public AppSecurityException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AppSecurityException(Throwable throwable) {
        super(throwable);
    }
}
