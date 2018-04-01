package com.muzammil.roomsample.repo;

import android.os.Handler;

import com.muzammil.roomsample.database.AppDatabase;
import com.muzammil.roomsample.database.dao.UserDao;
import com.muzammil.roomsample.model.User;

import java.util.List;

/**
 * Created by Muzammil on 01-Apr-18.
 */

public class MainActivityRepo {
    
    public static final String TAG = MainActivityRepo.class.getSimpleName();
    private static MainActivityRepo instance;
    private AppDatabase db;
    private Handler handler;
    
    public MainActivityRepo(AppDatabase db, Handler handler) {
        this.db = db;
        this.handler = handler;
        
    }
    
    public static MainActivityRepo getInstance() {
        if (instance == null) {
            throw new RuntimeException("MainActivityRepo has not been initialized. Please call MainActivityRepo#init() before performing any actions on this class");
        }
        return instance;
    }
    
    public static void init(final AppDatabase db, Handler handler) {
        instance = new MainActivityRepo(db, handler);
    }
    
    private User addUser(User user) {
        db.userDao().insertAll(user);
        return user;
    }
    
    public void loadData(final LoadUserFromDbCallback loadUserFromDb) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserDao userDao = db.userDao();
                    List<User> all = userDao.getAll();
                    String userToString = "";
                    for (User u :
                            all) {
                        userToString += u.toString();
                    }
                    
                    final String finalUserToString = userToString;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loadUserFromDb != null) {
                                loadUserFromDb.onDataLoaded(finalUserToString);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loadUserFromDb != null) {
                                loadUserFromDb.onError();
                            }
                            
                        }
                    });
                }
                
            }
        }).start();
    }
    
    public void saveUserData(final User user, final SaveUserToDbCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    addUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError();
                            }
                            
                        }
                    });
                    return;
                }
                
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onUserSaved(user);
                        }
                        
                    }
                });
            }
        }).start();
        
    }
    
    public interface LoadUserFromDbCallback {
        void onDataLoaded(String string);
        
        void onError();
    }
    
    public interface SaveUserToDbCallback {
        void onUserSaved(User user);
        
        void onError();
    }
    
}
