package com.myshopp.androidecommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Value implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_prefix")
    @Expose
    private String pricePrefix;



    public Value() {
    }



    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The pricePrefix
     */
    public String getPricePrefix() {
        return pricePrefix;
    }

    /**
     * 
     * @param pricePrefix
     *     The price_prefix
     */
    public void setPricePrefix(String pricePrefix) {
        this.pricePrefix = pricePrefix;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeInt(id);
        parcel_out.writeString(value);
        parcel_out.writeString(price);
        parcel_out.writeString(pricePrefix);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<Value> CREATOR = new Creator<Value>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public Value createFromParcel(Parcel parcel_in) {
            return new Value(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public Value[] newArray(int size) {
            return new Value[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected Value(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.value = parcel_in.readString();
        this.price = parcel_in.readString();
        this.pricePrefix = parcel_in.readString();
    }

}
