package com.myshopp.androidecommerce.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.myshopp.androidecommerce.R;

import java.util.ArrayList;
import java.util.List;

import com.myshopp.androidecommerce.app.App;
import com.myshopp.androidecommerce.models.category_model.CategoryDetails;

import static com.myshopp.androidecommerce.app.App.getContext;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class CategoryListAdapter_6 extends RecyclerView.Adapter<CategoryListAdapter_6.MyViewHolder> {

    boolean isSubCategory;

    Context context;
    List<CategoryDetails> categoriesList;
    List<CategoryDetails> allCategoriesList;


    public CategoryListAdapter_6(Context context, List<CategoryDetails> categoriesList, boolean isSubCategory) {
        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;

        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories_6, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CategoryDetails categoryDetails = categoriesList.get(position);

        holder.category_title.setText(categoryDetails.getName());
        holder.category_products.setText(categoryDetails.getTotalProducts() + " "+ context.getString(R.string.products));


        List<CategoryDetails> subCategoriesList = new ArrayList<>();

        for (int i=0;  i<allCategoriesList.size();  i++) {
            // Get Subcategories from all Categories
            if (allCategoriesList.get(i).getParentId().equalsIgnoreCase(categoryDetails.getId())) {
                subCategoriesList.add(allCategoriesList.get(i));
            }
        }

        // Initialize the SubCategoryListAdapter for RecyclerView
        SubCategoryListAdapter subCategoryListAdapter = new SubCategoryListAdapter(context, subCategoriesList);

        holder.sub_categories_list.setAdapter(subCategoryListAdapter);

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ListView sub_categories_list;
        TextView category_title, category_products;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_title = (TextView) itemView.findViewById(R.id.category_title);
            category_products = (TextView) itemView.findViewById(R.id.category_products);
            sub_categories_list = (ListView) itemView.findViewById(R.id.sub_categories_list);
        }

    }

}

