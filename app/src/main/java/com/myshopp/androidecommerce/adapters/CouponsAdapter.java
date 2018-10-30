package com.myshopp.androidecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.myshopp.androidecommerce.R;

import java.util.List;

import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.fragments.Checkout;
import com.myshopp.androidecommerce.models.coupons_model.CouponsInfo;


/**
 * CouponsAdapter is the adapter class of RecyclerView holding List of Coupons in Checkout and Order_Details
 **/

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.MyViewHolder> {

    Context context;
    Boolean isRemovable;

    Checkout checkout;
    List<CouponsInfo> couponsList;


    public CouponsAdapter(Context context, List<CouponsInfo> couponsList, Boolean isRemovable, Checkout checkout) {
        this.context = context;
        this.checkout = checkout;
        this.isRemovable = isRemovable;
        this.couponsList = couponsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_coupons, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CouponsInfo coupon = couponsList.get(position);

        holder.coupon_code.setText(coupon.getCode());
        holder.coupon_discount.setText(ConstantValues.CURRENCY_SYMBOL + coupon.getDiscount());
    
        
        if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart") || coupon.getDiscountType().equalsIgnoreCase("percent")) {
            holder.coupon_type.setText(context.getString(R.string.cart));
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("fixed_product") || coupon.getDiscountType().equalsIgnoreCase("percent_product")) {
            holder.coupon_type.setText(context.getString(R.string.product));
        }
        
        
        if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart") || coupon.getDiscountType().equalsIgnoreCase("fixed_product")) {
            holder.coupon_amount.setText(ConstantValues.CURRENCY_SYMBOL + coupon.getAmount());
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("percent") || coupon.getDiscountType().equalsIgnoreCase("percent_product")) {
            holder.coupon_amount.setText(coupon.getAmount() + "%");
        }
    
        

        if (isRemovable) {
            holder.coupon_delete.setVisibility(View.VISIBLE);

            holder.coupon_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkout.removeCoupon(coupon);

                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeRemoved(holder.getAdapterPosition(), getItemCount());
                    notifyDataSetChanged();
                }
            });

        }
        else {
            holder.coupon_delete.setVisibility(View.GONE);
        }

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return couponsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton coupon_delete;
        private TextView coupon_code, coupon_type, coupon_amount, coupon_discount;


        public MyViewHolder(final View itemView) {
            super(itemView);

            coupon_code = (TextView) itemView.findViewById(R.id.coupon_code);
            coupon_type = (TextView) itemView.findViewById(R.id.coupon_type);
            coupon_amount = (TextView) itemView.findViewById(R.id.coupon_amount);
            coupon_discount = (TextView) itemView.findViewById(R.id.coupon_discount);
            coupon_delete = (ImageButton) itemView.findViewById(R.id.coupon_delete);

        }
    }
}

