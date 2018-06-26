package br.com.rmso.filmesfamosos.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.rmso.filmesfamosos.Review;
import br.com.rmso.filmesfamosos.database.Movie;

/**
 * Created by Raquel on 24/06/2018.
 */

public class ReviewJsonUtils {

    public static ArrayList getSimpleReviewStringsFromJson(String reviewJsonString)
            throws JSONException {

        ArrayList<Review> reviews = new ArrayList<>();

        final String results = "results";
        final String conresult = "success";
        final String msg = "status_message";

        JSONObject reviewJson = new JSONObject(reviewJsonString);

        if (reviewJson.has(conresult)) {
            String errorCode = reviewJson.getString(conresult);
            if (errorCode.equals("false")){
                return null;
            }
        }

        JSONArray reviewsJson = reviewJson.getJSONArray(results);

        for (int i = 0; i < reviewsJson .length(); i++) {
            JSONObject reviewJsonObject = reviewsJson .getJSONObject(i);

            Review review = new Review();
            review.setId(reviewJsonObject.getString("id"));
            review.setAuthor(reviewJsonObject.getString("author"));
            review.setText(reviewJsonObject.getString("content"));
            reviews.add(review);
        }

        return reviews;
    }
}
