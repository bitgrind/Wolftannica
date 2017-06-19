package com.example.kstedman.mathapplication.models;

import org.parceler.Parcel;

@Parcel
public class WolframResponseModel {
    String title;
    String value;
    String image;
    String pushId;

    public WolframResponseModel() {}

    public WolframResponseModel(String title, String value, String image){
        this.title = title;
        this.value = value;
        this.image = image;
    }

    public String getTitle(){
        return title;
    }

    public String getValue(){
        return value;
    }

    public String getImage() {
        return image;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
