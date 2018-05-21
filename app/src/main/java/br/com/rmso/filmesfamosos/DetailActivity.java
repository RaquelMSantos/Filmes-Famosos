package br.com.rmso.filmesfamosos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView mPosterImageView = findViewById(R.id.img_poster);
        TextView mTitleTextView = findViewById(R.id.tv_title);
        TextView mDateTextView = findViewById(R.id.tv_date);
        TextView mAverageTextView = findViewById(R.id.tv_average);
        TextView mOverViewTextView = findViewById(R.id.tv_overview);

        Movie movie = getIntent().getExtras().getParcelable("movie");

        if (movie != null){
            Picasso.with(this).load(movie.getBackdrop_path()).into(mPosterImageView);
            mTitleTextView.setText(movie.getTitle());
            mDateTextView.setText(movie.getDate());
            mAverageTextView.setText(movie.getAverage());
            mOverViewTextView.setText(movie.getOverview());
        }
    }
}
