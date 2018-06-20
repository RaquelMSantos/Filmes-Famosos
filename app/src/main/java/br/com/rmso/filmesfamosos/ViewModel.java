package br.com.rmso.filmesfamosos;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.rmso.filmesfamosos.database.AppDatabase;
import br.com.rmso.filmesfamosos.database.Movie;

/**
 * Created by Raquel on 12/06/2018.
 */

public class ViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public ViewModel(Application application) {
        super(application);

        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        movies = database.movieDao().loadAllMovie();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
