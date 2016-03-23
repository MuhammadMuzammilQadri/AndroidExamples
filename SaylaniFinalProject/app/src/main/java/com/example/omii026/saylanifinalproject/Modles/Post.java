package com.example.omii026.saylanifinalproject.Modles;

import java.io.Serializable;

/**
 * Created by Omii026 on 3/13/2016.
 */
public class Post implements Serializable {

    Post(){

    }
    private String UserName,city,state,unitsRequired,countery, postValue, urgency,contactNo,additionalInstruction,volunteer,currentcount,bloodGroup,location,relation,hospital,stillRequired;

//    public Post(String UserName, String postValue,String urgency,String contactNo,String stillRequired,String bloodGroup,String location,String hospital,String relation, String additionalInstruction,String donationRecv){
//        this.UserName = UserName;
//        this.postValue = postValue;
//        this.urgency = urgency;
//        this.contactNo = contactNo;
//        this.stillRequired = stillRequired;
//        this.additionalInstruction = additionalInstruction;
//        this.bloodGroup = bloodGroup;
//        this.location = location;
//        this.hospital = hospital;
//        this.relation = relation;
//    }
    public Post(String bloodGroup, String unitsRequired,String urgency,String countery,String state, String city,String hospital,String relation,String contactNo,String additionalInstruction ){
        this.bloodGroup = bloodGroup;
        this.unitsRequired = unitsRequired;
        this.urgency = urgency;
        this.countery = countery;
        this.state = state;
        this.city = city;
        this.hospital = hospital;
        this.relation = relation;
        this.contactNo = contactNo;
        this.additionalInstruction = additionalInstruction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountery() {
        return countery;
    }

    public void setCountery(String countery) {
        this.countery = countery;
    }

    public String getUnitsRequired() {
        return unitsRequired;
    }

    public void setUnitsRequired(String unitsRequired) {
        this.unitsRequired = unitsRequired;
    }


    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getStillRequired() {
        return stillRequired;
    }

    public void setStillRequired(String stillRequired) {
        this.stillRequired = stillRequired;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPostValue() {
        return postValue;
    }

    public void setPostValue(String postValue) {
        this.postValue = postValue;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAdditionalInstruction() {
        return additionalInstruction;
    }

    public void setAdditionalInstruction(String additionalInstruction) {
        this.additionalInstruction = additionalInstruction;
    }

    public String getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(String volunteer) {
        this.volunteer = volunteer;
    }

    public String getCurrentcount() {
        return currentcount;
    }

    public void setCurrentcount(String currentcount) {
        this.currentcount = currentcount;
    }
}
