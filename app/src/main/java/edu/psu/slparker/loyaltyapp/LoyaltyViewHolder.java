package edu.psu.slparker.loyaltyapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class LoyaltyViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    TextView textView_loyaltyDescription;

    public LoyaltyViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        textView_loyaltyDescription = itemView.findViewById(R.id.textView_loyaltyDescription);
    }
}
