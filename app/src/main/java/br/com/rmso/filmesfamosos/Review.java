package br.com.rmso.filmesfamosos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raquel on 20/06/2018.
 */

public class Review implements Parcelable {

    private String author;
    private String text;
    private String id;

    public Review() {
    }

    public Review(Parcel p) {
        author = p.readString();
        text = p.readString();
        id = p.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(text);
        parcel.writeString(id);
    }


}
