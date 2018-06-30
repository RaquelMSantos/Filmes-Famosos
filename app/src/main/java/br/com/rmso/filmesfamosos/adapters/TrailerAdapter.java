package br.com.rmso.filmesfamosos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.rmso.filmesfamosos.R;
import br.com.rmso.filmesfamosos.Trailer;

/**
 * Created by Raquel on 20/06/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> trailerList;
    private Context context;
    private final TrailerAdapterOnClickHandler clickHandler;
    private static final String IMG_URL = "https://img.youtube.com/vi/";
    private String finalUrl = "/0.jpg";

    public TrailerAdapter(Context mContext, TrailerAdapterOnClickHandler mClickHandler, ArrayList<Trailer> mTrailerList) {
        clickHandler = mClickHandler;
        context = mContext;
        trailerList = mTrailerList;
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(int itemClicked, Trailer trailerClicked);
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item_trailer, parent, false);
        return new TrailerAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer trailer = trailerList.get(position);
        Picasso.with(context)
                .load(IMG_URL.concat(trailer.getKey()).concat(finalUrl))
                .placeholder(R.drawable.placeholder_videos).centerCrop()
                .error(R.drawable.placeholder_image_error).fit()
                .into(holder.mThumbnailImageView);
        holder.mTitleTrailerTextView.setText(trailer.getTitle());
    }

    @Override
    public int getItemCount() {
        if (trailerList !=null) return trailerList.size(); else return 0;
    }

    public void setTrailers(ArrayList<Trailer> trailerList) {
        if (trailerList != null){
            this.trailerList = trailerList;
            notifyDataSetChanged();
        }
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mThumbnailImageView;
        public final TextView mTitleTrailerTextView;

        public TrailerViewHolder(View view) {
            super(view);
            mThumbnailImageView = itemView.findViewById(R.id.trailer_thumbnail);
            mTitleTrailerTextView = itemView.findViewById(R.id.trailer_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            Trailer trailerClicked = trailerList.get(itemPosition);
            clickHandler.onClick(itemPosition, trailerClicked);
        }
    }
}

