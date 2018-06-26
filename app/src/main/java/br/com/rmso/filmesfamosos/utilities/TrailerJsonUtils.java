package br.com.rmso.filmesfamosos.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.rmso.filmesfamosos.Review;
import br.com.rmso.filmesfamosos.Trailer;
import br.com.rmso.filmesfamosos.database.Movie;

/**
 * Created by Raquel on 24/06/2018.
 */

public class TrailerJsonUtils {
    private static final String VIDEO_URL = "http://image.tmdb.org/t/p/";

    public static ArrayList getSimpleTrailerStringsFromJson(Context context, String trailerJsonString, Movie movie)
            throws JSONException {

        ArrayList<Trailer> trailers = new ArrayList<>();

        final String results = "results";
        final String conresult = "success";
        final String msg = "status_message";

        JSONObject trailerJson = new JSONObject(trailerJsonString);

        if (trailerJson.has(conresult)) {
            String errorCode = trailerJson.getString(conresult);
            if (errorCode.equals("false")){
                return null;
            }
        }

        JSONArray trailersJson = trailerJson.getJSONArray(results);

        for (int i = 0; i < trailersJson .length(); i++) {
            JSONObject trailerJsonObject = trailersJson .getJSONObject(i);

            Trailer trailer = new Trailer();
            trailer.setId(trailerJsonObject.getString("id"));
            trailer.setTitle(trailerJsonObject.getString("name"));
            trailer.setKey(trailerJsonObject.getString("key"));
            trailers.add(trailer);
        }

        return trailers;
    }
}
