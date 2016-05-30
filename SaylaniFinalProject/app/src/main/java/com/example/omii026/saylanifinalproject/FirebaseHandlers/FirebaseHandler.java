package com.example.omii026.saylanifinalproject.FirebaseHandlers;

import com.firebase.client.Firebase;

/**
 * Created by Omii026 on 3/12/2016.
 */
public class FirebaseHandler {

    private static FirebaseHandler ourRef;
    private static Firebase myRef;
    private Firebase post;

    public static FirebaseHandler getInstance(){
        if(ourRef == null){
            ourRef = new FirebaseHandler();
        }
        return ourRef;
    }

    public FirebaseHandler(){
//        mFirebaseRef = new Firebase(Configuration.FIREBASE_URL);
        myRef = new Firebase("https://finalappss.firebaseio.com/");
    }

    public Firebase getPostRef(){
        return post = myRef.child("posts");
    }

}
