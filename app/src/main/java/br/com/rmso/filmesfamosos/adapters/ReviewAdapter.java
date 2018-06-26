package br.com.rmso.filmesfamosos.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.rmso.filmesfamosos.R;
import br.com.rmso.filmesfamosos.Review;

/**
 * Created by Raquel on 20/06/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> reviewsList;

    public ReviewAdapter(ArrayList<Review> mReviewsList) {
        reviewsList = mReviewsList;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item_review, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        final Review review = reviewsList.get(position);
        holder.mAuthorTextView.setText(review.getAuthor());
        holder.mTextReviewTextView.setText(review.getText());
    }

    @Override
    public int getItemCount() {
        if (reviewsList !=null) return reviewsList.size(); else return 0;
    }

    public void setReviews(ArrayList<Review> reviewsList) {
        if (reviewsList != null){
            this.reviewsList = reviewsList;
            notifyDataSetChanged();
        }
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mAuthorTextView;
        public final TextView mTextReviewTextView;

        public ReviewViewHolder(View view) {
            super(view);
            mAuthorTextView = itemView.findViewById(R.id.tv_name_author);
            mTextReviewTextView = itemView.findViewById(R.id.tv_review_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
