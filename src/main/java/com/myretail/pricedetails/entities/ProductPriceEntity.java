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

    public ProductPriceEntity() {
    }

    public ProductPriceEntity(Integer productId) {
        this.productId = productId;
    }

    public ProductPriceEntity(Integer productId, Double productPrice, String currencyCode) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.currencyCode = currencyCode;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
}
