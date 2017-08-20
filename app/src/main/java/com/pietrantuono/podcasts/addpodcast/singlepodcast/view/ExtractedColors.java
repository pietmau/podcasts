package com.pietrantuono.podcasts.addpodcast.singlepodcast.view;


import android.os.Parcel;
import android.os.Parcelable;

public class ExtractedColors implements Parcelable {
    private final int backgroundColor;
    private final int textColor;

    ExtractedColors(int backgroundColor, int textColor) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    protected ExtractedColors(Parcel in) {
        backgroundColor = in.readInt();
        textColor = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(backgroundColor);
        dest.writeInt(textColor);
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
