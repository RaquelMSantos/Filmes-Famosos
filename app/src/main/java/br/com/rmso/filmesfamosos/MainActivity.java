package br.com.rmso.filmesfamosos;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(getApplicationContext(), this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mLoadingMovies = findViewById(R.id.pb_loading_movies);

        loadMoviesData();
    }

    private void loadMoviesData(){
        showMovieDataView();
        new FetchMovieTask("popular").execute();
    }

    private void showMovieDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int itemClicked, Movie movieClicked) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movieClicked);
        startActivity(intent);
    }

    public class FetchMovieTask extends AsyncTask<ArrayList, Void, ArrayList> {

        final String movie_params;

        private FetchMovieTask(String string){
            if (string.equals("top_rated")){
                movie_params = "top_rated";
            }else {
                movie_params = "popular";
            }
        }

        @Override
        protected ArrayList doInBackground(ArrayList... ArrayList) {
            URL movieRequestUrl = NetworkUtils.buildUrl(movie_params);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl (movieRequestUrl);

                ArrayList simpleJsonMovieData = new ArrayList();

                try {
                    simpleJsonMovieData = MovieJsonUtils.getSimpleMovieStringsFromJson(MainActivity.this, jsonMovieResponse);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                return simpleJsonMovieData;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList movieList) {
            if (movieList != null){
                mLoadingMovies.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                showMovieDataView();
                mMovieAdapter.setMovieData(movieList);
                mMovieAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLoadingMovies.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        switch (itemid) {
            case R.id.action_popular:
                showMovieDataView();
                new FetchMovieTask("popular").execute();
                break;

            case R.id.action_top_rated:
                showMovieDataView();
                new FetchMovieTask("top_rated").execute();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
