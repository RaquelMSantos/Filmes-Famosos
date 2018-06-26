package br.com.rmso.filmesfamosos.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import br.com.rmso.filmesfamosos.BuildConfig;

/**
 * Created by Raquel on 24/06/2018.
 */

public class TrailerNetworkUtils {
    private static int key;
    private static String apiKey = BuildConfig.MovieApiKey;

    public static URL buildUrl (int baseURL){
        key = baseURL;
        String baseMovieURL = "http://api.themoviedb.org/3/movie/"+key+"/videos?api_key="+apiKey+"&language=en-US";

        URL url = null;
        Uri builtUri = Uri.parse(baseMovieURL).buildUpon()
                .build();

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl (URL url) throws IOException {
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
