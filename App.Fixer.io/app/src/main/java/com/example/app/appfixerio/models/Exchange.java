package com.example.app.appfixerio.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by joao on 18/07/17.
 */

public class Exchange implements Parcelable {

    public String base;
    public Date date;
    public HashMap<String, Float> rates;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }


}
