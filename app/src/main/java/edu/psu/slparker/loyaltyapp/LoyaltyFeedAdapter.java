package edu.psu.slparker.loyaltyapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LoyaltyFeedAdapter extends RecyclerView.Adapter<LoyaltyFeedAdapter.LoyaltyViewHolder> {
    private Context context;
    List<String> loyaltyFeed = null;

    public static class LoyaltyViewHolder extends RecyclerView.ViewHolder {
        // Elements defined in the UI
        CardView parentLayout;

        TextView loyaltyTextView;

        public LoyaltyViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.cardView);
            loyaltyTextView = itemView.findViewById(R.id.textView_loyaltyDescription);
        }
    }

    public LoyaltyFeedAdapter(Context context, List<String> myDataset) {
        this.context = context;
        loyaltyFeed = myDataset;
    }


    @NonNull
    @Override
    public LoyaltyFeedAdapter.LoyaltyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loyalty_feed_layout, parent, false);

        LoyaltyViewHolder viewHolder = new LoyaltyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoyaltyFeedAdapter.LoyaltyViewHolder loyaltyViewHolder, int position) {
        loyaltyViewHolder.loyaltyTextView.setText(loyaltyFeed.get(position));
    }

    @Override
    public int getItemCount() {
        return loyaltyFeed.size();
    }
}
