package br.com.rmso.filmesfamosos;

/**
 * Created by Raquel on 16/05/2018.
 */

public class Movie {
    private String title;
    private String overview;
    private String average;
    private String date;

    public Movie(String title, String overview, String average, String date){
        this.title = title;
        this.overview = overview;
        this.average = average;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
