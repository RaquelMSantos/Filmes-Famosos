package br.com.rmso.filmesfamosos;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raquel on 15/05/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int mNumberItems;
    private List<Movie> mMoviesList;
    private Context mContext;

    public Context getContext(){
        return mContext;
    }

    public MovieAdapter (Context context){
        mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, viewGroup, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);
        Picasso.with(mContext).load(movie.getBackdrop_path()).into(holder.movieCoverImageView);
       }

    @Override
    public int getItemCount() {
        if (mMoviesList !=null) return mMoviesList.size(); else return 0;
    }

    public void setMovieData(ArrayList MovieData) {
        mMoviesList = MovieData;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder  {
        ImageView movieCoverImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieCoverImageView = itemView.findViewById(R.id.img_movie_cover);
        }
    }

}
