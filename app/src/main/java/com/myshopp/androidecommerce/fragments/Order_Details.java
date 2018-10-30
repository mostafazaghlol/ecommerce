package com.myshopp.androidecommerce.fragments;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myshopp.androidecommerce.R;

import java.text.DecimalFormat;
import java.util.List;

import com.myshopp.androidecommerce.adapters.CouponsAdapter;
import com.myshopp.androidecommerce.adapters.OrderedProductsListAdapter;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.coupons_model.CouponsInfo;
import com.myshopp.androidecommerce.models.order_model.OrderDetails;
import com.myshopp.androidecommerce.models.order_model.OrderProducts;
import com.myshopp.androidecommerce.customs.DividerItemDecoration;


public class Order_Details extends Fragment {

    View rootView;
    
    OrderDetails orderDetails;

    CardView buyer_comments_card, seller_comments_card;
    RecyclerView checkout_items_recycler, checkout_coupons_recycler;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address;
    TextView order_price, order_products_count, order_status, order_date, shipping_method, payment_method, buyer_comments, seller_comments;

    List<CouponsInfo> couponsList;
    List<OrderProducts> orderProductsList;

    CouponsAdapter couponsAdapter;
    OrderedProductsListAdapter orderedProductsAdapter;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_details, container, false);
    
    
        // Get orderDetails from bundle arguments
        orderDetails = getArguments().getParcelable("orderDetails");
        
        
        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.order_details));
        

        // Binding Layout Views
        order_price = (TextView) rootView.findViewById(R.id.order_price);
        order_products_count = (TextView) rootView.findViewById(R.id.order_products_count);
        shipping_method = (TextView) rootView.findViewById(R.id.shipping_method);
        payment_method = (TextView) rootView.findViewById(R.id.payment_method);
        order_status = (TextView) rootView.findViewById(R.id.order_status);
        order_date = (TextView) rootView.findViewById(R.id.order_date);
        checkout_subtotal = (TextView) rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = (TextView) rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = (TextView) rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = (TextView) rootView.findViewById(R.id.checkout_discount);
        checkout_total = (TextView) rootView.findViewById(R.id.checkout_total);
        billing_name = (TextView) rootView.findViewById(R.id.billing_name);
        billing_address = (TextView) rootView.findViewById(R.id.billing_address);
        billing_street = (TextView) rootView.findViewById(R.id.billing_street);
        shipping_name = (TextView) rootView.findViewById(R.id.shipping_name);
        shipping_address = (TextView) rootView.findViewById(R.id.shipping_address);
        shipping_street = (TextView) rootView.findViewById(R.id.shipping_street);
        buyer_comments = (TextView) rootView.findViewById(R.id.buyer_comments);
        seller_comments = (TextView) rootView.findViewById(R.id.seller_comments);
        buyer_comments_card = (CardView) rootView.findViewById(R.id.buyer_comments_card);
        seller_comments_card = (CardView) rootView.findViewById(R.id.seller_comments_card);
        checkout_items_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_coupons_recycler);

        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);


        // Set Order Details

        couponsList = orderDetails.getCoupons();
        orderProductsList = orderDetails.getProducts();

        double subTotal = 0;
        int noOfProducts = 0;
        for (int i=0;  i<orderProductsList.size();  i++) {
            subTotal += Double.parseDouble(orderProductsList.get(i).getFinalPrice());
            noOfProducts += orderProductsList.get(i).getProductsQuantity();
        }
        
        
        String orderPrice = ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getOrderPrice()));
        String Tax = ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getTotalTax()));
        String Shipping = ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getShippingCost()));
        String Discount = ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getCouponAmount()));
        String Subtotal = ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(subTotal);
        String Total = ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getOrderPrice()));
    
    
        order_price.setText(orderPrice);
        order_products_count.setText(String.valueOf(noOfProducts));
        shipping_method.setText(orderDetails.getShippingMethod());
        payment_method.setText(orderDetails.getPaymentMethod());
        order_status.setText(orderDetails.getOrdersStatus());
        order_date.setText(orderDetails.getDatePurchased());

        checkout_tax.setText(Tax);
        checkout_shipping.setText(Shipping);
        checkout_discount.setText(Discount);
        checkout_subtotal.setText(Subtotal);
        checkout_total.setText(Total);

        billing_name.setText(orderDetails.getBillingName());
        billing_address.setText(orderDetails.getBillingCity());
        billing_street.setText(orderDetails.getBillingStreetAddress());
        shipping_name.setText(orderDetails.getDeliveryName());
        shipping_address.setText(orderDetails.getDeliveryCity());
        shipping_street.setText(orderDetails.getDeliveryStreetAddress());

        
        if (!orderDetails.getCustomerComments().isEmpty()  &&  !orderDetails.getCustomerComments().equalsIgnoreCase("")) {
            buyer_comments_card.setVisibility(View.VISIBLE);
            buyer_comments.setText(orderDetails.getCustomerComments());
        } else {
            buyer_comments_card.setVisibility(View.GONE);
        }

        if (!orderDetails.getAdminComments().isEmpty()  &&  !orderDetails.getAdminComments().equalsIgnoreCase("")) {
            seller_comments_card.setVisibility(View.VISIBLE);
            seller_comments.setText(orderDetails.getAdminComments());
        } else {
            seller_comments_card.setVisibility(View.GONE);
        }


        couponsAdapter = new CouponsAdapter(getContext(), couponsList, false, null);

        checkout_coupons_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_coupons_recycler.setAdapter(couponsAdapter);

        
        orderedProductsAdapter = new OrderedProductsListAdapter(getContext(), orderProductsList);

        checkout_items_recycler.setAdapter(orderedProductsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_items_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        return rootView;

    }


}



