package com.pietrantuono.podcasts.addpodcast.singlepodcast.view;


import android.os.Parcel;
import android.os.Parcelable;

public class ExtractedColors implements Parcelable {
    private final int backgroundColor;

    ExtractedColors(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    protected ExtractedColors(Parcel in) {
        backgroundColor = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(backgroundColor);
    }

    @SuppressWarnings("unused")
    public static final Creator<ExtractedColors> CREATOR = new Creator<ExtractedColors>() {
        @Override
        public ExtractedColors createFromParcel(Parcel in) {
            return new ExtractedColors(in);
        }

        @Override
        public ExtractedColors[] newArray(int size) {
            return new ExtractedColors[size];
        }
    };
}
