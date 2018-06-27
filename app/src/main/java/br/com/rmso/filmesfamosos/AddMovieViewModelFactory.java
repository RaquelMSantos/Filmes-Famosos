package br.com.rmso.filmesfamosos;

import android.arch.lifecycle.*;
import android.arch.lifecycle.ViewModel;

import br.com.rmso.filmesfamosos.database.AppDatabase;

/**
 * Created by Raquel on 12/06/2018.
 */

public class AddMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mMovieId;

    public AddMovieViewModelFactory(AppDatabase database, int movieId){
        mDb = database;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AddMovieViewModel(mDb, mMovieId);
    }
}
