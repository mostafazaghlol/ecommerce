package com.myshopp.androidecommerce.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.myshopp.androidecommerce.activities.MainActivity;
import com.myshopp.androidecommerce.R;

import java.util.List;

import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.fragments.News_of_Category;
import com.myshopp.androidecommerce.models.news_model.news_categories.NewsCategoryDetails;


/**
 * NewsCategoriesAdapter is the adapter class of RecyclerView holding List of News Categories in NewsCategories Fragment
 **/

public class NewsCategoriesAdapter extends RecyclerView.Adapter<NewsCategoriesAdapter.MyViewHolder> {

    Context context;
    private List<NewsCategoryDetails> newsCategoriesList;


    public NewsCategoriesAdapter(Context context, List<NewsCategoryDetails> newsCategoriesList) {
        this.context = context;
        this.newsCategoriesList = newsCategoriesList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news_categories, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final NewsCategoryDetails newsCategory = newsCategoriesList.get(position);
    
        holder.category_title.setText(newsCategory.getName());
    
        // Set Category Image on ImageView with Glide Library
        Glide
            .with(context)
            .load(ConstantValues.ECOMMERCE_URL+newsCategory.getImage())
            .error(R.drawable.placeholder)
            .into(holder.category_image);
        
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return newsCategoriesList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
        RelativeLayout category_card;
        ImageView category_image;
        TextView category_title, category_products;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_card = (RelativeLayout) itemView.findViewById(R.id.category_card);
            category_image = (ImageView) itemView.findViewById(R.id.category_image);
            category_title = (TextView) itemView.findViewById(R.id.category_title);
            category_products = (TextView) itemView.findViewById(R.id.category_products);

            category_products.setVisibility(View.GONE);

            category_card.setOnClickListener(this);
        }


        // Handle Click Listener on Category item
        @Override
        public void onClick(View view) {
            // Get NewsCategory Info
            Bundle newsCategoryInfo = new Bundle();
            newsCategoryInfo.putInt("NewsCategoryID", Integer.parseInt(newsCategoriesList.get(getAdapterPosition()).getId()));
            newsCategoryInfo.putString("NewsCategoryName", newsCategoriesList.get(getAdapterPosition()).getName());

            Fragment categoryNews = new News_of_Category();
            categoryNews.setArguments(newsCategoryInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, categoryNews)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

}

