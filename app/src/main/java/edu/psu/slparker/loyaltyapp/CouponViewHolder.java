package edu.psu.slparker.loyaltyapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CouponViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    TextView textView_couponDescription;

    public CouponViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardViewCoupon);
        textView_couponDescription = itemView.findViewById(R.id.textView_couponDescription);
    }
}
