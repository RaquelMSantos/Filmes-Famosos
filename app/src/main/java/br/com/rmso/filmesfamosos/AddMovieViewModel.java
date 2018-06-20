package br.com.rmso.filmesfamosos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import br.com.rmso.filmesfamosos.database.AppDatabase;
import br.com.rmso.filmesfamosos.database.Movie;

/**
 * Created by Raquel on 12/06/2018.
 */

public class AddMovieViewModel extends ViewModel {
    private LiveData<Movie> movie;

    public AddMovieViewModel(AppDatabase mDb, int mMovieId) {
        movie = mDb.movieDao().loadMovieById(mMovieId);
    }

    public LiveData<Movie> getMovie(){
        return movie;
    }
}
