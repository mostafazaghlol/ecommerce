package com.myshopp.androidecommerce.adapters;

import android.content.Context;
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
import android.widget.TextView;

import com.myshopp.androidecommerce.activities.MainActivity;
import com.myshopp.androidecommerce.R;

import java.util.List;

import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.fragments.Order_Details;
import com.myshopp.androidecommerce.models.order_model.OrderDetails;


/**
 * OrdersListAdapter is the adapter class of RecyclerView holding List of Orders in My_Orders
 **/

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {

    Context context;
    String customerID;
    List<OrderDetails> ordersList;


    public OrdersListAdapter(Context context, String customerID, List<OrderDetails> ordersList) {
        this.context = context;
        this.customerID = customerID;
        this.ordersList = ordersList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_orders, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final OrderDetails orderDetails = ordersList.get(position);
    
        int noOfProducts = 0;
        for (int i=0;  i<orderDetails.getProducts().size();  i++) {
            // Count no of Products
            noOfProducts += orderDetails.getProducts().get(i).getProductsQuantity();
        }

        holder.order_id.setText(String.valueOf(orderDetails.getOrdersId()));
        holder.order_status.setText(orderDetails.getOrdersStatus());
        holder.order_price.setText(ConstantValues.CURRENCY_SYMBOL + orderDetails.getOrderPrice());
        holder.order_date.setText(orderDetails.getDatePurchased());
        holder.order_product_count.setText(String.valueOf(noOfProducts));
    

        // Check Order's status
        if (orderDetails.getOrdersStatus().equalsIgnoreCase("Pending")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentBlue));
        } else if (orderDetails.getOrdersStatus().equalsIgnoreCase("Completed")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentGreen));
        } else {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentRed));
        }
    
        
    
        holder.order_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Order Info
                Bundle itemInfo = new Bundle();
                itemInfo.putParcelable("orderDetails", orderDetails);
    
                // Navigate to Order_Details Fragment
                Fragment fragment = new Order_Details();
                fragment.setArguments(itemInfo);
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return ordersList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private Button order_view_btn;
        private TextView order_id, order_product_count, order_status, order_price, order_date;


        public MyViewHolder(final View itemView) {
            super(itemView);
    
            order_view_btn = (Button) itemView.findViewById(R.id.order_view_btn);
            order_id = (TextView) itemView.findViewById(R.id.order_id);
            order_product_count = (TextView) itemView.findViewById(R.id.order_products_count);
            order_status = (TextView) itemView.findViewById(R.id.order_status);
            order_price = (TextView) itemView.findViewById(R.id.order_price);
            order_date = (TextView) itemView.findViewById(R.id.order_date);
        }
    }
}

