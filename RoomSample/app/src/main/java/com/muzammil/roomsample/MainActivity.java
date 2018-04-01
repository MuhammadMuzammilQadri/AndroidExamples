package com.muzammil.roomsample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.muzammil.roomsample.database.AppDatabase;
import com.muzammil.roomsample.databinding.ActivityMainBinding;
import com.muzammil.roomsample.model.Parent;
import com.muzammil.roomsample.model.User;
import com.muzammil.roomsample.repo.MainActivityRepo;

public class MainActivity extends AppCompatActivity {
    
    public static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        
        // Initialize repo once
        MainActivityRepo.init(AppDatabase.getAppDatabase(this), new Handler());
        
        // Intially load data from DB upon start
        loadDataFromDb();
        
        // Load user data from database using Room in a separate thread
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preRequestisAreMet()) {
                    String userName = binding.nameEt.getText().toString();
                    String userAge = binding.ageEt.getText().toString();
                    String parentName = binding.parentnameEt.getText().toString();
                    
                    binding.nameEt.setText("");
                    binding.ageEt.setText("");
                    binding.parentnameEt.setText("");
                    
                    
                    User user = new User(userName, Integer.parseInt(userAge));
                    user.setParent(new Parent(parentName));
                    
                    // Save some User data in database
                    MainActivityRepo.getInstance().saveUserData(user, new MainActivityRepo.SaveUserToDbCallback() {
                        @Override
                        public void onUserSaved(User user) {
                            loadDataFromDb();
                        }
                        
                        @Override
                        public void onError() {
                            Toast.makeText(MainActivity.this, "Some thing bad happened. Try again later", Toast.LENGTH_SHORT).show();
                        }
                    });
                    
                }
            }
        });
    }
    
    private boolean preRequestisAreMet() {
        String userName = binding.nameEt.getText().toString();
        String userAge = binding.ageEt.getText().toString();
        String parentName = binding.parentnameEt.getText().toString();
        
        if (TextUtils.isEmpty(userName)) {
            return false;
        }
        
        if (TextUtils.isEmpty(userAge)) {
            return false;
        }
        
        if (TextUtils.isEmpty(parentName)) {
            return false;
        }
        
        return true;
    }
    
    private void loadDataFromDb() {
        MainActivityRepo.getInstance().loadData(new MainActivityRepo.LoadUserFromDbCallback() {
            
            @Override
            public void onDataLoaded(String string) {
                binding.tv.setText(string);
            }
            
            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Some thing bad happened. Try again later", Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}
