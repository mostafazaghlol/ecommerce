package com.myshopp.androidecommerce.models.order_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderProducts implements Parcelable {

    @SerializedName("orders_products_id")
    @Expose
    private int ordersProductsId;
    @SerializedName("orders_id")
    @Expose
    private int ordersId;
    @SerializedName("products_id")
    @Expose
    private int productsId;
    @SerializedName("products_model")
    @Expose
    private String productsModel;
    @SerializedName("products_name")
    @Expose
    private String productsName;
    @SerializedName("products_price")
    @Expose
    private String productsPrice;
    @SerializedName("final_price")
    @Expose
    private String finalPrice;
    @SerializedName("products_tax")
    @Expose
    private String productsTax;
    @SerializedName("products_quantity")
    @Expose
    private int productsQuantity;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("categories_id")
    @Expose
    private String categoriesID;
    @SerializedName("categories_name")
    @Expose
    private String categoriesName;
    @SerializedName("language_id")
    @Expose
    private String languageID;
    @SerializedName("attributes")
    @Expose
    private List<PostProductsAttributes> attribute = new ArrayList<PostProductsAttributes>();

    /**
     * 
     * @return
     *     The ordersProductsId
     */
    public int getOrdersProductsId() {
        return ordersProductsId;
    }

    /**
     * 
     * @param ordersProductsId
     *     The orders_products_id
     */
    public void setOrdersProductsId(int ordersProductsId) {
        this.ordersProductsId = ordersProductsId;
    }

    /**
     * 
     * @return
     *     The ordersId
     */
    public int getOrdersId() {
        return ordersId;
    }

    /**
     * 
     * @param ordersId
     *     The orders_id
     */
    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    /**
     * 
     * @return
     *     The productsId
     */
    public int getProductsId() {
        return productsId;
    }

    /**
     * 
     * @param productsId
     *     The products_id
     */
    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    /**
     * 
     * @return
     *     The productsModel
     */
    public String getProductsModel() {
        return productsModel;
    }

    /**
     * 
     * @param productsModel
     *     The products_model
     */
    public void setProductsModel(String productsModel) {
        this.productsModel = productsModel;
    }

    /**
     * 
     * @return
     *     The productsName
     */
    public String getProductsName() {
        return productsName;
    }

    /**
     * 
     * @param productsName
     *     The products_name
     */
    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    /**
     * 
     * @return
     *     The productsPrice
     */
    public String getProductsPrice() {
        return productsPrice;
    }

    /**
     * 
     * @param productsPrice
     *     The products_price
     */
    public void setProductsPrice(String productsPrice) {
        this.productsPrice = productsPrice;
    }

    /**
     * 
     * @return
     *     The finalPrice
     */
    public String getFinalPrice() {
        return finalPrice;
    }

    /**
     * 
     * @param finalPrice
     *     The final_price
     */
    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    /**
     * 
     * @return
     *     The productsTax
     */
    public String getProductsTax() {
        return productsTax;
    }

    /**
     * 
     * @param productsTax
     *     The products_tax
     */
    public void setProductsTax(String productsTax) {
        this.productsTax = productsTax;
    }

    /**
     * 
     * @return
     *     The productsQuantity
     */
    public int getProductsQuantity() {
        return productsQuantity;
    }

    /**
     * 
     * @param productsQuantity
     *     The products_quantity
     */
    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }


    public String getCategoriesID() {
        return categoriesID;
    }

    public void setCategoriesID(String categoriesID) {
        this.categoriesID = categoriesID;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }


    /**
     * 
     * @return
     *     The attribute
     */
    public List<PostProductsAttributes> getAttribute() {
        return attribute;
    }

    /**
     * 
     * @param attribute
     *     The attribute
     */
    public void setAttribute(List<PostProductsAttributes> attribute) {
        this.attribute = attribute;
    }
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    
    
    //********** Writes the values to the Parcel *********//
    
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeValue(ordersProductsId);
        parcel_out.writeValue(ordersId);
        parcel_out.writeValue(productsId);
        parcel_out.writeValue(productsModel);
        parcel_out.writeValue(productsName);
        parcel_out.writeValue(productsPrice);
        parcel_out.writeValue(finalPrice);
        parcel_out.writeValue(productsTax);
        parcel_out.writeValue(productsQuantity);
        parcel_out.writeValue(image);
        parcel_out.writeValue(categoriesID);
        parcel_out.writeValue(categoriesName);
        parcel_out.writeValue(languageID);
        parcel_out.writeList(attribute);
    }
    
    
    
    //********** Generates Instances of Parcelable class from a Parcel *********//
    
    public static final Creator<OrderProducts> CREATOR = new Creator<OrderProducts>() {
        
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public OrderProducts createFromParcel(Parcel parcel_in) {
            return new OrderProducts(parcel_in);
        }
        
        // Creates a new array of the Parcelable class
        @Override
        public OrderProducts[] newArray(int size) {
            return new OrderProducts[size];
        }
    };
    
    
    
    //********** Retrieves the values from the Parcel *********//
    
    protected OrderProducts(Parcel parcel_in) {
        this.ordersProductsId = parcel_in.readInt();
        this.ordersId = parcel_in.readInt();
        this.productsId = parcel_in.readInt();
        this.productsModel = parcel_in.readString();
        this.productsName = parcel_in.readString();
        this.productsPrice = parcel_in.readString();
        this.finalPrice = parcel_in.readString();
        this.productsTax = parcel_in.readString();
        this.productsQuantity = parcel_in.readInt();
        this.image = parcel_in.readString();
        this.categoriesID = parcel_in.readString();
        this.categoriesName = parcel_in.readString();
        this.languageID = parcel_in.readString();

        this.attribute = new ArrayList<PostProductsAttributes>();
        parcel_in.readList(attribute, PostProductsAttributes.class.getClassLoader());
    }
    
}
