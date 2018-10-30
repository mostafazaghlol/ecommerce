package com.myshopp.androidecommerce.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.BraintreeRequestCodes;
import com.braintreepayments.api.models.CardBuilder;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.cardform.view.SupportedCardTypesView;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.*;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.exceptions.BraintreeError;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.cardform.utils.CardType;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.model.Token;

import com.myshopp.androidecommerce.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.myshopp.androidecommerce.activities.MainActivity;
import com.myshopp.androidecommerce.adapters.DemoCouponsListAdapter;
import com.myshopp.androidecommerce.app.App;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.customs.DialogLoader;
import com.myshopp.androidecommerce.databases.User_Cart_DB;
import com.myshopp.androidecommerce.databases.User_Info_DB;
import com.myshopp.androidecommerce.adapters.CouponsAdapter;
import com.myshopp.androidecommerce.adapters.CheckoutItemsAdapter;
import com.myshopp.androidecommerce.models.address_model.AddressDetails;
import com.myshopp.androidecommerce.models.cart_model.CartProduct;
import com.myshopp.androidecommerce.models.cart_model.CartProductAttributes;
import com.myshopp.androidecommerce.models.coupons_model.CouponsData;
import com.myshopp.androidecommerce.models.coupons_model.CouponsInfo;
import com.myshopp.androidecommerce.models.order_model.OrderData;
import com.myshopp.androidecommerce.models.payment_model.GetBrainTreeToken;
import com.myshopp.androidecommerce.models.payment_model.PaymentMethodsData;
import com.myshopp.androidecommerce.models.payment_model.PaymentMethodsInfo;
import com.myshopp.androidecommerce.models.order_model.PostOrder;
import com.myshopp.androidecommerce.models.order_model.PostProductsAttributes;
import com.myshopp.androidecommerce.models.order_model.PostProducts;
import com.myshopp.androidecommerce.models.product_model.Option;
import com.myshopp.androidecommerce.models.product_model.Value;
import com.myshopp.androidecommerce.models.shipping_model.ShippingService;
import com.myshopp.androidecommerce.models.user_model.UserDetails;
import com.myshopp.androidecommerce.customs.DividerItemDecoration;
import com.myshopp.androidecommerce.network.APIClient;
import com.myshopp.androidecommerce.utils.NotificationHelper;
import com.myshopp.androidecommerce.utils.ValidateInputs;
import com.myshopp.androidecommerce.utils.Utilities;

import retrofit2.Callback;
import retrofit2.Call;


public class Checkout extends Fragment {

    View rootView;
    AlertDialog demoCouponsDialog;
    boolean disableOtherCoupons = false;
    
    String tax;
    String braintreeToken;
    String selectedPaymentMethod;
    String paymentNonceToken = "";
    double checkoutSubtotal, checkoutTax, checkoutShipping, checkoutShippingCost, checkoutDiscount, checkoutTotal = 0;
    
    Button checkout_paypal_btn;
    CardView card_details_layout;
    ProgressDialog progressDialog;
    NestedScrollView scroll_container;
    RecyclerView checkout_items_recycler;
    RecyclerView checkout_coupons_recycler;
    Button checkout_coupon_btn, checkout_order_btn, checkout_cancel_btn;
    ImageButton edit_billing_Btn, edit_shipping_Btn, edit_shipping_method_Btn;
    EditText checkout_coupon_code, checkout_comments, checkout_card_number, checkout_card_cvv, checkout_card_expiry;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total, demo_coupons_text;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address, shipping_method, payment_method;
    
    
    List<CouponsInfo> couponsList;
    List<String> paymentMethodsList;
    List<CartProduct> checkoutItemsList;
    
    UserDetails userInfo;
    DialogLoader dialogLoader;
    AddressDetails billingAddress;
    AddressDetails shippingAddress;
    CouponsAdapter couponsAdapter;
    ShippingService shippingMethod;
    CheckoutItemsAdapter checkoutItemsAdapter;
    
    User_Cart_DB user_cart_db = new User_Cart_DB();
    User_Info_DB user_info_db = new User_Info_DB();
    
    
    CardBuilder braintreeCard;
    BraintreeFragment braintreeFragment;
    com.stripe.android.model.Card stripeCard;

    private String PAYMENT_CURRENCY = "USD";
    private String STRIPE_PUBLISHABLE_KEY = "";
    private String PAYPAL_PUBLISHABLE_KEY = "";
    
    private static PayPalConfiguration payPalConfiguration;
    private static final int SIMPLE_PAYPAL_REQUEST_CODE = 123;
    
    
    CardType cardType;
    SupportedCardTypesView braintreeSupportedCards;

