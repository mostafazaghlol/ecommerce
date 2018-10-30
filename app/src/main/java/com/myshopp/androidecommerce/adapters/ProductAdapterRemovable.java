package com.myshopp.androidecommerce.adapters;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.myshopp.androidecommerce.activities.MainActivity;
import com.myshopp.androidecommerce.R;

import java.util.List;

import com.myshopp.androidecommerce.utils.Utilities;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.databases.User_Recents_DB;
import com.myshopp.androidecommerce.fragments.Product_Description;
import com.myshopp.androidecommerce.models.product_model.ProductDetails;


/**
 * ProductAdapterRemovable is the adapter class of RecyclerView holding List of Products in RecentlyViewed and WishList
 **/

public class ProductAdapterRemovable extends RecyclerView.Adapter<ProductAdapterRemovable.MyViewHolder> {

    private Context context;

    private String customerID;
    private TextView emptyText;

    private boolean isRecents;
    private boolean isHorizontal;

    private List<ProductDetails> productList;

    private User_Recents_DB recents_db;


    public ProductAdapterRemovable(Context context, List<ProductDetails> productList, boolean isHorizontal, boolean isRecents, TextView emptyText) {
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        this.isRecents = isRecents;
        this.emptyText = emptyText;

        recents_db = new User_Recents_DB();
        customerID = this.context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("userID", "");
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_sm, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_lg, parent, false);
        }

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // Get the data model based on Position
        final ProductDetails product = productList.get(position);

        holder.product_checked.setVisibility(View.GONE);


        // Set Product Image on ImageView with Glide Library
        Glide.with(context)
                .load(ConstantValues.ECOMMERCE_URL+product.getProductsImage())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.cover_loader.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.cover_loader.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.product_thumbnail);


        holder.product_title.setText(product.getProductsName());
        holder.product_price_old.setPaintFlags(holder.product_price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


        // Calculate the Discount on Product with static method of Helper class
        String discount = Utilities.checkDiscount(product.getProductsPrice(), product.getDiscountPrice());

        if (discount != null) {
            // Set Product's Price
            holder.product_price_old.setVisibility(View.VISIBLE);
            holder.product_price_old.setText(ConstantValues.CURRENCY_SYMBOL + product.getProductsPrice());
            holder.product_price_new.setText(ConstantValues.CURRENCY_SYMBOL + product.getDiscountPrice());

            holder.product_tag_new.setVisibility(View.GONE);
            holder.product_tag_new_text.setVisibility(View.GONE);

            // Set Discount Tag and its Text
            holder.product_tag_discount.setVisibility(View.VISIBLE);
            holder.product_tag_discount_text.setVisibility(View.VISIBLE);
            holder.product_tag_discount_text.setText(discount);

        } else {

            // Check if the Product is Newly Added with the help of static method of Helper class
            if (Utilities.checkNewProduct(product.getProductsDateAdded())) {
                // Set New Tag and its Text
                holder.product_tag_new.setVisibility(View.VISIBLE);
                holder.product_tag_new_text.setVisibility(View.VISIBLE);
            } else {
                holder.product_tag_new.setVisibility(View.GONE);
                holder.product_tag_new_text.setVisibility(View.GONE);
            }

            // Hide Discount Text and Set Product's Price
            holder.product_tag_discount.setVisibility(View.GONE);
            holder.product_tag_discount_text.setVisibility(View.GONE);
            holder.product_price_old.setVisibility(View.GONE);
            holder.product_price_new.setText(ConstantValues.CURRENCY_SYMBOL + product.getProductsPrice());
        }
        
        

        // Handle the Click event of product_thumbnail ImageView
        holder.product_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Product Info
                Bundle itemInfo = new Bundle();
                itemInfo.putParcelable("productDetails", product);

                // Navigate to Product_Description of selected Product
                Fragment fragment = new Product_Description();
                fragment.setArguments(itemInfo);
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();


                // Add the Product to User's Recently Viewed Products
                if (!recents_db.getUserRecents().contains(product.getProductsId())) {
                    recents_db.insertRecentItem(product.getProductsId());
                }
            }
        });
    
    
        holder.product_remove_btn.setVisibility(View.VISIBLE);
    
        // Handle Click event of product_remove_btn Button
        holder.product_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
                // Check if Product is in User's Recents
                if (isRecents) {
                    // Delete Product from User_Recents_DB Local Database
                    recents_db.deleteRecentItem(product.getProductsId());
                
                } else {
                    // Unlike the Product for the User with the static method of Product_Description
                    Product_Description.UnlikeProduct(product.getProductsId(), customerID, context, view);
                }
            
                // Remove Product from productList List
                productList.remove(holder.getAdapterPosition());
            
                notifyItemRemoved(holder.getAdapterPosition());
            
                // Update View
                updateView(isRecents);
            }
        });

    }



    //********** Returns the total number of items in the RecyclerView *********//

    @Override
    public int getItemCount() {
        return productList.size();
    }



    //********** Change the Layout View based on the total number of items in the RecyclerView *********//

    public void updateView(boolean isRecentProducts) {


        // Check if Product is in User's Recents
        if (isRecentProducts) {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.VISIBLE);
            } else {
                emptyText.setVisibility(View.GONE);
            }

        } else {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
        }
        
        notifyDataSetChanged();

    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar cover_loader;
        ImageView product_checked;
        Button product_remove_btn;
        ToggleButton product_like_btn;
        ImageView product_thumbnail, product_tag_new, product_tag_discount;
        TextView product_title, product_price_old, product_price_new, product_tag_new_text, product_tag_discount_text;

        public MyViewHolder(final View itemView) {
            super(itemView);

            product_checked = (ImageView) itemView.findViewById(R.id.product_checked);
            cover_loader = (ProgressBar) itemView.findViewById(R.id.product_cover_loader);

            product_remove_btn = (Button) itemView.findViewById(R.id.product_card_Btn);
            product_like_btn = (ToggleButton) itemView.findViewById(R.id.product_like_btn);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_price_old = (TextView) itemView.findViewById(R.id.product_price_old);
            product_price_new = (TextView) itemView.findViewById(R.id.product_price_new);
            product_thumbnail = (ImageView) itemView.findViewById(R.id.product_cover);
            product_tag_new = (ImageView) itemView.findViewById(R.id.product_tag_new);
            product_tag_new_text = (TextView) itemView.findViewById(R.id.product_tag_new_text);
            product_tag_discount = (ImageView) itemView.findViewById(R.id.product_tag_discount);
            product_tag_discount_text = (TextView) itemView.findViewById(R.id.product_tag_discount_text);

            product_like_btn.setVisibility(View.GONE);
            product_remove_btn.setText(context.getString(R.string.removeProduct));
            product_remove_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
        }
    }

}

