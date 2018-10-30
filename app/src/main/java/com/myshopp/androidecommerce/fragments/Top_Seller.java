package com.myshopp.androidecommerce.fragments;


import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.myshopp.androidecommerce.R;

import java.util.ArrayList;
import java.util.List;

import com.myshopp.androidecommerce.adapters.ProductAdapter;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.product_model.GetAllProducts;
import com.myshopp.androidecommerce.models.product_model.ProductData;
import com.myshopp.androidecommerce.models.product_model.ProductDetails;
import com.myshopp.androidecommerce.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;


public class Top_Seller extends Fragment {

    String customerID;
    Boolean isHeaderVisible;
    Call<ProductData> networkCall;

    TextView emptyRecord, headerText;
    RecyclerView top_seller_recycler;

    ProductAdapter productAdapter;

    List<ProductDetails> topSellerProductsList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        // Get the Boolean from Bundle Arguments
        isHeaderVisible = getArguments().getBoolean("isHeaderVisible");

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.products_horizontal_header);
        top_seller_recycler = (RecyclerView) rootView.findViewById(R.id.products_horizontal_recycler);
    
        
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
    
        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.top_seller));
        }
    
    
        topSellerProductsList = new ArrayList<>();
        

        // RecyclerView has fixed Size
        top_seller_recycler.setHasFixedSize(true);
        top_seller_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    
        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), topSellerProductsList, true);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        top_seller_recycler.setAdapter(productAdapter);
        

        // Request for Most Sold Products
        RequestTopSellerProducts();


        return rootView;
    }



    //*********** Adds Products returned from the Server to the topSellerProductsList ********//

    private void addProducts(ProductData productData) {

        // Add Products to topSellerProductsList
        topSellerProductsList.addAll(productData.getProductData());

        productAdapter.notifyDataSetChanged();
    }



    //*********** Request all the Products from the Server based on the Sales of Products ********//

    public void RequestTopSellerProducts() {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setType("top seller");

        networkCall = APIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        networkCall.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {

                    // Check the Success status
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Products have been returned. Add Products to the topSellerProductsList
                        emptyRecord.setVisibility(View.GONE);
                        addProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned
                        emptyRecord.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (!networkCall.isCanceled()) {
                    Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    @Override
    public void onPause() {

        // Check if NetworkCall is being executed
        if (networkCall.isExecuted()){
            // Cancel the NetworkCall
            networkCall.cancel();
        }

        super.onPause();
    }
}