package com.example.android.movielistapplication;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String MOVIE_LIST_BASE_URL =
            "https://hoblist.com/movieList";

    final static String PARAM_CATEGORY = "category";
    final static String PARAM_LANGUAGE = "language";
    final static String PARAM_GENRE = "genre";
    final static String PARAM_SORT = "sort";

    final static String catValue = "movies";
    final static String languageVal = "kannada";
    final static String genreVal = "all";
    final static String sortBy = "voting";

    public static URL searchUrl = null;

    public static void buildUrl() {
        Uri builtUri = Uri.parse(MOVIE_LIST_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_CATEGORY, catValue)
                .appendQueryParameter(PARAM_LANGUAGE, languageVal)
                .appendQueryParameter(PARAM_GENRE, genreVal)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        try {
            searchUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static String getResponseFromHttpUrl() throws IOException {
        buildUrl();
        HttpURLConnection urlConnection = (HttpURLConnection) searchUrl.openConnection();
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
