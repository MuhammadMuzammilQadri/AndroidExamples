package com.example.omii026.saylanifinalproject.Modles;

/**
 * Created by Omii026 on 3/12/2016.
 */
public class User {

    private String userName;
    private String userPass;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String bloodGroup;
    private String userId;

    public User(String firstname,String lastName,String userEmail, String bloodGroup ){
        this.userEmail = userEmail;
        this.lastName = lastName;
        this.firstName = firstname;
        this.bloodGroup = bloodGroup;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
