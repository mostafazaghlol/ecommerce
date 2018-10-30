package com.myshopp.androidecommerce.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.myshopp.androidecommerce.R;

import java.util.ArrayList;

import java.util.List;

import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.filter_model.get_filters.FilterData;
import com.myshopp.androidecommerce.models.filter_model.get_filters.FilterDetails;
import com.myshopp.androidecommerce.models.product_model.GetAllProducts;
import com.myshopp.androidecommerce.customs.EndlessRecyclerViewScroll;
import com.myshopp.androidecommerce.adapters.ProductAdapter;
import com.myshopp.androidecommerce.customs.FilterDialog;
import com.myshopp.androidecommerce.models.filter_model.post_filters.PostFilterData;
import com.myshopp.androidecommerce.models.product_model.ProductData;
import com.myshopp.androidecommerce.models.product_model.ProductDetails;
import com.myshopp.androidecommerce.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;


public class Category_Products extends Fragment {

    View rootView;
    
    int pageNo = 0;
    double maxPrice = 0;
    boolean isVisible;
    boolean isGridView;
    boolean isFilterApplied;
    
    int categoryID;
    String customerID;
    String sortBy = "Newest";

    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    TextView sortListText;
    Button resetFiltersBtn;
    ProgressBar progressBar;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView category_products_recycler;
    
    LoadMoreTask loadMoreTask;
    FilterDialog filterDialog;
    PostFilterData filters = null;

