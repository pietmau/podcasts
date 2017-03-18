package com.pietrantuono.podcasts.providers;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class RealmString extends RealmObject implements Parcelable {
    private String string;

    public RealmString() {

    }

    public RealmString(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    protected RealmString(Parcel in) {
        string = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(string);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RealmString> CREATOR = new Parcelable.Creator<RealmString>() {
        @Override
        public RealmString createFromParcel(Parcel in) {
            return new RealmString(in);
        }

        @Override
        public RealmString[] newArray(int size) {
            return new RealmString[size];
        }
    };
}
