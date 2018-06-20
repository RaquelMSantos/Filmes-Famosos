package br.com.rmso.filmesfamosos.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raquel on 16/05/2018.
 */
@Entity(tableName = "movie")
public class Movie implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int idKey;
    private String id = "id";
    private String results = "results";
    private String date = "release_date";
    private String average = "vote_average";
    private String poster_path = "poster_path";
    private String backdrop_path = "backdrop_path";
    private String overview = "overview";
    private String title = "original_title";

    @Ignore
    public Movie() {
    }

    public Movie(int idKey, String id, String date, String average, String backdrop_path, String overview, String title) {
        this.idKey = idKey;
        this.id = id;
        this.date = date;
        this.average = average;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.title = title;
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

    public int getIdKey() {
        return idKey;
    }

    public void setIdKey(int idKey) {
        this.idKey = idKey;
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
