package br.com.rmso.filmesfamosos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raquel on 16/05/2018.
 */

public class Movie implements Parcelable{
    private String id = "id";
    private String results = "results";
    private String date = "release_date";
    private String average = "vote_average";
    private String poster_path = "poster_path";
    private String backdrop_path = "backdrop_path";
    private String overview = "overview";
    private String title = "original_title";

    public Movie() {
    }

    private Movie (Parcel p) {
        id = p.readString();
        results = p.readString();
        date = p.readString();
        average = p.readString();
        poster_path = p.readString();
        backdrop_path = p.readString();
        overview = p.readString();
        title = p.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(results);
        parcel.writeString(date);
        parcel.writeString(average);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(overview);
        parcel.writeString(title);
    }
}
