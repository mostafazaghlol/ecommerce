package com.myshopp.androidecommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductDetails implements Parcelable {


    @SerializedName("products_id")
    @Expose
    private int productsId;
    @SerializedName("products_quantity")
    @Expose
    private int productsQuantity;
    @SerializedName("products_model")
    @Expose
    private String productsModel;
    @SerializedName("products_image")
    @Expose
    private String productsImage;
    @SerializedName("products_price")
    @Expose
    private String productsPrice;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("products_date_added")
    @Expose
    private String productsDateAdded;
    @SerializedName("products_last_modified")
    @Expose
    private String productsLastModified;
    @SerializedName("products_date_available")
    @Expose
    private String productsDateAvailable;
    @SerializedName("products_weight")
    @Expose
    private String productsWeight;
    @SerializedName("products_weight_unit")
    @Expose
    private String productsWeightUnit;
    @SerializedName("products_status")
    @Expose
    private int productsStatus;
    @SerializedName("products_ordered")
    @Expose
    private int productsOrdered;
    @SerializedName("products_liked")
    @Expose
    private int productsLiked;
    @SerializedName("language_id")
    @Expose
    private int languageId;
    @SerializedName("products_name")
    @Expose
    private String productsName;
    @SerializedName("products_description")
    @Expose
    private String productsDescription;
    @SerializedName("products_url")
    @Expose
    private String productsUrl;
    @SerializedName("products_viewed")
    @Expose
    private int productsViewed;
    @SerializedName("products_tax_class_id")
    @Expose
    private int productsTaxClassId;
    @SerializedName("tax_rates_id")
    @Expose
    private int taxRatesId;
    @SerializedName("tax_zone_id")
    @Expose
    private int taxZoneId;
    @SerializedName("tax_class_id")
    @Expose
    private int taxClassId;
    @SerializedName("tax_priority")
    @Expose
    private int taxPriority;
    @SerializedName("tax_rate")
    @Expose
    private String taxRate;
    @SerializedName("tax_description")
    @Expose
    private String taxDescription;
    @SerializedName("tax_class_title")
    @Expose
    private String taxClassTitle;
    @SerializedName("tax_class_description")
    @Expose
    private String taxClassDescription;
    @SerializedName("categories_id")
    @Expose
    private int categoriesId;
    @SerializedName("categories_name")
    @Expose
    private String categoriesName;
    @SerializedName("categories_image")
    @Expose
    private String categoriesImage;
    @SerializedName("categories_icon")
    @Expose
    private String categoriesIcon;
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("sort_order")
    @Expose
    private int sortOrder;
    @SerializedName("isLiked")
    @Expose
    private String isLiked;
    @SerializedName("manufacturers_id")
    @Expose
    private int manufacturersId;
    @SerializedName("manufacturers_name")
    @Expose
    private String manufacturersName;
    @SerializedName("manufacturers_image")
    @Expose
    private String manufacturersImage;
    @SerializedName("manufacturers_url")
    @Expose
    private String manufacturersUrl;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("last_modified")
    @Expose
    private String lastModified;
    
    @SerializedName("isSale_product")
    @Expose
    private String isSaleProduct;
    
    
    @SerializedName("attributes_price")
    @Expose
    private String attributesPrice;
    @SerializedName("final_price")
    @Expose
    private String productsFinalPrice;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("customers_basket_quantity")
    @Expose
    private int customersBasketQuantity;

    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = new ArrayList<Attribute>();



    public ProductDetails() {
    }



    public int getProductsId() {
        return productsId;
    }

    public void setProductsId(int productsId) {
        this.productsId = productsId;
    }

    public int getProductsQuantity() {
        return productsQuantity;
    }

    public void setProductsQuantity(int productsQuantity) {
        this.productsQuantity = productsQuantity;
    }

    public String getProductsModel() {
        return productsModel;
    }

    public void setProductsModel(String productsModel) {
        this.productsModel = productsModel;
    }

    public String getProductsImage() {
        return productsImage;
    }

    public void setProductsImage(String productsImage) {
        this.productsImage = productsImage;
    }

    public String getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(String productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getProductsDateAdded() {
        return productsDateAdded;
    }

    public void setProductsDateAdded(String productsDateAdded) {
        this.productsDateAdded = productsDateAdded;
    }

    public String getProductsLastModified() {
        return productsLastModified;
    }

    public void setProductsLastModified(String productsLastModified) {
        this.productsLastModified = productsLastModified;
    }

    public String getProductsDateAvailable() {
        return productsDateAvailable;
    }

    public void setProductsDateAvailable(String productsDateAvailable) {
        this.productsDateAvailable = productsDateAvailable;
    }

    public String getProductsWeight() {
        return productsWeight;
    }

    public void setProductsWeight(String productsWeight) {
        this.productsWeight = productsWeight;
    }

    public String getProductsWeightUnit() {
        return productsWeightUnit;
    }

    public void setProductsWeightUnit(String productsWeightUnit) {
        this.productsWeightUnit = productsWeightUnit;
    }

    public int getProductsStatus() {
        return productsStatus;
    }

    public void setProductsStatus(int productsStatus) {
        this.productsStatus = productsStatus;
    }

    public int getProductsOrdered() {
        return productsOrdered;
    }

    public void setProductsOrdered(int productsOrdered) {
        this.productsOrdered = productsOrdered;
    }

    public int getProductsLiked() {
        return productsLiked;
    }

    public void setProductsLiked(int productsLiked) {
        this.productsLiked = productsLiked;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getProductsDescription() {
        return productsDescription;
    }

    public void setProductsDescription(String productsDescription) {
        this.productsDescription = productsDescription;
    }

    public String getProductsUrl() {
        return productsUrl;
    }

    public void setProductsUrl(String productsUrl) {
        this.productsUrl = productsUrl;
    }

    public int getProductsViewed() {
        return productsViewed;
    }

    public void setProductsViewed(int productsViewed) {
        this.productsViewed = productsViewed;
    }

    public int getProductsTaxClassId() {
        return productsTaxClassId;
    }

    public void setProductsTaxClassId(int productsTaxClassId) {
        this.productsTaxClassId = productsTaxClassId;
    }

    public int getTaxRatesId() {
        return taxRatesId;
    }

    public void setTaxRatesId(int taxRatesId) {
        this.taxRatesId = taxRatesId;
    }

    public int getTaxZoneId() {
        return taxZoneId;
    }

    public void setTaxZoneId(int taxZoneId) {
        this.taxZoneId = taxZoneId;
    }

    public int getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(int taxClassId) {
        this.taxClassId = taxClassId;
    }

    public int getTaxPriority() {
        return taxPriority;
    }

    public void setTaxPriority(int taxPriority) {
        this.taxPriority = taxPriority;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public String getTaxClassTitle() {
        return taxClassTitle;
    }

    public void setTaxClassTitle(String taxClassTitle) {
        this.taxClassTitle = taxClassTitle;
    }

    public String getTaxClassDescription() {
        return taxClassDescription;
    }

    public void setTaxClassDescription(String taxClassDescription) {
        this.taxClassDescription = taxClassDescription;
    }

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName;
    }

    public int getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getCategoriesImage() {
        return categoriesImage;
    }

    public void setCategoriesImage(String categoriesImage) {
        this.categoriesImage = categoriesImage;
    }

    public String getCategoriesIcon() {
        return categoriesIcon;
    }

    public void setCategoriesIcon(String categoriesIcon) {
        this.categoriesIcon = categoriesIcon;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }

    public int getManufacturersId() {
        return manufacturersId;
    }

    public void setManufacturersId(int manufacturersId) {
        this.manufacturersId = manufacturersId;
    }

    public String getManufacturersName() {
        return manufacturersName;
    }

    public void setManufacturersName(String manufacturersName) {
        this.manufacturersName = manufacturersName;
    }

    public String getManufacturersImage() {
        return manufacturersImage;
    }

    public void setManufacturersImage(String manufacturersImage) {
        this.manufacturersImage = manufacturersImage;
    }

    public String getManufacturersUrl() {
        return manufacturersUrl;
    }

    public void setManufacturersUrl(String manufacturersUrl) {
        this.manufacturersUrl = manufacturersUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
    
    
    public String getIsSaleProduct() {
        return isSaleProduct;
    }
    
    public void setIsSaleProduct(String isSaleProduct) {
        this.isSaleProduct = isSaleProduct;
    }
    
    
    public String getAttributesPrice() {
        return attributesPrice;
    }
    
    public void setAttributesPrice(String attributesPrice) {
        this.attributesPrice = attributesPrice;
    }
    
    public String getProductsFinalPrice() {
        return productsFinalPrice;
    }
    
    public void setProductsFinalPrice(String productsFinalPrice) {
        this.productsFinalPrice = productsFinalPrice;
    }
    
    public String getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public int getCustomersBasketQuantity() {
        return customersBasketQuantity;
    }

    public void setCustomersBasketQuantity(int customersBasketQuantity) {
        this.customersBasketQuantity = customersBasketQuantity;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeInt(productsId);
        parcel_out.writeInt(productsQuantity);
        parcel_out.writeInt(customersBasketQuantity);
        parcel_out.writeInt(productsStatus);
        parcel_out.writeInt(productsOrdered);
        parcel_out.writeInt(productsViewed);
        parcel_out.writeInt(productsLiked);
        parcel_out.writeInt(categoriesId);
        parcel_out.writeInt(manufacturersId);
        parcel_out.writeInt(taxClassId);
        parcel_out.writeInt(taxRatesId);
        parcel_out.writeInt(taxPriority);
        parcel_out.writeInt(taxZoneId);
        parcel_out.writeInt(productsTaxClassId);
        parcel_out.writeInt(languageId);
        parcel_out.writeInt(parentId);
        parcel_out.writeInt(sortOrder);
        parcel_out.writeString(productsName);
        parcel_out.writeString(productsModel);
        parcel_out.writeString(productsImage);
        parcel_out.writeString(productsWeight);
        parcel_out.writeString(productsWeightUnit);
        parcel_out.writeString(productsUrl);
        parcel_out.writeString(productsDescription);
        parcel_out.writeString(productsPrice);
        parcel_out.writeString(discountPrice);
        parcel_out.writeString(attributesPrice);
        parcel_out.writeString(productsFinalPrice);
        parcel_out.writeString(totalPrice);
        parcel_out.writeString(isLiked);
        parcel_out.writeString(dateAdded);
        parcel_out.writeString(productsDateAdded);
        parcel_out.writeString(productsDateAvailable);
        parcel_out.writeString(lastModified);
        parcel_out.writeString(productsLastModified);
        parcel_out.writeString(taxRate);
        parcel_out.writeString(taxDescription);
        parcel_out.writeString(taxClassTitle);
        parcel_out.writeString(taxClassDescription);
        parcel_out.writeString(categoriesName);
        parcel_out.writeString(categoriesImage);
        parcel_out.writeString(categoriesIcon);
        parcel_out.writeString(manufacturersName);
        parcel_out.writeString(manufacturersImage);
        parcel_out.writeString(manufacturersUrl);
        parcel_out.writeList(images);
        parcel_out.writeList(attributes);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
        
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductDetails createFromParcel(Parcel parcel_in) {
            return new ProductDetails(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected ProductDetails(Parcel parcel_in) {
        this.productsId = parcel_in.readInt();
        this.productsQuantity = parcel_in.readInt();
        this.customersBasketQuantity = parcel_in.readInt();
        this.productsStatus = parcel_in.readInt();
        this.productsOrdered = parcel_in.readInt();
        this.productsViewed = parcel_in.readInt();
        this.productsLiked = parcel_in.readInt();
        this.categoriesId = parcel_in.readInt();
        this.manufacturersId = parcel_in.readInt();
        this.taxClassId = parcel_in.readInt();
        this.taxRatesId = parcel_in.readInt();
        this.taxPriority = parcel_in.readInt();
        this.taxZoneId = parcel_in.readInt();
        this.productsTaxClassId = parcel_in.readInt();
        this.languageId = parcel_in.readInt();
        this.parentId = parcel_in.readInt();
        this.sortOrder = parcel_in.readInt();
        this.productsName = parcel_in.readString();
        this.productsModel = parcel_in.readString();
        this.productsImage = parcel_in.readString();
        this.productsWeight = parcel_in.readString();
        this.productsWeightUnit = parcel_in.readString();
        this.productsUrl = parcel_in.readString();
        this.productsDescription = parcel_in.readString();
        this.productsPrice = parcel_in.readString();
        this.discountPrice = parcel_in.readString();
        this.attributesPrice = parcel_in.readString();
        this.productsFinalPrice = parcel_in.readString();
        this.totalPrice = parcel_in.readString();
        this.isLiked = parcel_in.readString();
        this.dateAdded = parcel_in.readString();
        this.productsDateAdded = parcel_in.readString();
        this.productsDateAvailable = parcel_in.readString();
        this.lastModified = parcel_in.readString();
        this.productsLastModified = parcel_in.readString();
        this.taxRate = parcel_in.readString();
        this.taxDescription = parcel_in.readString();
        this.taxClassTitle = parcel_in.readString();
        this.taxClassDescription = parcel_in.readString();
        this.categoriesName = parcel_in.readString();
        this.categoriesImage = parcel_in.readString();
        this.categoriesIcon = parcel_in.readString();
        this.manufacturersName = parcel_in.readString();
        this.manufacturersImage = parcel_in.readString();
        this.manufacturersUrl = parcel_in.readString();

        this.images = new ArrayList<Image>();
        parcel_in.readList(images, Image.class.getClassLoader());

        this.attributes = new ArrayList<Attribute>();
        parcel_in.readList(attributes, Attribute.class.getClassLoader());
    }

}
