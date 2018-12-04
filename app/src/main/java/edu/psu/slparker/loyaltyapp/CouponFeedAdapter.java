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

public class CouponFeedAdapter extends RecyclerView.Adapter<CouponFeedAdapter.CouponViewHolder> {
    private Context context;
    List<String> couponFeed = null;

    public static class CouponViewHolder extends RecyclerView.ViewHolder {
        // Elements defined in the UI
        CardView parentLayout;

        TextView couponTextView;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.cardViewCoupon);
            couponTextView = itemView.findViewById(R.id.textView_couponDescription);
        }
    }

    public CouponFeedAdapter(Context context, List<String> myDataset) {
        this.context = context;
        couponFeed = myDataset;
    }


    @NonNull
    @Override
    public CouponFeedAdapter.CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_feed_layout, parent, false);

        CouponViewHolder viewHolder = new CouponViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CouponFeedAdapter.CouponViewHolder loyaltyViewHolder, int position) {
        loyaltyViewHolder.couponTextView.setText(couponFeed.get(position));
    }

    @Override
    public int getItemCount() {
        return couponFeed.size();
    }
}
