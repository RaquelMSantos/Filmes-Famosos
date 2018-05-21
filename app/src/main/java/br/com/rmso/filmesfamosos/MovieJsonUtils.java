package br.com.rmso.filmesfamosos;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raquel on 19/05/2018.
 */

final class MovieJsonUtils {
    private static final String IMG_URL = "http://image.tmdb.org/t/p/";

    public static ArrayList getSimpleMovieStringsFromJson(Context context, String movieJsonString) throws JSONException {

        final String results = "results";
        final String conresult = "sucess";

        ArrayList movies = new ArrayList();

        JSONObject movieJson = new JSONObject(movieJsonString);

        if (movieJson.has(conresult)) {
            String errorCode = movieJson.getString(conresult);

            if (errorCode.equals("false")) {
               return null;
            }
        }

        JSONArray moviesArray = movieJson.getJSONArray(results);

        for (int i = 0; i < moviesArray.length(); i++){
            JSONObject movieJsonObject = moviesArray.getJSONObject(i);
            String posterPath = movieJsonObject.getString("poster_path");
            String backdropPath = movieJsonObject.getString("backdrop_path");

            Movie movie = new Movie();
            movie.setPoster_path(IMG_URL + "w185/" + posterPath);
            movie.setBackdrop_path(IMG_URL + "w500/" + backdropPath);
            movie.setId(movieJsonObject.getString("id"));
            movie.setOverview(movieJsonObject.getString("overview"));
            movie.setDate(movieJsonObject.getString("release_date"));
            movie.setTitle(movieJsonObject.getString("original_title"));
            movie.setAverage(movieJsonObject.getString("vote_average"));
            movies.add(movie);
        }
        return movies;
    }
}
