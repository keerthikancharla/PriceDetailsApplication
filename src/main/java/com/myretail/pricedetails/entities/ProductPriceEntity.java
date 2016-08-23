package com.myretail.pricedetails.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by keerthiprasad on 8/16/16.
 */
@Entity
@Table(name = "product_price")
public class ProductPriceEntity {

    @Id
    @NotNull
    private Integer productId;

    @NotNull
    private Double productPrice;

    private String currencyCode = "USD";

    /**
     * Instantiates a new Product price entity.
     */
    public ProductPriceEntity() {
    }

    /**
     * Instantiates a new Product price entity.
     *
     * @param productId the product id
     */
    public ProductPriceEntity(Integer productId) {
        this.productId = productId;
    }

    /**
     * Instantiates a new Product price entity.
     *
     * @param productId    the product id
     * @param productPrice the product price
     * @param currencyCode the currency code
     */
    public ProductPriceEntity(Integer productId, Double productPrice, String currencyCode) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.currencyCode = currencyCode;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets product id.
     *
     * @param productId the product id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Gets currency code.
     *
     * @return the currency code
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets currency code.
     *
     * @param currencyCode the currency code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Gets product price.
     *
     * @return the product price
     */
    public Double getProductPrice() {
        return productPrice;
    }

    /**
     * Sets product price.
     *
     * @param productPrice the product price
     */
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
