package br.com.rmso.filmesfamosos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raquel on 15/05/2018.
 */

public class MovieAdapter extends BaseAdapter {

    private final Movie[] movies;
    private final Context mContext;

    public MovieAdapter(Context context, Movie[] movies){
      this.mContext = context;
      this.movies = movies;
    }


    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final Movie movie = movies[position];

        if (view == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(
                    mContext);
            view = layoutInflater.inflate(R.layout.movie_list_item, null);
        }

        final ImageView mCoverImageView = view.findViewById(R.id.img_movie_cover);
        final TextView mNameMovieTextView = view.findViewById(R.id.tv_movie_name);

        return view;
    }
}
