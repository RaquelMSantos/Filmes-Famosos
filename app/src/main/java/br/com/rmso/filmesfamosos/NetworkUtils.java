package br.com.rmso.filmesfamosos;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Raquel on 17/05/2018.
 */

class NetworkUtils {
    private static String apiKey = BuildConfig.MovieApiKey;
    private static final String baseMoviePOPURL = "http://api.themoviedb.org/3/movie/popular?api_key="+apiKey;
    private static final String baseMovieTOPURL = "http://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey;

    public static URL buildUrl(String baseURL){
        URL url = null;

        if (baseURL.equals("popular")) {
            Uri builtUri = Uri.parse(baseMoviePOPURL).buildUpon()
                    .build();
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }else {
            Uri builtUri = Uri.parse(baseMovieTOPURL).buildUpon()
                    .build();
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }
    }

    public static String getResponseFromHttpUrl (URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