    ProductAdapter productAdapter;
    List<ProductDetails> categoryProductsList;
    List<FilterDetails> filtersList = new ArrayList<>();

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    
    
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    
        isVisible = isVisibleToUser;
    }
    
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_products_vertical, container, false);

        
        // Get CategoryID from bundle arguments
        categoryID = getArguments().getInt("CategoryID");

        // Get sortBy from bundle arguments
        if (getArguments().containsKey("sortBy")) {
            sortBy = getArguments().getString("sortBy");
        }
        

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        bottomBar = (LinearLayout) rootView.findViewById(R.id.bottomBar);
        sortList = (LinearLayout) rootView.findViewById(R.id.sort_list);
        sortListText = (TextView) rootView.findViewById(R.id.sort_text);
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loading_bar);
        resetFiltersBtn = (Button) rootView.findViewById(R.id.resetFiltersBtn);
        filterButton = (ToggleButton) rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = (ToggleButton) rootView.findViewById(R.id.layout_toggleBtn);
        category_products_recycler = (RecyclerView) rootView.findViewById(R.id.products_recycler);


        // Hide some of the Views
        progressBar.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        resetFiltersBtn.setVisibility(View.GONE);
        
        
        isGridView = true;
        isFilterApplied = false;
        filterButton.setChecked(isFilterApplied);
        toggleLayoutView.setChecked(isGridView);
    
    
        // Set sortListText text
        if (sortBy.equalsIgnoreCase("top seller")) {
            sortListText.setText(getString(R.string.top_seller));
        } else if (sortBy.equalsIgnoreCase("special")) {
            sortListText.setText(getString(R.string.super_deals));
        } else if (sortBy.equalsIgnoreCase("most liked")) {
            sortListText.setText(getString(R.string.most_liked));
        } else {
            sortListText.setText(getString(R.string.newest));
        }


        // Initialize CategoryProductsList
        categoryProductsList = new ArrayList<>();
    
    
        // Request for Products of given Category based on PageNo.
        RequestCategoryProducts(pageNo, sortBy);
    
        // Request for Filters of given Category
        RequestFilters(categoryID);


        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), categoryProductsList, false);

        
        setRecyclerViewLayoutManager(isGridView);
        category_products_recycler.setAdapter(productAdapter);



        // Handle the Scroll event of Product's RecyclerView
        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
            @Override
            public void onLoadMore(final int current_page) {
                
                progressBar.setVisibility(View.VISIBLE);

                if (isFilterApplied) {
                    // Initialize LoadMoreTask to Load More Products from Server against some Filters
                    loadMoreTask = new LoadMoreTask(current_page, filters);
                } else {
                    // Initialize LoadMoreTask to Load More Products from Server without Filters
                    loadMoreTask = new LoadMoreTask(current_page, filters);
                }

                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                loadMoreTask.execute();
            }
        });

        productAdapter.notifyDataSetChanged();
    
    
        // Toggle RecyclerView's LayoutManager
        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGridView = isChecked;
                setRecyclerViewLayoutManager(isGridView);
            }
        });


        // Initialize FilterDialog and Override its abstract methods
        filterDialog = new FilterDialog(getContext(), categoryID, filtersList, maxPrice) {
            @Override
            public void clearFilters() {
                // Clear Filters
                isFilterApplied = false;
                filterButton.setChecked(false);
                filters = null;
                categoryProductsList.clear();
                new LoadMoreTask(pageNo, filters).execute();
            }

            @Override
            public void applyFilters(PostFilterData postFilterData) {
                // Apply Filters
                isFilterApplied = true;
                filterButton.setChecked(true);
                filters = postFilterData;
                categoryProductsList.clear();
                new LoadMoreTask(pageNo, filters).execute();
            }
        };


        // Handle the Click event of Filter Button
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFilterApplied) {
                    filterButton.setChecked(true);
                    filterDialog.show();

                } else {
                    filterButton.setChecked(false);
                    filterDialog = new FilterDialog(getContext(), categoryID, filtersList, maxPrice) {
                        @Override
                        public void clearFilters() {
                            isFilterApplied = false;
                            filterButton.setChecked(false);
                            filters = null;
                            categoryProductsList.clear();
                            new LoadMoreTask(pageNo, filters).execute();
                        }

                        @Override
                        public void applyFilters(PostFilterData postFilterData) {
                            isFilterApplied = true;
                            filterButton.setChecked(true);
                            filters = postFilterData;
                            categoryProductsList.clear();
                            new LoadMoreTask(pageNo, filters).execute();
                        }
                    };
                    filterDialog.show();
                }
            }
        });



        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                
                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        
                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);
    
                        
                        if (selectedText.equalsIgnoreCase(sortArray[0])) {
                            sortBy = "Newest";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[1])) {
                            sortBy = "a to z";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[2])) {
                            sortBy = "z to a";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[3])) {
                            sortBy = "high to low";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[4])) {
                            sortBy = "low to high";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[5])) {
                            sortBy = "top seller";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[6])) {
                            sortBy = "special";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[7])) {
                            sortBy = "most liked";
                        }
                        else {
                            sortBy = "Newest";
                        }
                        

                        categoryProductsList.clear();
                        if(isFilterApplied){
                            // Initialize LoadMoreTask to Load More Products from Server against some Filters
                            RequestFilteredProducts(pageNo, sortBy, filters);
                        }else {
                            // Initialize LoadMoreTask to Load More Products from Server without Filters
                            RequestCategoryProducts(pageNo, sortBy);
                        }
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
                        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
                            @Override
                            public void onLoadMore(final int current_page) {
                                
                                progressBar.setVisibility(View.VISIBLE);

                                if(isFilterApplied){
                                    // Initialize LoadMoreTask to Load More Products from Server against some Filters
                                    loadMoreTask = new LoadMoreTask(current_page, filters);
                                }else {
                                    // Initialize LoadMoreTask to Load More Products from Server without Filters
                                    loadMoreTask = new LoadMoreTask(current_page, filters);
                                }

                                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                                loadMoreTask.execute();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });


        resetFiltersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilterApplied = false;
                filterButton.setChecked(false);
                filters = null;
                categoryProductsList.clear();
                new LoadMoreTask(pageNo, filters).execute();
            }
        });


        return rootView;
    }
    
    
    
    //*********** Switch RecyclerView's LayoutManager ********//
    
    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;
        
        // If a LayoutManager has already been set, get current Scroll Position
        if (category_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) category_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
    
        productAdapter.toggleLayout(isGridView);
        
        category_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        category_products_recycler.setAdapter(productAdapter);
        
        category_products_recycler.scrollToPosition(scrollPosition);
    }



    //*********** Adds Products returned from the Server to the CategoryProductsList ********//

    private void addCategoryProducts(ProductData productData) {

        // Add Products to CategoryProductsList from the List of ProductData
        for (int i = 0; i < productData.getProductData().size(); i++) {
            ProductDetails productDetails = productData.getProductData().get(i);
            categoryProductsList.add(productDetails);
        }

        productAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on CategoryProductsList's Size
        if (productAdapter.getItemCount() == 0) {
            if (isFilterApplied) {
                resetFiltersBtn.setVisibility(View.VISIBLE);
            }
            emptyRecord.setVisibility(View.VISIBLE);

        } else {
            emptyRecord.setVisibility(View.GONE);
            resetFiltersBtn.setVisibility(View.GONE);
        }
    }



    //*********** Request Products of given Category from the Server based on PageNo. ********//

    public void RequestCategoryProducts(int pageNumber, String sortBy) {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setCategoriesId(String.valueOf(categoryID));
        getAllProducts.setType(sortBy);


        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // Products have been returned. Add Products to the ProductsList
                        addCategoryProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned. Call the method to process some implementations
                        addCategoryProducts(response.body());
                        
                        // Show the Message to the User
                        if (isVisible)
                            Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                        if (isVisible)
                            Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar
                    progressBar.setVisibility(View.GONE);
                    
                }
                else {
                    if (isVisible)
                        Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
                
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (isVisible)
                    Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Request Products of given Category from the Server based on PageNo. against some Filters ********//

    public void RequestFilteredProducts(int pageNumber, String sortBy, PostFilterData postFilterData) {


        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setCategoriesId(String.valueOf(categoryID));
        getAllProducts.setType(sortBy);
        getAllProducts.setPrice(postFilterData.getPrice());
        getAllProducts.setFilters(postFilterData.getFilters());


        Call<ProductData> call = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        // Products have been returned. Add Products to the ProductsList
                        addCategoryProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned. Call the method to process some implementations
                        addCategoryProducts(response.body());
                        
                        // Show the Message to the User
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar
                    progressBar.setVisibility(View.GONE);
                    
                }
                else {
                    Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Request Filters of the given Category ********//

    private void RequestFilters(int categories_id) {

        Call<FilterData> call = APIClient.getInstance()
                .getFilters
                        (
                                categories_id,
                                ConstantValues.LANGUAGE_ID
                        );

        call.enqueue(new Callback<FilterData>() {
            @Override
            public void onResponse(Call<FilterData> call, retrofit2.Response<FilterData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        filtersList = response.body().getFilters();
                        maxPrice = Double.parseDouble(response.body().getMaxPrice());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        if (isVisible)
                            Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        if (isVisible)
                            Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (isVisible)
                        Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FilterData> call, Throwable t) {
                if (isVisible)
                    Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;
        PostFilterData postFilters;


        private LoadMoreTask(int page_number, PostFilterData postFilterData) {
            this.page_number = page_number;
            this.postFilters = postFilterData;
        }


        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Check if any of the Filter is applied
            if (isFilterApplied) {
                // Request for Products against specified Filters, based on PageNo.
                RequestFilteredProducts(page_number, sortBy, postFilters);
            }
            else {
                // Request for Products of given Category, based on PageNo.
                RequestCategoryProducts(page_number, sortBy);
            }

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}