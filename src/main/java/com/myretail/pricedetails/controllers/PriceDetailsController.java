package com.myretail.pricedetails.controllers;

import com.myretail.pricedetails.entities.ProductPriceEntity;
import com.myretail.pricedetails.exceptions.ResourceAlreadyExists;
import com.myretail.pricedetails.exceptions.ResourceNotFoundException;
import com.myretail.pricedetails.exceptions.UnknownServerException;
import com.myretail.pricedetails.repositories.PriceDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by keerthiprasad on 8/16/16.
 * This class will be invoked when ever a URL is hit
 */
@Controller
public class PriceDetailsController {
    private final Logger logger = LoggerFactory.getLogger(PriceDetailsController.class);
    @Autowired
    private PriceDetailsRepository priceDetailsRepository;

    /**
     * Gets price details.
     *
     * @param productId the product id
     * @return the product price entity
     * This  method will be invoked for a GET method with the URL pattern as mentioned in the request mapping.
     * This method is used to get the price and currency code details based on product ID.
     */
    @RequestMapping(value = "/price/{productId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ProductPriceEntity getPriceDetails(@PathVariable @Valid Integer productId) {
        logger.info("Invoked GET method with URL as /price/{productId}");
        if (priceDetailsRepository.exists(productId))
            try {
                return priceDetailsRepository.findByProductId(productId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new UnknownServerException(e.getMessage());
            }
        else
            throw new ResourceNotFoundException("Requested Resource Not Found");
    }

    /**
     * Save price details product price entity.
     *
     * @param productPriceEntity the product price entity
     * @return the product price entity
     * This  method will be invoked for a POST method with the URL pattern as mentioned in the request mapping.
     * This method is used to insert price and currency code details for a product.
     */
    @RequestMapping(value = "/price", method = RequestMethod.POST)
    public
    @ResponseBody
    ProductPriceEntity savePriceDetails(@RequestBody @Valid ProductPriceEntity productPriceEntity) {
        logger.info("Invoked POST method with URL as /price");
        if (priceDetailsRepository.exists(productPriceEntity.getProductId()))
            throw new ResourceAlreadyExists("Requested resource already exists");
        else
            try {
                return priceDetailsRepository.save(productPriceEntity);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new UnknownServerException(e.getMessage());
            }
    }

    /**
     * Delete price details boolean.
     *
     * @param productId the product id
     * @return the boolean
     * This  method will be invoked for a DELETE method with the URL pattern as mentioned in the request mapping.
     * This method is used to DELETE a product.
     */
    @RequestMapping(value = "/price/{productId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Boolean deletePriceDetails(@PathVariable @Valid Integer productId) {
        logger.info("Invoked DELETE method with URL as /price/{productId}");
        if (priceDetailsRepository.exists(productId))
            try {
                priceDetailsRepository.delete(productId);
                return true;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new UnknownServerException(e.getMessage());
            }
        else
            throw new ResourceNotFoundException("Requested Resource Not Found");
    }

    /**
     * Update price details product price entity.
     *
     * @param productPriceEntity the product price entity
     * @return the product price entity
     * This  method will be invoked for a PUT method with the URL pattern as mentioned in the request mapping.
     * This method is used to save the price details for a product.
     */
    @RequestMapping(value = "/price", method = RequestMethod.PUT)
    public
    @ResponseBody
    ProductPriceEntity updatePriceDetails(@RequestBody @Valid ProductPriceEntity productPriceEntity) {
        logger.info("Invoked PUT method with URL as /price");
        if (priceDetailsRepository.exists(productPriceEntity.getProductId()))
            try {
                return priceDetailsRepository.save(productPriceEntity);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new UnknownServerException(e.getMessage());
            }
        else
            throw new ResourceNotFoundException("Requested Resource Not Found");

    }

    /**
     * Update prices details string.
     *
     * @param productPriceEntities the product price entities
     * @return the string
     * This  method will be invoked for a PUT method with the URL pattern as mentioned in the request mapping.
     * This method is used to save price details for multiple products.
     */
    @RequestMapping(value = "/prices", method = RequestMethod.PUT)
    public
    @ResponseBody
    String updatePricesDetails(@RequestBody @Valid List<ProductPriceEntity> productPriceEntities) {
        logger.info("Invoked PUT method with URL as /prices");
        for (int i = 0; i < productPriceEntities.size(); i++) {

            if (priceDetailsRepository.exists(productPriceEntities.get(i).getProductId()))
                try {
                    priceDetailsRepository.save(productPriceEntities.get(i));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new UnknownServerException(e.getMessage());

                }
            else
                throw new ResourceNotFoundException("Requested Resource Not Found");
        }
        return "Saved Successfully";

    }

    /**
     * Gets all price details.
     *
     * @return the all price details
     * This  method will be invoked for a GET method with the URL pattern as mentioned in the request mapping.
     * This method is used to get the price and currency code details of all the products
     */
    @RequestMapping(value = "/prices", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ProductPriceEntity> getAllPriceDetails() {
        logger.info("Invoked GET method with URL as /prices");
        try {
            return (List<ProductPriceEntity>) priceDetailsRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnknownServerException(e.getMessage());
        }

    }

    /**
     * Index page string.
     *
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    String indexPage() {
        logger.info("Invoked GET method with URL as /");
        return "Use the following urls to see the interactions with the database:<br/>\"\n" +
                " * GET '/prices': Get all price details.<br/>\"\n" +
                " * GET '/price/productId': get the price info details of the passed product id.<br/>\"\n" +
                " * DELETE '/price/productId': delete the price info of the passed product id.<br/>\"\n" +
                " * POST '/price': create the price info with posted data.<br/>\"\n" +
                " * PUT '/price': update the price info of the product id posted.<br/>\"\n" +
                " * PUT '/prices': update the price info of the list of products.";
    }
}
