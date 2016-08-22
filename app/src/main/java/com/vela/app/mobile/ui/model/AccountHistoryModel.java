package com.vela.app.mobile.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountHistoryModel implements Parcelable {


    public static Creator<AccountHistoryModel> getCREATOR() {
        return CREATOR;
    }

    public String getProfileimage() {
        return Profileimage;
    }

    public void setProfileimage(String profileimage) {
        Profileimage = profileimage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCarname() {
        return Carname;
    }

    public void setCarname(String carname) {
        Carname = carname;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    String Profileimage;
    String Name;
    String Carname;
    String Rating;
    String Location;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Profileimage);
        dest.writeString(this.Name);
        dest.writeString(this.Carname);
        dest.writeString(this.Rating);
        dest.writeString(this.Location);

    }

    public AccountHistoryModel() {
    }

    protected AccountHistoryModel(Parcel in) {
        this.Profileimage = in.readString();
        this.Name = in.readString();
        this.Carname = in.readString();
        this.Rating = in.readString();
        this.Location = in.readString();
    }

    public static final Creator<AccountHistoryModel> CREATOR = new Creator<AccountHistoryModel>() {
        public AccountHistoryModel createFromParcel(Parcel source) {
            return new AccountHistoryModel(source);
        }

        public AccountHistoryModel[] newArray(int size) {
            return new AccountHistoryModel[size];
        }
    };
}
