package com.example.productscanner.Model;

import static com.example.productscanner.Utill.DataApi.BASE_URL;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URL;

public class ProfileModel implements Serializable {
    @SerializedName("username")
    String username;
    @SerializedName("about")
    String about;
    @SerializedName("image")
    String image;

    public ProfileModel(String username, String about, String image) {
        this.username = username;
        this.about = about;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return BASE_URL + "/qrcode/profile_image/" + image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
