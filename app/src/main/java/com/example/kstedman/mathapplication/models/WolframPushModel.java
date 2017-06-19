package com.example.kstedman.mathapplication.models;

import java.util.ArrayList;

import org.parceler.Parcel;

@Parcel
public class WolframPushModel {
    String pushId;
    ArrayList<WolframResponseModel> responseArray = new ArrayList<>();

    public WolframPushModel(){}

    public WolframPushModel(ArrayList<WolframResponseModel> responseArray, String uid) {
        this.pushId = uid;
        this.responseArray = responseArray;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public ArrayList<WolframResponseModel> getResponseArray() {
        return responseArray;
    }

    public void setResponseArray(ArrayList<WolframResponseModel> responseArray) {
        this.responseArray = responseArray;
    }
}
