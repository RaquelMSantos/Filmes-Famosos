package br.com.rmso.filmesfamosos;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.rmso.filmesfamosos.adapters.MovieAdapter;
import br.com.rmso.filmesfamosos.database.AppDatabase;
import br.com.rmso.filmesfamosos.database.Movie;
import br.com.rmso.filmesfamosos.utilities.MovieJsonUtils;
import br.com.rmso.filmesfamosos.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingMovies;
    private AppDatabase mDb;

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
        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation);

        mDb = AppDatabase.getInstance(getApplicationContext());

        loadMoviesData();

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_popular:
                        showMovieDataView();
                        new FetchMovieTask("popular").execute();
                        break;

                    case R.id.action_top_rated:
                        showMovieDataView();
                        new FetchMovieTask("top_rated").execute();
                        break;

                    case R.id.action_favorites:
                        retrieveMovies();
                        break;

                    default:
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveMovies();
    }

    private void retrieveMovies() {
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                mMovieAdapter.setMovies(movieList);
            }
        });
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
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movieClicked);
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
}
