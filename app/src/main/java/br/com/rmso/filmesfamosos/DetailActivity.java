package br.com.rmso.filmesfamosos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.rmso.filmesfamosos.adapters.ReviewAdapter;
import br.com.rmso.filmesfamosos.adapters.TrailerAdapter;
import br.com.rmso.filmesfamosos.database.AppDatabase;
import br.com.rmso.filmesfamosos.database.Movie;
import br.com.rmso.filmesfamosos.utilities.ReviewJsonUtils;
import br.com.rmso.filmesfamosos.utilities.ReviewNetworkUtils;
import br.com.rmso.filmesfamosos.utilities.TrailerJsonUtils;
import br.com.rmso.filmesfamosos.utilities.TrailerNetworkUtils;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {
    public static final String EXTRA_MOVIE = "extraMovie";
    private static final int DEFAULT_MOVIE_ID = -1;
    private static final String TRAILER_DETAILS_EXTRA = "trailer_query";
    private static final String REVIEW_DETAILS_EXTRA = "review_query";
    private final int TRAILER_DETAILS_LOADER = 44;
    private final int REVIEW_DETAILS_LOADER = 88;

    private Movie movie;
    private AppDatabase mDb;
    private boolean  isFavorite = false;
    MenuItem menuItem = null;
    private int idMovie;

    ImageView mPosterImageView;
    TextView mTitleTextView;
    TextView mDateTextView;
    TextView mAverageTextView;
    TextView mOverViewTextView;
    RecyclerView mTrailersRecyclerView;
    RecyclerView mReviewsRecyclerView;

    ReviewAdapter mReviewAdapter;
    TrailerAdapter mTrailerAdapter;
    ArrayList<Trailer> mTrailerList;
    ArrayList<Review> mReviewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());
        movie = getIntent().getExtras().getParcelable(EXTRA_MOVIE);

        initViews();

        movieDB(movie.getId());
    }

    private void initViews() {
        mPosterImageView = findViewById(R.id.img_poster);
        mTitleTextView = findViewById(R.id.tv_title);
        mDateTextView = findViewById(R.id.tv_date);
        mAverageTextView = findViewById(R.id.tv_average);
        mOverViewTextView = findViewById(R.id.tv_overview);
        mReviewsRecyclerView = findViewById(R.id.rv_reviews);
        mTrailersRecyclerView = findViewById(R.id.rv_trailers);

        if (movie != null) {
            DateFormat formatUS = new SimpleDateFormat("yyyy-mm-dd");
            Date date = null;
            try {
                date = formatUS.parse(movie.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateFormat formatBR = new SimpleDateFormat("dd-mm-yyyy");
            String dateFormated = formatBR.format(date);

            Picasso.with(this).load(movie.getBackdrop_path()).into(mPosterImageView);
            mTitleTextView.setText(movie.getTitle());
            mDateTextView.setText(dateFormated);
            mAverageTextView.setText(movie.getAverage());
            mOverViewTextView.setText(movie.getOverview());
            idMovie = Integer.parseInt(movie.getId());
        }

        mReviewList = new ArrayList<>();
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mTrailerList = new ArrayList<>();
        mTrailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        reviewLoader(idMovie);
        trailerLoader(idMovie);

        mReviewAdapter = new ReviewAdapter(mReviewList);
        mReviewsRecyclerView.setAdapter(mReviewAdapter);
        mTrailerAdapter = new TrailerAdapter(getApplicationContext(), this, mTrailerList);
        mTrailersRecyclerView.setAdapter(mTrailerAdapter);
    }

    private void movieDB(String id) {
        final int idInt =  Integer.parseInt(id);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> movies = mDb.movieDao().loadMovieByUId(idInt);

                if (movies.size() > 0){
                    isFavorite = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        if (isFavorite) {
            menu.getItem(0).setIcon(R.drawable.baseline_favorite_white_24);
        }else {
            menu.getItem(0).setIcon(R.drawable.baseline_favorite_border_white_24);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int itemid = item.getItemId();
        final int idMovie = Integer.parseInt(movie.getId());
        menuItem = item;

        switch (itemid){
            case R.id.action_favorites:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Movie> movies = mDb.movieDao().loadMovieByUId(idMovie);

                        if (movies.size() > 0) {
                            onDeleteFavorites();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    item.setIcon(R.drawable.baseline_favorite_border_white_24);
                                }
                            });

                        }else {
                            onSaveFavorites();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    item.setIcon(R.drawable.baseline_favorite_white_24);
                                }
                            });
                        }
                    }
                });

                break;

                default:
                    break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveFavorites(){
        final Movie newMovie = new Movie(movie.getIdKey(), movie.getId(), movie.getDate(), movie.getAverage(), movie.getBackdrop_path(), movie.getOverview(), movie.getTitle());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insertMovie(newMovie);
            }
        });
    }

    public void onDeleteFavorites(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().deleteMovie(movie);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(int itemClicked, Trailer trailerClicked) {
        String urlYoutube = "https://www.youtube.com/watch?v=";
        Uri uri = Uri.parse(urlYoutube.concat(trailerClicked.getKey()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    private void reviewLoader (int idMovie){
        Bundle queryBundle = new Bundle();
        queryBundle.putInt(REVIEW_DETAILS_EXTRA, idMovie);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Integer> reviewSearchLoader = loaderManager.getLoader(REVIEW_DETAILS_LOADER);

        if (reviewSearchLoader == null){
            loaderManager.initLoader(REVIEW_DETAILS_LOADER, queryBundle, reviewLoaderListener).forceLoad();
        }else {
            loaderManager.restartLoader(REVIEW_DETAILS_LOADER, queryBundle, reviewLoaderListener);
        }
    }

    private void trailerLoader (int idMovie){
        Bundle queryBundle = new Bundle();
        queryBundle.putInt(TRAILER_DETAILS_EXTRA, idMovie);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<Integer> trailerSearchLoader = loaderManager.getLoader(TRAILER_DETAILS_LOADER);

        if (trailerSearchLoader == null){
            loaderManager.initLoader(TRAILER_DETAILS_LOADER, queryBundle, trailerLoaderListener).forceLoad();
        }else {
            loaderManager.restartLoader(TRAILER_DETAILS_LOADER, queryBundle, trailerLoaderListener);
        }
    }

    public LoaderManager.LoaderCallbacks<ArrayList> reviewLoaderListener = new LoaderManager.LoaderCallbacks<ArrayList>() {
        @NonNull
        @Override
        public Loader<ArrayList> onCreateLoader(int id, @Nullable final Bundle bundle) {
            return new AsyncTaskLoader(DetailActivity.this) {
                @Override
                public ArrayList loadInBackground() {
                    int idMovie = bundle.getInt(REVIEW_DETAILS_EXTRA);
                    if (idMovie < 0) {
                        return null;
                    }

                    try {
                        URL movieRequestUrl = ReviewNetworkUtils.buildUrl(idMovie);

                        String jsonReviewResponse = ReviewNetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                        ArrayList reviewJson = new ArrayList<>();

                        try {
                            reviewJson = ReviewJsonUtils.getSimpleReviewStringsFromJson(jsonReviewResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return reviewJson;

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(@NonNull Loader<ArrayList> loader, ArrayList reviewList) {

            if (reviewList != null){
                mReviewAdapter.setReviews(reviewList);
                mReviewList = reviewList;
                mReviewAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<ArrayList> loader) {

        }
    };

    public LoaderManager.LoaderCallbacks<ArrayList> trailerLoaderListener = new LoaderManager.LoaderCallbacks<ArrayList>() {

        public AsyncTaskLoader onCreateLoader(int id, final Bundle bundle){
            return new AsyncTaskLoader(DetailActivity.this) {

                @Nullable
                @Override
                public ArrayList loadInBackground() {
                    int idMovie = bundle.getInt(TRAILER_DETAILS_EXTRA);
                    if (idMovie < 0) {
                        return null;
                    }

                    try {
                        URL movieRequestUrl = TrailerNetworkUtils.buildUrl(idMovie);

                        String jsonTrailerResponse = TrailerNetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                        ArrayList trailerJson = new ArrayList<>();

                        try {
                            trailerJson = TrailerJsonUtils.getSimpleTrailerStringsFromJson(DetailActivity.this, jsonTrailerResponse, movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return trailerJson;

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(@NonNull Loader<ArrayList> loader, ArrayList trailerList) {
            if (trailerList != null){
                mTrailerAdapter.setTrailers(trailerList);
                mTrailerList = trailerList;
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<ArrayList> loader) {
        }
    };
}
