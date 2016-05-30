package com.example.umairomii.firebaseauth.Authentication;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.umairomii.firebaseauth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FramentInteractionlistener listener;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;


    public AuthFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AuthFragment newInstance(String param1, String param2) {
        AuthFragment fragment = new AuthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_auth, container, false);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final EditText email = (EditText) view.findViewById(R.id.signupEmail);
        final EditText signInemail = (EditText) view.findViewById(R.id.signinEmail);
        final EditText pass = (EditText) view.findViewById(R.id.signuppassword);
        final EditText signInpass = (EditText) view.findViewById(R.id.signinpassword);

        Button signUp = (Button) view.findViewById(R.id.signupButton);
        Button signIn = (Button) view.findViewById(R.id.signinButton);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.d("User:","onAuthStateChanged:signed_in:"+firebaseUser.getUid());
                }else {
                    // User is signed out
                    Log.d("User:", "onAuthStateChanged:signed_out");
                }

            }
        };

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(getContext(),"","wait..",false);
                String userEmail = email.getText().toString();
                String userPass = pass.getText().toString();

                if(!userEmail.equals("") && !userPass.equals("")){
                    Log.d("TAG1",userEmail +" "+userPass);
                    mAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();
                            if(!task.isSuccessful()){
                                Snackbar.make(v,"error signUp",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }else {
                                Snackbar.make(v,"success signUp",Snackbar.LENGTH_SHORT).setAction("Action", null).show();
//                                Log.d("token",task.getResult().getUser().getToken(true)+"");
                            }
                        }
                    });
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(getContext(),"","wait..",false);
                String userEmail = signInemail.getText().toString();
                String userPass = signInpass.getText().toString();

                    Log.d("TAG2", signInemail.getText().toString() + " " + signInpass.getText().toString());
                    mAuth.signInWithEmailAndPassword(signInemail.getText().toString(),signInpass.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();
                            if (!task.isSuccessful()) {
                                Snackbar.make(v, "error signIn", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                Log.d("exception ", task.getException().toString() + "");
                            } else {
                                Snackbar.make(v, "success signIn", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        }
                    });
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart ", "onStart called");
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onStop ", "onStop called");
        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    public interface FramentInteractionlistener{
        void signIn();
        void signUp();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
