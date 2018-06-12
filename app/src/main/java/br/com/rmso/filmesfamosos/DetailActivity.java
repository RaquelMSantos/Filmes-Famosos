package br.com.rmso.filmesfamosos;

import android.support.design.widget.CollapsingToolbarLayout;
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

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

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

        //Primeiro converte de String para Date
        DateFormat formatUS = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = formatUS.parse(movie.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat formatBR = new SimpleDateFormat("dd-mm-yyyy");
        String dateFormated = formatBR.format(date);

        if (movie != null){
            Picasso.with(this).load(movie.getBackdrop_path()).into(mPosterImageView);
            mTitleTextView.setText(movie.getTitle());
            mDateTextView.setText(dateFormated);
            mAverageTextView.setText(movie.getAverage());
            mOverViewTextView.setText(movie.getOverview());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        switch (itemid){
            case R.id.action_favorites:
                Toast.makeText(DetailActivity.this, "Salvar nos favoritos", Toast.LENGTH_SHORT).show();

                break;

                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
}
