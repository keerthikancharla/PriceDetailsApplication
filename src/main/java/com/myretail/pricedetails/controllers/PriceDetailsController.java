package com.myretail.pricedetails.controllers;

import com.myretail.pricedetails.entities.ProductPriceEntity;
import com.myretail.pricedetails.exceptions.ResourceAlreadyExists;
import com.myretail.pricedetails.exceptions.ResourceNotFoundException;
import com.myretail.pricedetails.exceptions.UnknownServerException;
import com.myretail.pricedetails.repositories.PriceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by keerthiprasad on 8/16/16.
 */
@Controller
public class PriceDetailsController {

    @Autowired
    private PriceDetailsRepository priceDetailsRepository;

    /**
     * @param productId
     * @return
     */
    @RequestMapping(value = "/price/{productId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ProductPriceEntity getPriceDetails(@PathVariable @Valid Integer productId) {

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

    @RequestMapping(value = "/price", method = RequestMethod.POST)
    public
    @ResponseBody
    ProductPriceEntity savePriceDetails(@RequestBody @Valid ProductPriceEntity productPriceEntity) {
        if (priceDetailsRepository.exists(productPriceEntity.getProductId()))
            throw new ResourceAlreadyExists("Requested resource already exists");
        else
            try {
                return priceDetailsRepository.save(productPriceEntity);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new UnknownServerException(e.getMessage());
            }
    }

    @RequestMapping(value = "/price/{productId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Boolean deletePriceDetails(@PathVariable @Valid Integer productId) {
        if (priceDetailsRepository.exists(productId))
            try {
                priceDetailsRepository.delete(productId);
                return true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new UnknownServerException(e.getMessage());
            }
        else
            throw new ResourceNotFoundException("Requested Resource Not Found");
    }

    @RequestMapping(value = "/price", method = RequestMethod.PUT)
    public
    @ResponseBody
    ProductPriceEntity updatePriceDetails(@RequestBody @Valid ProductPriceEntity productPriceEntity) {
        if (priceDetailsRepository.exists(productPriceEntity.getProductId()))
            try {
                return priceDetailsRepository.save(productPriceEntity);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new UnknownServerException(e.getMessage());
            }
        else
            throw new ResourceNotFoundException("Requested Resource Not Found");

    }

    @RequestMapping(value = "/prices", method = RequestMethod.PUT)
    public
    @ResponseBody
    String updatePricesDetails(@RequestBody @Valid List<ProductPriceEntity> productPriceEntities) {
        for (int i = 0; i < productPriceEntities.size(); i++) {

            if (priceDetailsRepository.exists(productPriceEntities.get(i).getProductId()))
                try {
                    priceDetailsRepository.save(productPriceEntities.get(i));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new UnknownServerException(e.getMessage());

                }
            else
                throw new ResourceNotFoundException("Requested Resource Not Found");
        }
        return "Saved Successfully";

    }

    @RequestMapping(value = "/prices", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ProductPriceEntity> getAllPriceDetails() {
        try {
            return (List<ProductPriceEntity>) priceDetailsRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnknownServerException(e.getMessage());
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    String indexPage() {

        return "Use the following urls to see the interactions with the database:<br/>\"\n" +
                " * GET '/prices': Get all price details.<br/>\"\n" +
                " * GET '/price/productId': get the price info details of the passed product id.<br/>\"\n" +
                " * DELETE '/price/productId': delete the price info of the passed product id.<br/>\"\n" +
                " * POST '/price': create the price info with posted data.<br/>\"\n" +
                " * PUT '/price': update the price info of the product id posted.<br/>\"\n" +
                " * PUT '/prices': update the price info of the list of products.";
    }
}
