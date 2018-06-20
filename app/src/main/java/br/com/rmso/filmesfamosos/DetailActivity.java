package br.com.rmso.filmesfamosos;

import android.arch.lifecycle.LiveData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.rmso.filmesfamosos.database.AppDatabase;
import br.com.rmso.filmesfamosos.database.Movie;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extraMovie";
    public static final String INSTANCE_MOVIE = "instanceMovie";
    private static final int DEFAULT_MOVIE_ID = -1;

    private Movie movie;
    private AppDatabase mDb;
    private int mMovieId = DEFAULT_MOVIE_ID;
    private boolean  isFavorite = false;
    MenuItem menuItem = null;

    ImageView mPosterImageView;
    TextView mTitleTextView;
    TextView mDateTextView;
    TextView mAverageTextView;
    TextView mOverViewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());
        movie = getIntent().getExtras().getParcelable(EXTRA_MOVIE);

        initViews();

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_MOVIE)){
            mMovieId = savedInstanceState.getInt(INSTANCE_MOVIE, DEFAULT_MOVIE_ID);
        }

        movieDB(movie.getId());
    }

    private void initViews() {
        mPosterImageView = findViewById(R.id.img_poster);
        mTitleTextView = findViewById(R.id.tv_title);
        mDateTextView = findViewById(R.id.tv_date);
        mAverageTextView = findViewById(R.id.tv_average);
        mOverViewTextView = findViewById(R.id.tv_overview);

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
        }
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
}
