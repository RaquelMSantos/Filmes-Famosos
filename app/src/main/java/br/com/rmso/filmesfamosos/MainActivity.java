package br.com.rmso.filmesfamosos;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter mMovieAdapter;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Movie[] movies = new Movie[10];

        mGridView = findViewById(R.id.gridview_movies);
        mMovieAdapter = new MovieAdapter(this, movies);
        mGridView.setAdapter(mMovieAdapter);

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
                Toast.makeText(MainActivity.this, "Menu Popular", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_top_rated:
                Toast.makeText(MainActivity.this, "Menu Rating", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
