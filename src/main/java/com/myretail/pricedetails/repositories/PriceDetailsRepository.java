package com.myretail.pricedetails.repositories;

import com.myretail.pricedetails.entities.ProductPriceEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by keerthiprasad on 8/16/16.
 */
@Transactional
public interface PriceDetailsRepository extends CrudRepository<ProductPriceEntity, Integer> {
    /**
     * Find by product id product price entity.
     *
     * @param productId the product id
     * @return the product price entity
     */
    ProductPriceEntity findByProductId(Integer productId);
}
