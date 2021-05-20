package com.example.android.movielistapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.URL;

public class MovieListActivity extends AppCompatActivity {
    TextView mErrorMessageTextView, mMovieResultsTextView, mCompanyInfoTextView;
    ProgressBar mResultsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);

        mErrorMessageTextView = (TextView) findViewById(R.id.error_message_display);
        mResultsProgressBar = (ProgressBar) findViewById(R.id.movie_loading_indicator);
        mMovieResultsTextView = (TextView) findViewById(R.id.movie_list_search_results_json);
        mCompanyInfoTextView = (TextView) findViewById(R.id.movie_list_company_info);
    }

    void showJsonDataView() {
        mMovieResultsTextView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    void showErrorMessage(){
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mMovieResultsTextView.setVisibility(View.INVISIBLE);
    }

    public class FetchMovieListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mResultsProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            String movieSearchResults = null;
            try {
                movieSearchResults = NetworkUtils.getResponseFromHttpUrl();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            mResultsProgressBar.setVisibility(View.INVISIBLE);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                showJsonDataView();
                mMovieResultsTextView.setText(movieSearchResults);
            }
            else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.action_company_info) {
            mMovieResultsTextView.setVisibility(View.INVISIBLE);
            mCompanyInfoTextView.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
