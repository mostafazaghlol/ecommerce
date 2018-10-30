package com.myshopp.androidecommerce.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.myshopp.androidecommerce.models.filter_model.post_filters.FiltersAttributes;
import com.myshopp.androidecommerce.models.filter_model.post_filters.FiltersPrice;



public class GetAllProducts {

    @SerializedName("page_number")
    @Expose
    private int pageNumber;
    @SerializedName("language_id")
    @Expose
    private int languageId;
    @SerializedName("customers_id")
    @Expose
    private String customersId;
    @SerializedName("categories_id")
    @Expose
    private String categoriesId;
    @SerializedName("products_id")
    @Expose
    private String productsId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("filters")
    @Expose
    private List<FiltersAttributes> filters = null;
    @SerializedName("price")
    @Expose
    private FiltersPrice price;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getCustomersId() {
        return customersId;
    }

    public void setCustomersId(String customersId) {
        this.customersId = customersId;
    }

    public String getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FiltersAttributes> getFilters() {
        return filters;
    }

    public void setFilters(List<FiltersAttributes> filters) {
        this.filters = filters;
    }

    public FiltersPrice getPrice() {
        return price;
    }

    public void setPrice(FiltersPrice price) {
        this.price = price;
    }

}
