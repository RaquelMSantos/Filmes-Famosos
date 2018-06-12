package br.com.rmso.filmesfamosos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raquel on 15/05/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMoviesList;
    private final Context mContext;

    private final MovieAdapterOnClickHandler mClickHandler;

    public MovieAdapter (Context context, MovieAdapterOnClickHandler clickHandler){
        mContext = context;
        mClickHandler = clickHandler;
    }


    public interface MovieAdapterOnClickHandler {
        void onClick(int itemClicked, Movie movieClicked);
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = mMoviesList.get(position);
        Picasso.with(mContext).load(movie.getBackdrop_path()).into(holder.movieCoverImageView);
        holder.mTitleMovieTextView.setText(mMoviesList.get(position).getTitle());
        holder.mAverageMovieTextView.setText(mMoviesList.get(position).getAverage());
       }

    @Override
    public int getItemCount() {
        if (mMoviesList !=null) return mMoviesList.size(); else return 0;
    }

    public void setMovieData(ArrayList MovieData) {
        mMoviesList = MovieData;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView movieCoverImageView;
        public final TextView mTitleMovieTextView;
        public final TextView mAverageMovieTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieCoverImageView = itemView.findViewById(R.id.img_movie_cover);
            mTitleMovieTextView = itemView.findViewById(R.id.tv_title_movie);
            mAverageMovieTextView = itemView.findViewById(R.id.tv_average_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            Movie movieClicked = mMoviesList.get(itemPosition);
            mClickHandler.onClick(itemPosition, movieClicked);
        }
    }
}