    private static final CardType[] SUPPORTED_CARD_TYPES = { CardType.VISA, CardType.MASTERCARD, CardType.MAESTRO,
                                                             CardType.UNIONPAY, CardType.AMEX};
    


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.checkout, container, false);

        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.checkout));

        // Get selectedShippingMethod, billingAddress and shippingAddress from ApplicationContext
        tax = ((App) getContext().getApplicationContext()).getTax();
        shippingMethod = ((App) getContext().getApplicationContext()).getShippingService();
        billingAddress = ((App) getContext().getApplicationContext()).getBillingAddress();
        shippingAddress = ((App) getContext().getApplicationContext()).getShippingAddress();

        // Get userInfo from Local Databases User_Info_DB
        userInfo = user_info_db.getUserData(getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", null));


        // Binding Layout Views
        checkout_order_btn = (Button) rootView.findViewById(R.id.checkout_order_btn);
        checkout_cancel_btn = (Button) rootView.findViewById(R.id.checkout_cancel_btn);
        checkout_coupon_btn = (Button) rootView.findViewById(R.id.checkout_coupon_btn);
        edit_billing_Btn = (ImageButton) rootView.findViewById(R.id.checkout_edit_billing);
        edit_shipping_Btn = (ImageButton) rootView.findViewById(R.id.checkout_edit_shipping);
        edit_shipping_method_Btn = (ImageButton) rootView.findViewById(R.id.checkout_edit_shipping_method);
        shipping_method = (TextView) rootView.findViewById(R.id.shipping_method);
        payment_method = (TextView) rootView.findViewById(R.id.payment_method);
        checkout_subtotal = (TextView) rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = (TextView) rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = (TextView) rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = (TextView) rootView.findViewById(R.id.checkout_discount);
        checkout_total = (TextView) rootView.findViewById(R.id.checkout_total);
        shipping_name = (TextView) rootView.findViewById(R.id.shipping_name);
        shipping_street = (TextView) rootView.findViewById(R.id.shipping_street);
        shipping_address = (TextView) rootView.findViewById(R.id.shipping_address);
        billing_name = (TextView) rootView.findViewById(R.id.billing_name);
        billing_street = (TextView) rootView.findViewById(R.id.billing_street);
        billing_address = (TextView) rootView.findViewById(R.id.billing_address);
        demo_coupons_text = (TextView) rootView.findViewById(R.id.demo_coupons_text);
        checkout_coupon_code = (EditText) rootView.findViewById(R.id.checkout_coupon_code);
        checkout_comments = (EditText) rootView.findViewById(R.id.checkout_comments);
        checkout_items_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_coupons_recycler);

        card_details_layout = (CardView) rootView.findViewById(R.id.card_details_layout);
        checkout_paypal_btn = (Button) rootView.findViewById(R.id.checkout_paypal_btn);
        checkout_card_number = (EditText) rootView.findViewById(R.id.checkout_card_number);
        checkout_card_cvv = (EditText) rootView.findViewById(R.id.checkout_card_cvv);
        checkout_card_expiry = (EditText) rootView.findViewById(R.id.checkout_card_expiry);
        scroll_container = (NestedScrollView) rootView.findViewById(R.id.scroll_container);
        braintreeSupportedCards = (SupportedCardTypesView) rootView.findViewById(R.id.supported_card_types);


        braintreeSupportedCards.setSupportedCardTypes(SUPPORTED_CARD_TYPES);
    
        checkout_order_btn.setEnabled(false);
        card_details_layout.setVisibility(View.GONE);
        checkout_paypal_btn.setVisibility(View.GONE);


        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);

        checkout_card_expiry.setKeyListener(null);

        
        dialogLoader = new DialogLoader(getContext());
    
        
        checkoutItemsList = new ArrayList<>();
        paymentMethodsList = new ArrayList<>();
        couponsList = new ArrayList<>();
        
        // Get checkoutItems from Local Databases User_Cart_DB
        checkoutItemsList = user_cart_db.getCartItems();

        
        // Request Payment Methods
        RequestPaymentMethods();


        // Initialize the CheckoutItemsAdapter for RecyclerView
        checkoutItemsAdapter = new CheckoutItemsAdapter(getContext(), checkoutItemsList);

        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        checkout_items_recycler.setAdapter(checkoutItemsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_items_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    
    
    
        // Initialize the CouponsAdapter for RecyclerView
        couponsAdapter = new CouponsAdapter(getContext(), couponsList, true, Checkout.this);
    
        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        checkout_coupons_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_coupons_recycler.setAdapter(couponsAdapter);

        couponsAdapter.notifyDataSetChanged();



        checkoutTax = Double.parseDouble(tax);
        shipping_method.setText(shippingMethod.getName() + " ("+ shippingMethod.getShippingMethod() +")");
        checkoutShipping = checkoutShippingCost = Double.parseDouble(shippingMethod.getRate());
    
        // Set Billing Details
        shipping_name.setText(shippingAddress.getFirstname()+" "+shippingAddress.getLastname());
        shipping_address.setText(shippingAddress.getZoneName()+", "+shippingAddress.getCountryName());
        shipping_street.setText(shippingAddress.getStreet());

        // Set Billing Details
        billing_name.setText(billingAddress.getFirstname()+" "+billingAddress.getLastname());
        billing_address.setText(billingAddress.getZoneName()+", "+billingAddress.getCountryName());
        billing_street.setText(billingAddress.getStreet());


        // Set Checkout Total
        setCheckoutTotal();


        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.processing));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
    
    
    
        // Handle the Click event of edit_payment_method_Btn
        payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
                final ArrayAdapter paymentAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                paymentAdapter.addAll(paymentMethodsList);
            
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
            
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
            
                dialog_button.setVisibility(View.GONE);
            
                dialog_title.setText(getString(R.string.payment_method));
                dialog_list.setAdapter(paymentAdapter);
            
            
                final AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            
            
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    
                        String userSelectedPaymentMethod = paymentAdapter.getItem(position).toString();
                    
                        payment_method.setText(userSelectedPaymentMethod);
                        checkout_order_btn.setEnabled(true);
                        checkout_order_btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccentGreen));
                    
                        // Check the selected Payment Method
                        switch (userSelectedPaymentMethod) {
                        
                            // Change the Visibility of some Views based on selected Payment Method
                            case "Cash On Delivery":
                                checkout_paypal_btn.setVisibility(View.GONE);
                                card_details_layout.setVisibility(View.GONE);
                                selectedPaymentMethod = "cash_on_delivery";
                                break;
                                
                            case "PayPal":
                                checkout_paypal_btn.setVisibility(View.VISIBLE);
                                card_details_layout.setVisibility(View.GONE);
                                selectedPaymentMethod = "simplePaypal";
                                break;
                                
                            case "Stripe Credit Card":
                                checkout_paypal_btn.setVisibility(View.GONE);
                                card_details_layout.setVisibility(View.VISIBLE);
                                selectedPaymentMethod = "stripe";
                            
                                checkout_card_number.setText("4242424242424242");
                                checkout_card_cvv.setText("123");
                                checkout_card_expiry.setText("12/2018");
                                break;
                                
                            case "Braintree Credit Card":
                                checkout_paypal_btn.setVisibility(View.GONE);
                                card_details_layout.setVisibility(View.VISIBLE);
                                selectedPaymentMethod = "card_payment";
                            
                                checkout_card_number.setText("5555555555554444");
                                checkout_card_cvv.setText("123");
                                checkout_card_expiry.setText("12/2018");
                                break;
                                
                            case "Braintree PayPal":
                                checkout_paypal_btn.setVisibility(View.VISIBLE);
                                card_details_layout.setVisibility(View.GONE);
                                selectedPaymentMethod = "paypal";
                                break;
                                
                            default:
                                checkout_paypal_btn.setVisibility(View.GONE);
                                card_details_layout.setVisibility(View.GONE);
                                selectedPaymentMethod = "cash_on_delivery";
                                break;
                        }
                    
                        scroll_container.post(new Runnable() {
                            @Override
                            public void run() {
                                scroll_container.fullScroll(scroll_container.FOCUS_DOWN);
//                                scroll_container.scrollTo(0, scroll_container.getBottom());
                            }
                        });
                    
                        alertDialog.dismiss();
                    }
                });
            }
        });
        
        
        // Integrate SupportedCardTypes with TextChangedListener of checkout_card_number
        checkout_card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!checkout_card_number.getText().toString().trim().isEmpty()) {
                    CardType type = CardType.forCardNumber(checkout_card_number.getText().toString());
                    if (cardType != type) {
                        cardType = type;
                    
                        InputFilter[] filters = { new InputFilter.LengthFilter(cardType.getMaxCardLength()) };
                        checkout_card_number.setFilters(filters);
                        checkout_card_number.invalidate();
                    
                        braintreeSupportedCards.setSelected(cardType);
                    }
                } else {
                    braintreeSupportedCards.setSupportedCardTypes(SUPPORTED_CARD_TYPES);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        
        // Handle Touch event of input_dob EditText
        checkout_card_expiry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Get Calendar instance
                    final Calendar calendar = Calendar.getInstance();

                    // Initialize DateSetListener of DatePickerDialog
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            // Set the selected Date Info to Calendar instance
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);

                            // Set Date Format
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);

                            // Set Date in input_dob EditText
                            checkout_card_expiry.setText(dateFormat.format(calendar.getTime()));
                        }
                    };


                    // Initialize DatePickerDialog
                    DatePickerDialog datePicker = new DatePickerDialog
                            (
                                    getContext(),
                                    date,
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                            );

                    // Show datePicker Dialog
                    datePicker.show();
                }

                return false;
            }
        });


        // Handle the Click event of checkout_paypal_btn Button
        checkout_paypal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (selectedPaymentMethod.equalsIgnoreCase("paypal")) {
                    // Process Payment using Braintree PayPal
                    PayPal.authorizeAccount(braintreeFragment);
                }
                else if (selectedPaymentMethod.equalsIgnoreCase("simplePaypal")) {
                    // Process Payment using PayPal
                    PayPalPayment payment = new PayPalPayment
                        (
                            new BigDecimal(String.valueOf(checkoutTotal)),
                            PAYMENT_CURRENCY,
                            ConstantValues.APP_HEADER,
                            PayPalPayment.PAYMENT_INTENT_SALE
                        );
    
                    Intent intent = new Intent(getContext(), PaymentActivity.class);
    
                    // send the same configuration for restart resiliency
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
    
                    startActivityForResult(intent, SIMPLE_PAYPAL_REQUEST_CODE);
                }
            }
        });



        // Handle the Click event of edit_billing_Btn Button
        edit_billing_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Navigate to Billing_Address Fragment to Edit BillingAddress
                Fragment fragment = new Billing_Address();
                Bundle args = new Bundle();
                args.putBoolean("isUpdate", true);
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });


        // Handle the Click event of edit_shipping_Btn Button
        edit_shipping_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Navigate to Shipping_Address Fragment to Edit ShippingAddress
                Fragment fragment = new Shipping_Address();
                Bundle args = new Bundle();
                args.putBoolean("isUpdate", true);
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });


        // Handle the Click event of edit_shipping_method_Btn Button
        edit_shipping_method_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Navigate to Shipping_Methods Fragment to Edit ShippingMethod
                Fragment fragment = new Shipping_Methods();
                Bundle args = new Bundle();
                args.putBoolean("isUpdate", true);
                fragment.setArguments(args);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });
    
        

        if (!ConstantValues.IS_CLIENT_ACTIVE) {
            setupDemoCoupons();
        }
        else {
            demo_coupons_text.setVisibility(View.GONE);
        }
        

        // Handle the Click event of checkout_coupon_btn Button
        checkout_coupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkout_coupon_code.getText().toString().isEmpty()) {
                    GetCouponInfo(checkout_coupon_code.getText().toString());
                    dialogLoader.showProgressDialog();
                }
            }
        });


        // Handle the Click event of checkout_cancel_btn Button
        checkout_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cancel the Order and Navigate back to My_Cart Fragment
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack(getString(R.string.actionCart), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });


        // Handle the Click event of checkout_order_btn Button
        checkout_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Check if the selectedPaymentMethod is not "Cash_on_Delivery"
                if (!selectedPaymentMethod.equalsIgnoreCase("cash_on_delivery")) {

                    // Check if the selectedPaymentMethod is not "PayPal" or "Braintree PayPal"
                    if (!selectedPaymentMethod.equalsIgnoreCase("paypal")
                        &&  !selectedPaymentMethod.equalsIgnoreCase("simplePaypal"))
                    {

                        if (validatePaymentCard()) {
                            // Setup Payment Method
                            validateSelectedPaymentMethod();
                            progressDialog.show();

                            // Delay of 2 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (!"".equalsIgnoreCase(paymentNonceToken)) {
                                        // Proceed Order
                                        proceedOrder();
                                    } else {
                                        progressDialog.dismiss();
                                        Snackbar.make(view, getString(R.string.invalid_payment_token), Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            }, 2000);
                        }

                    }
                    else {
                        // Setup Payment Method
                        validateSelectedPaymentMethod();
                        progressDialog.show();

                        // Delay of 2 seconds
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!"".equalsIgnoreCase(paymentNonceToken)) {
                                    // Proceed Order
                                    proceedOrder();
                                }
                                else {
                                    progressDialog.dismiss();
                                    Snackbar.make(view, getString(R.string.invalid_payment_token), Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }, 2000);
                    }
                }
                else {
                    // Proceed Order
                    proceedOrder();
                    progressDialog.show();
                }
            }
        });


        return rootView;
    }
    
    
    
    //*********** Called when the fragment is no longer in use ********//
    
    @Override
    public void onDestroy() {
        getContext().stopService(new Intent(getContext(), PayPalService.class));
        super.onDestroy();
    }
    
    
    
    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            
            if (requestCode == BraintreeRequestCodes.PAYPAL) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                
                // use the result to update your UI and send the payment method nonce to your server
                if (!result.getPaymentMethodNonce().getNonce().isEmpty()) {
                    selectedPaymentMethod = "paypal";
                    paymentNonceToken = result.getPaymentMethodNonce().getNonce();
                }
                
            }
            else if (requestCode == SIMPLE_PAYPAL_REQUEST_CODE) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                
                if (confirm != null) {
                    selectedPaymentMethod = "simplePaypal";
                    paymentNonceToken = confirm.getProofOfPayment().getPaymentId();
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paypal", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paypal", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
    


    //*********** Validate Payment method Details according to the selectedPaymentMethod ********//

    private void validateSelectedPaymentMethod() {

        // Check if the selectedPaymentMethod is Braintree's card_payment
        if (selectedPaymentMethod.equalsIgnoreCase("card_payment")) {

            // Initialize BraintreeCard
            braintreeCard = new CardBuilder()
                    .cardNumber(checkout_card_number.getText().toString().trim())
                    .expirationDate(checkout_card_expiry.getText().toString().trim())
                    .cvv(checkout_card_cvv.getText().toString().trim());

            // Tokenize BraintreeCard
            Card.tokenize(braintreeFragment, braintreeCard);


            // Add PaymentMethodNonceCreatedListener to BraintreeFragment
            braintreeFragment.addListener(new PaymentMethodNonceCreatedListener() {
                @Override
                public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {

                    // Get Payment Nonce
                    paymentNonceToken = paymentMethodNonce.getNonce();
                }
            });


            // Add BraintreeErrorListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeErrorListener() {
                @Override
                public void onError(Exception error) {

                    // Check if there was a Validation Error of provided Data
                    if (error instanceof ErrorWithResponse) {
                        ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;

                        BraintreeError cardNumberError = errorWithResponse.errorFor("number");
                        BraintreeError cardCVVErrors = errorWithResponse.errorFor("creditCard");
                        BraintreeError expirationMonthError = errorWithResponse.errorFor("expirationMonth");
                        BraintreeError expirationYearError = errorWithResponse.errorFor("expirationYear");

                        // Check if there is an Issue with the Credit Card
                        if (cardNumberError != null) {
                            checkout_card_number.setError(cardNumberError.getMessage());
                        }
                        else if (expirationMonthError != null) {
                            checkout_card_expiry.setError(expirationMonthError.getMessage());
                        }
                        else if (expirationYearError != null) {
                            checkout_card_expiry.setError(expirationYearError.getMessage());
                        }
                        else if (cardCVVErrors != null) {
                            checkout_card_cvv.setError(cardCVVErrors.getMessage());
                        }
                        else {
                            Toast.makeText(getContext(), errorWithResponse.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            // Add ConfigurationListener to BraintreeFragment
            braintreeFragment.addListener(new ConfigurationListener() {
                @Override
                public void onConfigurationFetched(Configuration configuration) {}
            });

            // Add BraintreeCancelListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeCancelListener() {
                @Override
                public void onCancel(int requestCode) {}
            });


        }
        // Check if the selectedPaymentMethod is Stripe's card_payment
        else if (selectedPaymentMethod.equalsIgnoreCase("stripe")) {

            String[] expiryDate = checkout_card_expiry.getText().toString().split("/");

            // Initialize StripeCard
            stripeCard = new com.stripe.android.model.Card
                    (
                            checkout_card_number.getText().toString().trim(),
                            Integer.valueOf(expiryDate[0]),
                            Integer.valueOf(expiryDate[1]),
                            checkout_card_cvv.getText().toString().trim()
                    );

            Stripe stripe = null;

            if (stripeCard.validateCard()) {
                try {
                    // Initialize Stripe with Stripe API Key
                    stripe = new Stripe(STRIPE_PUBLISHABLE_KEY);

                    // Create Token of the StripeCard
                    stripe.createToken(
                            stripeCard,
                            new TokenCallback() {

                                // Handle onSuccess Callback
                                public void onSuccess(Token token) {

                                    // Get Payment Nonce
                                    paymentNonceToken = token.getId();
                                }

                                // Handle onError Callback
                                public void onError(Exception error) {

                                    // Check if there is an Issue with the Credit Card
                                    if (!stripeCard.validateNumber()) {
                                        checkout_card_number.setError(getString(R.string.invalid_credit_card));
                                    } else if (!stripeCard.validateExpiryDate()) {
                                        checkout_card_expiry.setError(getString(R.string.expired_card));
                                    } else if (!stripeCard.validateCVC()) {
                                        checkout_card_cvv.setError(getString(R.string.invalid_card_cvv));
                                    } else {
                                        checkout_card_number.setError(getString(R.string.invalid_credit_card));
                                    }
                                }
                            }
                    );
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                }

            } else if (!stripeCard.validateNumber()) {
                checkout_card_number.setError(getString(R.string.invalid_credit_card));
            } else if (!stripeCard.validateExpiryDate()) {
                checkout_card_expiry.setError(getString(R.string.expired_card));
            } else if (!stripeCard.validateCVC()) {
                checkout_card_cvv.setError(getString(R.string.invalid_card_cvv));
            } else {
                checkout_card_number.setError(getString(R.string.invalid_credit_card));
            }


        }
        // Check if the selectedPaymentMethod is Braintree's PayPal
        else if (selectedPaymentMethod.equalsIgnoreCase("paypal")) {

            // Add PaymentMethodNonceCreatedListener to BraintreeFragment
            braintreeFragment.addListener(new PaymentMethodNonceCreatedListener() {
                @Override
                public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {

                    // Get Payment Nonce
                    paymentNonceToken = paymentMethodNonce.getNonce();
                    selectedPaymentMethod = "paypal";
                }
            });
    
            // Add BraintreeErrorListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeErrorListener() {
                @Override
                public void onError(Exception error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            // Add BraintreeCancelListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeCancelListener() {
                @Override
                public void onCancel(int requestCode) {}
            });

        }
        // Check if the selectedPaymentMethod is Braintree's PayPal
        else if (selectedPaymentMethod.equalsIgnoreCase("simplePaypal")) {
    
            // Add PaymentMethodNonceCreatedListener to BraintreeFragment
            braintreeFragment.addListener(new PaymentMethodNonceCreatedListener() {
                @Override
                public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
            
                    // Get Payment Nonce
                    paymentNonceToken = paymentMethodNonce.getNonce();
                    selectedPaymentMethod = "simplePaypal";
                }
            });
    
            // Add BraintreeErrorListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeErrorListener() {
                @Override
                public void onError(Exception error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    
            // Add BraintreeCancelListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeCancelListener() {
                @Override
                public void onCancel(int requestCode) {}
            });
    
        }
        else {
            return;
        }
    }


    //*********** Returns Final Price of User's Cart ********//

    private double getProductsSubTotal() {

        double finalPrice = 0;

        for (int i=0;  i<checkoutItemsList.size();  i++) {
            // Add the Price of each Cart Product to finalPrice
            finalPrice += Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice());
        }

        return finalPrice;
    }



    //*********** Set Checkout's Subtotal, Tax, ShippingCost, Discount and Total Prices ********//

    private void setCheckoutTotal() {

        // Get Cart Total
        checkoutSubtotal = getProductsSubTotal();
        // Calculate Checkout Total
        checkoutTotal = checkoutSubtotal + checkoutTax + checkoutShipping - checkoutDiscount;
    
//        checkoutTax = (double) Math.round(checkoutTax * 100) / 100;
//        checkoutShipping = (double) Math.round(checkoutShipping * 100) / 100 ;
//        checkoutDiscount = (double) Math.round(checkoutDiscount * 100) / 100 ;
//        checkoutSubtotal = (double) Math.round(checkoutSubtotal * 100) / 100 ;
//        checkoutTotal = (double) Math.round(checkoutTotal * 100) / 100;
    
    
        // Set Checkout Details
        checkout_tax.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(checkoutTax));
        checkout_shipping.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(checkoutShipping));
        checkout_discount.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(checkoutDiscount));
        
        checkout_subtotal.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(checkoutSubtotal));
        checkout_total.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(checkoutTotal));

    }



    //*********** Set Order Details to proceed Checkout ********//

    private void proceedOrder() {

        PostOrder orderDetails = new PostOrder();
        List<PostProducts> orderProductList = new ArrayList<>();
    
        for (int i=0;  i<checkoutItemsList.size();  i++) {
        
            PostProducts orderProduct = new PostProducts();
        
            // Get current Product Details
            orderProduct.setProductsId(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId());
            orderProduct.setProductsName(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsName());
            orderProduct.setModel(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsModel());
            orderProduct.setImage(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsImage());
            orderProduct.setWeight(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeight());
            orderProduct.setUnit(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsWeightUnit());
            orderProduct.setManufacture(checkoutItemsList.get(i).getCustomersBasketProduct().getManufacturersName());
            orderProduct.setCategoriesId(checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId());
            orderProduct.setCategoriesName(checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesName());
            orderProduct.setPrice(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsPrice());
            orderProduct.setFinalPrice(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsFinalPrice());
            orderProduct.setSubtotal(checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice());
            orderProduct.setTotal(checkoutItemsList.get(i).getCustomersBasketProduct().getTotalPrice());
            orderProduct.setCustomersBasketQuantity(checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity());
        
            orderProduct.setOnSale(checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1"));
    
            
            List<PostProductsAttributes> productAttributes = new ArrayList<>();
            
            for (int j=0;  j<checkoutItemsList.get(i).getCustomersBasketProductAttributes().size();  j++) {
                CartProductAttributes cartProductAttributes = checkoutItemsList.get(i).getCustomersBasketProductAttributes().get(j);
                Option attributeOption = cartProductAttributes.getOption();
                Value attributeValue = cartProductAttributes.getValues().get(0);
    
                PostProductsAttributes attribute = new PostProductsAttributes();
                attribute.setProductsOptionsId(String.valueOf(attributeOption.getId()));
                attribute.setProductsOptions(attributeOption.getName());
                attribute.setProductsOptionsValuesId(String.valueOf(attributeValue.getId()));
                attribute.setProductsOptionsValues(attributeValue.getValue());
                attribute.setOptionsValuesPrice(attributeValue.getPrice());
                attribute.setPricePrefix(attributeValue.getPricePrefix());
                attribute.setAttributeName(attributeValue.getValue()+" "+attributeValue.getPricePrefix()+attributeValue.getPrice());
    
                productAttributes.add(attribute);
            }
    
            orderProduct.setAttributes(productAttributes);
        
        
            // Add current Product to orderProductList
            orderProductList.add(orderProduct);
        }
    
    
        // Set Customer Info
        orderDetails.setCustomersId(Integer.parseInt(userInfo.getCustomersId()));
        orderDetails.setCustomersName(userInfo.getCustomersFirstname());
        orderDetails.setCustomersTelephone(userInfo.getCustomersTelephone());
        orderDetails.setCustomersEmailAddress(userInfo.getCustomersEmailAddress());
    
        // Set Shipping  Info
        orderDetails.setDeliveryFirstname(shippingAddress.getFirstname());
        orderDetails.setDeliveryLastname(shippingAddress.getLastname());
        orderDetails.setDeliveryStreetAddress(shippingAddress.getStreet());
        orderDetails.setDeliveryPostcode(shippingAddress.getPostcode());
        orderDetails.setDeliverySuburb(shippingAddress.getSuburb());
        orderDetails.setDeliveryCity(shippingAddress.getCity());
        orderDetails.setDeliveryZone(shippingAddress.getZoneName());
        orderDetails.setDeliveryState(shippingAddress.getZoneName());
        orderDetails.setDeliverySuburb(shippingAddress.getZoneName());
        orderDetails.setDeliveryCountry(shippingAddress.getCountryName());
        orderDetails.setDeliveryZoneId(String.valueOf(shippingAddress.getZoneId()));
        orderDetails.setDeliveryCountryId(String.valueOf(shippingAddress.getCountriesId()));
    
        // Set Billing Info
        orderDetails.setBillingFirstname(billingAddress.getFirstname());
        orderDetails.setBillingLastname(billingAddress.getLastname());
        orderDetails.setBillingStreetAddress(billingAddress.getStreet());
        orderDetails.setBillingPostcode(billingAddress.getPostcode());
        orderDetails.setBillingSuburb(billingAddress.getSuburb());
        orderDetails.setBillingCity(billingAddress.getCity());
        orderDetails.setBillingZone(billingAddress.getZoneName());
        orderDetails.setBillingState(billingAddress.getZoneName());
        orderDetails.setBillingSuburb(billingAddress.getZoneName());
        orderDetails.setBillingCountry(billingAddress.getCountryName());
        orderDetails.setBillingZoneId(String.valueOf(billingAddress.getZoneId()));
        orderDetails.setBillingCountryId(String.valueOf(billingAddress.getCountriesId()));
    
        orderDetails.setTaxZoneId(shippingAddress.getZoneId());
        orderDetails.setTotalTax(checkoutTax);
        orderDetails.setShippingCost(checkoutShipping);
        orderDetails.setShippingMethod(shippingMethod.getName() + " ("+ shippingMethod.getShippingMethod() +")");
    
        orderDetails.setComments(checkout_comments.getText().toString().trim());
    
        if (couponsList.size() > 0) {
            orderDetails.setIsCouponApplied(1);
        } else {
            orderDetails.setIsCouponApplied(0);
        }
        orderDetails.setCouponAmount(checkoutDiscount);
        orderDetails.setCoupons(couponsList);
    
        // Set PaymentNonceToken and PaymentMethod
        orderDetails.setNonce(paymentNonceToken);
        orderDetails.setPaymentMethod(selectedPaymentMethod);
    
        // Set Checkout Price and Products
        orderDetails.setProductsTotal(checkoutSubtotal);
        orderDetails.setTotalPrice(checkoutTotal);
        orderDetails.setProducts(orderProductList);
        
        
        PlaceOrderNow(orderDetails);
        
    }



    //*********** Request the Server to Generate BrainTreeToken ********//

    private void RequestPaymentMethods() {

        dialogLoader.showProgressDialog();

        Call<PaymentMethodsData> call = APIClient.getInstance()
                .getPaymentMethods();


        call.enqueue(new Callback<PaymentMethodsData>() {
            @Override
            public void onResponse(Call<PaymentMethodsData> call, retrofit2.Response<PaymentMethodsData> response) {

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        for (int i=0;  i<response.body().getData().size();  i++) {

                            PaymentMethodsInfo paymentMethodsInfo = response.body().getData().get(i);
                            
                            if (paymentMethodsInfo.getName().equalsIgnoreCase("Cash On Delivery")
                                    && paymentMethodsInfo.getActive().equalsIgnoreCase("1"))
                            {
                                paymentMethodsList.add("Cash On Delivery");
                            }
                            
                            
                            if (paymentMethodsInfo.getName().equalsIgnoreCase("Paypal")
                                    && paymentMethodsInfo.getActive().equalsIgnoreCase("1"))
                            {
                                paymentMethodsList.add("PayPal");
                                PAYMENT_CURRENCY = paymentMethodsInfo.getPaymentCurrency();
                                PAYPAL_PUBLISHABLE_KEY = paymentMethodsInfo.getPublicKey();
    
                                payPalConfiguration = new PayPalConfiguration()
                                    // sandbox (ENVIRONMENT_SANDBOX)
                                    // or live (ENVIRONMENT_PRODUCTION)
                                    .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
                                    .clientId(PAYPAL_PUBLISHABLE_KEY);
    
                                Intent intent = new Intent(getContext(), PayPalService.class);
                                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
    
                                getContext().startService(intent);
                            }
                            

                            if (paymentMethodsInfo.getName().equalsIgnoreCase("Stripe")
                                    && paymentMethodsInfo.getActive().equalsIgnoreCase("1"))
                            {
                                paymentMethodsList.add("Stripe Credit Card");
                                STRIPE_PUBLISHABLE_KEY = paymentMethodsInfo.getPublicKey();
                            }


                            if (paymentMethodsInfo.getName().equalsIgnoreCase("Braintree")
                                    && paymentMethodsInfo.getActive().equalsIgnoreCase("1"))
                            {
                                paymentMethodsList.add("Braintree Credit Card");
                                paymentMethodsList.add("Braintree PayPal");

                                GenerateBrainTreeToken();

                            }
                            else {
                                dialogLoader.hideProgressDialog();
                            }

                        }

                    }
                    else {
                        // Unexpected Response from Server
                        dialogLoader.hideProgressDialog();
                        Snackbar.make(rootView, getString(R.string.cannot_get_payment_methods), Snackbar.LENGTH_LONG).show();
                        Toast.makeText(getContext(), getString(R.string.cannot_get_payment_methods), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    dialogLoader.hideProgressDialog();
                    Toast.makeText(getContext(), getString(R.string.cannot_get_payment_methods), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentMethodsData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }


    
    //*********** Request the Server to Generate BrainTreeToken ********//

    private void GenerateBrainTreeToken() {

        Call<GetBrainTreeToken> call = APIClient.getInstance()
                .generateBraintreeToken();


        call.enqueue(new Callback<GetBrainTreeToken>() {
            @Override
            public void onResponse(Call<GetBrainTreeToken> call, retrofit2.Response<GetBrainTreeToken> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        braintreeToken = response.body().getToken();

                        // Initialize BraintreeFragment with BraintreeToken
                        try {
                            braintreeFragment = BraintreeFragment.newInstance(getActivity(), braintreeToken);
                        } catch (InvalidArgumentException e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        Snackbar.make(rootView, getString(R.string.cannot_initialize_braintree), Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), getString(R.string.cannot_initialize_braintree), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetBrainTreeToken> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

    
    
    //*********** Request the Server to Generate BrainTreeToken ********//

    private void GetCouponInfo(String coupon_code) {

        Call<CouponsData> call = APIClient.getInstance()
                .getCouponInfo
                        (
                                coupon_code
                        );


        call.enqueue(new Callback<CouponsData>() {
            @Override
            public void onResponse(Call<CouponsData> call, retrofit2.Response<CouponsData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        final CouponsInfo couponsInfo = response.body().getData().get(0);
                        
                        if (couponsList.size() !=0 && couponsInfo.getIndividualUse().equalsIgnoreCase("1")) {
                            
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            
                            dialog.setTitle(getString(R.string.add_coupon));
                            dialog.setMessage(getString(R.string.coupon_removes_other_coupons));
                            
                            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
    
                                    if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_cart")
                                            || couponsInfo.getDiscountType().equalsIgnoreCase("percent"))
                                    {
                                        if (validateCouponCart(couponsInfo))
                                                applyCoupon(couponsInfo);
                                        
                                    }
                                    else if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_product")
                                            || couponsInfo.getDiscountType().equalsIgnoreCase("percent_product"))
                                    {
                                        if (validateCouponProduct(couponsInfo))
                                            applyCoupon(couponsInfo);
                                    }
                                }
                            });
                            
                            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                            
                        }
                        else {
                            if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_cart")
                                    || couponsInfo.getDiscountType().equalsIgnoreCase("percent"))
                            {
                                if (validateCouponCart(couponsInfo))
                                    applyCoupon(couponsInfo);
                                
                            }
                            else if (couponsInfo.getDiscountType().equalsIgnoreCase("fixed_product")
                                    || couponsInfo.getDiscountType().equalsIgnoreCase("percent_product"))
                            {
                                if (validateCouponProduct(couponsInfo))
                                    applyCoupon(couponsInfo);
                            }
                        }

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        checkout_coupon_code.setError(response.body().getMessage());

                    } else {
                        // Unexpected Response from Server
                        Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CouponsData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Request the Server to Place User's Order ********//

    private void PlaceOrderNow(PostOrder postOrder) {

        Call<OrderData> call = APIClient.getInstance()
                .addToOrder
                        (
                                postOrder
                        );

        call.enqueue(new Callback<OrderData>() {
            @Override
            public void onResponse(Call<OrderData> call, retrofit2.Response<OrderData> response) {

                progressDialog.dismiss();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
    
                        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
                        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        
                        // Order has been placed Successfully
                        NotificationHelper.showNewNotification(getContext(), notificationIntent, getString(R.string.thank_you), response.body().getMessage());


                        // Clear User's Cart
                        My_Cart.ClearCart();

                        // Clear User's Shipping and Billing info from AppContext
                        ((App) getContext().getApplicationContext()).setShippingAddress(new AddressDetails());
                        ((App) getContext().getApplicationContext()).setBillingAddress(new AddressDetails());

                        
                        // Navigate to Thank_You Fragment
                        Fragment fragment = new Thank_You();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.popBackStack(getString(R.string.actionCart), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment, fragment)
                                .commit();


                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Apply given Coupon to checkout ********//
    
    public void applyCoupon(CouponsInfo coupon) {
    
        if (coupon.getIndividualUse().equalsIgnoreCase("1")) {
            couponsList.clear();
            checkoutDiscount = 0.0;
            checkoutShipping = checkoutShippingCost;
            disableOtherCoupons = true;
            setCheckoutTotal();
        }
        
        if (coupon.getFreeShipping().equalsIgnoreCase("1")) {
            checkoutShipping = 0.0;
        }
    
    
        double discount = 0.0;
    
        if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart")) {
            discount = Double.parseDouble(coupon.getAmount());
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("percent")) {
            discount = (checkoutSubtotal * Double.parseDouble(coupon.getAmount())) / 100;
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("fixed_product")) {
            
            for (int i=0;  i<checkoutItemsList.size();  i++) {
                
                int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
                int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
                
    
                if (!checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")  ||  !coupon.getExcludeSaleItems().equalsIgnoreCase("1")) {
                    if (!isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())  ||  coupon.getExcludedProductCategories().size() == 0 ) {
                        if (!isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())  ||  coupon.getExcludeProductIds().size() == 0 ) {
                            if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())  ||  coupon.getProductCategories().size() == 0 ) {
                                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())  ||  coupon.getProductIds().size() == 0 ) {
                                    
                                    discount += (Double.parseDouble(coupon.getAmount()) * checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity());
                                }
                            }
                        }
                    }
                }
    
                
            }
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("percent_product")) {
            
            for (int i=0;  i<checkoutItemsList.size();  i++) {
        
                int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
                int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
    
    
                if (!checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")  ||  !coupon.getExcludeSaleItems().equalsIgnoreCase("1")) {
                    if (!isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())  ||  coupon.getExcludedProductCategories().size() == 0 ) {
                        if (!isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())  ||  coupon.getExcludeProductIds().size() == 0 ) {
                            if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())  ||  coupon.getProductCategories().size() == 0 ) {
                                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())  ||  coupon.getProductIds().size() == 0 ) {
    
                                    double discountOnPrice = (Double.parseDouble(checkoutItemsList.get(i).getCustomersBasketProduct().getProductsFinalPrice()) * Double.parseDouble(coupon.getAmount())) / 100;
                                    discount += (discountOnPrice * checkoutItemsList.get(i).getCustomersBasketProduct().getCustomersBasketQuantity());
                                }
                            }
                        }
                    }
                }
        
            }
        }
    
        checkoutDiscount += discount;
        coupon.setDiscount(String.valueOf(discount));
        
        
        couponsList.add(coupon);
        checkout_coupon_code.setText("");
        couponsAdapter.notifyDataSetChanged();
        
        
        setCheckoutTotal();

    }
    
    
    
    //*********** Remove given Coupon from checkout ********//
    
    public void removeCoupon(CouponsInfo coupon) {
        
        if (coupon.getIndividualUse().equalsIgnoreCase("1")) {
            disableOtherCoupons = false;
        }
    
    
        for (int i=0;  i<couponsList.size();  i++) {
            if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode())) {
                couponsList.remove(i);
                couponsAdapter.notifyDataSetChanged();
            }
        }
    
        
        checkoutShipping = checkoutShippingCost;
    
        for (int i=0;  i<couponsList.size();  i++) {
            if (couponsList.get(i).getFreeShipping().equalsIgnoreCase("1")) {
                checkoutShipping = 0.0;
            }
        }
    
    
        double discount = Double.parseDouble(coupon.getDiscount());
        checkoutDiscount -= discount;
        
    
        setCheckoutTotal();

    }
    
    
    
    //*********** Validate Cart type Coupon ********//
    
    private boolean validateCouponCart(CouponsInfo coupon) {
    
        int user_used_this_coupon_counter = 0;
        
        boolean coupon_already_applied = false;
        
        boolean valid_user_email_for_coupon = false;
        boolean valid_sale_items_in_for_coupon = true;
        
        boolean valid_items_in_cart = false;
        boolean valid_category_items_in_cart = false;
        
        boolean no_excluded_item_in_cart = true;
        boolean no_excluded_category_item_in_cart = true;
        
        
        if (couponsList.size() != 0) {
            for (int i=0;  i<couponsList.size();  i++) {
                if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode())) {
                    coupon_already_applied = true;
                }
            }
        }
        
        
        for (int i=0;  i<coupon.getUsedBy().size();  i++) {
            if (userInfo.getCustomersId().equalsIgnoreCase(coupon.getUsedBy().get(i))) {
                user_used_this_coupon_counter += 1;
            }
        }
    
        
        if (coupon.getEmailRestrictions().size() != 0) {
            if (isStringExistsInList(userInfo.getCustomersEmailAddress(), coupon.getEmailRestrictions())) {
                valid_user_email_for_coupon = true;
            }
        }
        else {
            valid_user_email_for_coupon = true;
        }
        
    
    
        for (int i=0;  i<checkoutItemsList.size();  i++) {
    
            int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
            int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
            
    
            if (coupon.getExcludeSaleItems().equalsIgnoreCase("1") && checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")) {
                valid_sale_items_in_for_coupon = false;
            }
            
    
            if (coupon.getExcludedProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())) {
                    no_excluded_category_item_in_cart = false;
                }
            }
    
            if (coupon.getExcludeProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())) {
                    no_excluded_item_in_cart = false;
                }
            }
    
            if (coupon.getProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())) {
                    valid_category_items_in_cart = true;
                }
            } else {
                valid_category_items_in_cart = true;
            }
    
            
            if (coupon.getProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())) {
                    valid_items_in_cart = true;
                }
            } else {
                valid_items_in_cart = true;
            }
            
        }
        
        
        /////////////////////////////////////////////////////
        
        if (!disableOtherCoupons) {
            if (!coupon_already_applied) {
                if (!Utilities.checkIsDatePassed(coupon.getExpiryDate())) {
                    if (Integer.parseInt(coupon.getUsageCount()) <= Integer.parseInt(coupon.getUsageLimit())) {
                        if (user_used_this_coupon_counter <= Integer.parseInt(coupon.getUsageLimitPerUser())) {
                            if (valid_user_email_for_coupon) {
                                if (Double.parseDouble(coupon.getMinimumAmount()) <= checkoutTotal) {
                                    if (Double.parseDouble(coupon.getMaximumAmount()) == 0.0  ||  checkoutTotal <= Double.parseDouble(coupon.getMaximumAmount())) {
                                        if (valid_sale_items_in_for_coupon) {
                                            if (no_excluded_category_item_in_cart) {
                                                if (no_excluded_item_in_cart) {
                                                    if (valid_category_items_in_cart) {
                                                        if (valid_items_in_cart) {
                        
                                                            return true;
                        
                                                        } else {
                                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_products));
                                                            return false;
                                                        }
                                                    } else {
                                                        showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_categories));
                                                        return false;
                                                    }
                                                } else {
                                                    showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_products));
                                                    return false;
                                                }
                                            } else {
                                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_categories));
                                                return false;
                                            }
                                        } else {
                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_sale_items));
                                            return false;
                                        }
                                    } else {
                                        showSnackBarForCoupon(getString(R.string.coupon_max_amount_is_less_than_order_total));
                                        return false;
                                    }
                                } else {
                                    showSnackBarForCoupon(getString(R.string.coupon_min_amount_is_greater_than_order_total));
                                    return false;
                                }
                            } else {
                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_you));
                                return false;
                            }
                        } else {
                            showSnackBarForCoupon(getString(R.string.coupon_used_by_you));
                            return false;
                        }
                    } else {
                        showSnackBarForCoupon(getString(R.string.coupon_used_by_all));
                        return false;
                    }
                } else {
                    checkout_coupon_code.setError(getString(R.string.coupon_expired));
                    return false;
                }
            } else {
                showSnackBarForCoupon(getString(R.string.coupon_applied));
                return false;
            }
        } else {
            showSnackBarForCoupon(getString(R.string.coupon_cannot_used_with_existing));
            return false;
        }

    }
    
    
    
    //*********** Validate Product type Coupon ********//
    
    private boolean validateCouponProduct(CouponsInfo coupon) {
        
        int user_used_this_coupon_counter = 0;
        
        boolean coupon_already_applied = false;
        
        boolean valid_user_email_for_coupon = false;
        boolean valid_sale_items_in_for_coupon = false;
        
        boolean any_valid_item_in_cart = false;
        boolean any_valid_category_item_in_cart = false;
        
        boolean any_non_excluded_item_in_cart = false;
        boolean any_non_excluded_category_item_in_cart = false;
        
        
        if (couponsList.size() != 0) {
            for (int i=0;  i<couponsList.size();  i++) {
                if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode())) {
                    coupon_already_applied = true;
                }
            }
        }
        
        
        for (int i=0;  i<coupon.getUsedBy().size();  i++) {
            if (userInfo.getCustomersId().equalsIgnoreCase(coupon.getUsedBy().get(i))) {
                user_used_this_coupon_counter += 1;
            }
        }
        
        
        if (coupon.getEmailRestrictions().size() != 0) {
            if (isStringExistsInList(userInfo.getCustomersEmailAddress(), coupon.getEmailRestrictions())) {
                valid_user_email_for_coupon = true;
            }
        }
        else {
            valid_user_email_for_coupon = true;
        }
        
        
        
        for (int i=0;  i<checkoutItemsList.size();  i++) {
            
            int productID = checkoutItemsList.get(i).getCustomersBasketProduct().getProductsId();
            int categoryID = checkoutItemsList.get(i).getCustomersBasketProduct().getCategoriesId();
            
            
            if (!coupon.getExcludeSaleItems().equalsIgnoreCase("1") || !checkoutItemsList.get(i).getCustomersBasketProduct().getIsSaleProduct().equalsIgnoreCase("1")) {
                valid_sale_items_in_for_coupon = true;
            }
            
            
            if (coupon.getExcludedProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getExcludedProductCategories())) {
                    any_non_excluded_category_item_in_cart = true;
                }
            } else {
                any_non_excluded_category_item_in_cart = true;
            }
            
            if (coupon.getExcludeProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getExcludeProductIds())) {
                    any_non_excluded_item_in_cart = true;
                }
            } else {
                any_non_excluded_item_in_cart = true;
            }
            
            if (coupon.getProductCategories().size() != 0) {
                if (isStringExistsInList(String.valueOf(categoryID), coupon.getProductCategories())) {
                    any_valid_category_item_in_cart = true;
                }
            } else {
                any_valid_category_item_in_cart = true;
            }
            
            
            if (coupon.getProductIds().size() != 0) {
                if (isStringExistsInList(String.valueOf(productID), coupon.getProductIds())) {
                    any_valid_item_in_cart = true;
                }
            } else {
                any_valid_item_in_cart = true;
            }
            
        }
        
        
        /////////////////////////////////////////////////////
        
        if (!disableOtherCoupons) {
            if (!coupon_already_applied) {
                if (!Utilities.checkIsDatePassed(coupon.getExpiryDate())) {
                    if (Integer.parseInt(coupon.getUsageCount()) <= Integer.parseInt(coupon.getUsageLimit())) {
                        if (user_used_this_coupon_counter <= Integer.parseInt(coupon.getUsageLimitPerUser())) {
                            if (valid_user_email_for_coupon) {
                                if (Double.parseDouble(coupon.getMinimumAmount()) <= checkoutTotal) {
                                    if (Double.parseDouble(coupon.getMaximumAmount()) == 0.0  ||  checkoutTotal <= Double.parseDouble(coupon.getMaximumAmount())) {
                                        if (valid_sale_items_in_for_coupon) {
                                            if (any_non_excluded_category_item_in_cart) {
                                                if (any_non_excluded_item_in_cart) {
                                                    if (any_valid_category_item_in_cart) {
                                                        if (any_valid_item_in_cart) {
                        
                                                            return true;
                        
                                                        } else {
                                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_products));
                                                            return false;
                                                        }
                                                    } else {
                                                        showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_categories));
                                                        return false;
                                                    }
                                                } else {
                                                    showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_products));
                                                    return false;
                                                }
                                            } else {
                                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_categories));
                                                return false;
                                            }
                                        } else {
                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_sale_items));
                                            return false;
                                        }
                                    } else {
                                        showSnackBarForCoupon(getString(R.string.coupon_max_amount_is_less_than_order_total));
                                        return false;
                                    }
                                } else {
                                    showSnackBarForCoupon(getString(R.string.coupon_min_amount_is_greater_than_order_total));
                                    return false;
                                }
                            } else {
                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_you));
                                return false;
                            }
                        } else {
                            showSnackBarForCoupon(getString(R.string.coupon_used_by_you));
                            return false;
                        }
                    } else {
                        showSnackBarForCoupon(getString(R.string.coupon_used_by_all));
                        return false;
                    }
                } else {
                    checkout_coupon_code.setError(getString(R.string.coupon_expired));
                    return false;
                }
            } else {
                showSnackBarForCoupon(getString(R.string.coupon_applied));
                return false;
            }
        } else {
            showSnackBarForCoupon(getString(R.string.coupon_cannot_used_with_existing));
            return false;
        }
        
    }
    
    
    
    //*********** Show SnackBar with given Message  ********//
    
    private void showSnackBarForCoupon(String msg) {
        final Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.show();
    }
    
    
    
    //*********** Check if the given String exists in the given List ********//
    
    private boolean isStringExistsInList(String str, List<String> stringList) {
        boolean isExists = false;
    
        for (int i=0;  i<stringList.size();  i++) {
            if (stringList.get(i).equalsIgnoreCase(str)) {
                isExists = true;
            }
        }
        
        
        return isExists;
    }
    
    
    
    //*********** Setup Demo Coupons Dialog ********//
    
    private void setupDemoCoupons() {
    
        demo_coupons_text.setVisibility(View.VISIBLE);
        demo_coupons_text.setPaintFlags(demo_coupons_text.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        
        demo_coupons_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final List<CouponsInfo> couponsList = demoCouponsList();
                DemoCouponsListAdapter couponsListAdapter = new DemoCouponsListAdapter(getContext(), couponsList, Checkout.this);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);

                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);

                dialog_title.setText(getString(R.string.search) +" "+ getString(R.string.coupon));
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter(couponsListAdapter);

                demoCouponsDialog = dialog.create();

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        demoCouponsDialog.dismiss();
                    }
                });

                demoCouponsDialog.show();
            }
        });
    }
    
    
    
    //*********** Sets selected Coupon code from the Dialog ********//
    
    public void setCouponCode(String code) {
        checkout_coupon_code.setText(code);
        demoCouponsDialog.dismiss();
    }
    
    
    
    //*********** Demo Coupons List ********//
    
    private List<CouponsInfo> demoCouponsList() {
        List<CouponsInfo> couponsList = new ArrayList<>();

        CouponsInfo coupon1 = new CouponsInfo();
        coupon1.setCode("PercentProduct_10");
        coupon1.setAmount("10");
        coupon1.setDiscountType("Percent Product");
        coupon1.setDescription("For All Products");

        CouponsInfo coupon2 = new CouponsInfo();
        coupon2.setCode("FixedProduct_10");
        coupon2.setAmount("10");
        coupon2.setDiscountType("Fixed Product");
        coupon2.setDescription("For All Products");

        CouponsInfo coupon3 = new CouponsInfo();
        coupon3.setCode("PercentCart_10");
        coupon3.setAmount("10");
        coupon3.setDiscountType("Percent Cart");
        coupon3.setDescription("For All Products");

        CouponsInfo coupon4 = new CouponsInfo();
        coupon4.setCode("FixedCart_10");
        coupon4.setAmount("10");
        coupon4.setDiscountType("Fixed Cart");
        coupon4.setDescription("For All Products");

        CouponsInfo coupon5 = new CouponsInfo();
        coupon5.setCode("SingleCoupon_50");
        coupon5.setAmount("50");
        coupon5.setDiscountType("Fixed Cart");
        coupon5.setDescription("Individual Use");

        CouponsInfo coupon6 = new CouponsInfo();
        coupon6.setCode("FreeShipping_20");
        coupon6.setAmount("20");
        coupon6.setDiscountType("Fixed Cart");
        coupon6.setDescription("Free Shipping");

        CouponsInfo coupon7 = new CouponsInfo();
        coupon7.setCode("ExcludeSale_15");
        coupon7.setAmount("15");
        coupon7.setDiscountType("Fixed Cart");
        coupon7.setDescription("Not for Sale Items");

        CouponsInfo coupon8 = new CouponsInfo();
        coupon8.setCode("Exclude_Shoes_25");
        coupon8.setAmount("25");
        coupon8.setDiscountType("Fixed Cart");
        coupon8.setDescription("Not For Men Shoes");

        CouponsInfo coupon9 = new CouponsInfo();
        coupon9.setCode("Polo_Shirts_10");
        coupon9.setAmount("10");
        coupon9.setDiscountType("Percent Product");
        coupon9.setDescription("For Men Polo Shirts");

        CouponsInfo coupon10 = new CouponsInfo();
        coupon10.setCode("Jeans_10");
        coupon10.setAmount("10");
        coupon10.setDiscountType("Percent Cart");
        coupon10.setDescription("For Men Jeans");


        couponsList.add(coupon1);
        couponsList.add(coupon2);
        couponsList.add(coupon3);
        couponsList.add(coupon4);
        couponsList.add(coupon5);
        couponsList.add(coupon6);
        couponsList.add(coupon7);
        couponsList.add(coupon8);
        couponsList.add(coupon9);
        couponsList.add(coupon10);

        return couponsList;
    }



    //*********** Validate Payment Card Inputs ********//

    private boolean validatePaymentCard() {
        if (!ValidateInputs.isValidNumber(checkout_card_number.getText().toString().trim())) {
            checkout_card_number.setError(getString(R.string.invalid_credit_card));
            return false;
        } else if (!ValidateInputs.isValidNumber(checkout_card_cvv.getText().toString().trim())) {
            checkout_card_cvv.setError(getString(R.string.invalid_card_cvv));
            return false;
        } else if (checkout_card_expiry.getText().toString().trim().isEmpty()) {
            checkout_card_expiry.setError(getString(R.string.select_card_expiry));
            return false;
        } else {
            return true;
        }
    }

}

