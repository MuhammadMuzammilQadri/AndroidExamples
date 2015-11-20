package com.example.faiz.sqlliteexample.database;

/**
 * Created by faiz on 20/11/15.
 */
public final class ContactDbSchema {
    public static final class ContactTable {
        public static final String NAME = "contacts";

        public static final class Cols {
            public static final String ID = "id",
                    NAME = "name",
                    PHONE_NUM = "phonenumber";
        }
    }
}
