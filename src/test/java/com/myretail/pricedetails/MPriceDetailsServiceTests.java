package com.myretail.pricedetails;

import com.myretail.pricedetails.controllers.PriceDetailsController;
import com.myretail.pricedetails.entities.ProductPriceEntity;
import com.myretail.pricedetails.exceptions.ResourceNotFoundException;
import com.myretail.pricedetails.repositories.PriceDetailsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by keerthiprasad on 8/20/16.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PriceDetailsController.class)
public class MPriceDetailsServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceDetailsRepository priceDetailsRepository;

    /**
     * Test get price details.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetPriceDetails() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        given(this.priceDetailsRepository.findByProductId(anyInt()))
                .willReturn(new ProductPriceEntity(123456, 99.99, "USD"));

        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(get("/price/151177"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    /**
     * Test get price details invalid id.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetPriceDetailsInvalidId() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(false);
        given(this.priceDetailsRepository.findByProductId(anyInt()))
                .willReturn(new ProductPriceEntity(123456, 99.99, "USD"));

        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(get("/price/151177"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test get price details with exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetPriceDetailsWithException() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        given(this.priceDetailsRepository.findByProductId(anyInt()))
                .willThrow(new RuntimeException());
        mockMvc.perform(get("/price/151177"))
                .andExpect(status().is5xxServerError());
    }

    /**
     * Test get price details with invalid path.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetPriceDetailsWithInvalidPath() throws Exception {
        mockMvc.perform(get("/price/"))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test get all products price details.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetAllProductsPriceDetails() throws Exception {
        ProductPriceEntity product1 = new ProductPriceEntity(123456, 99.99, "USD");
        ProductPriceEntity product2 = new ProductPriceEntity(666788, 55.00, "USD");
        List<ProductPriceEntity> list = new ArrayList<ProductPriceEntity>();
        list.add(product1);
        list.add(product2);
        given(this.priceDetailsRepository.findAll())
                .willReturn(list);

        String jsonArray = "[{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}," +
                "{\"productId\":666788,\"productPrice\":55,\"currencyCode\":\"USD\"}]";
        mockMvc.perform(get("/prices"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonArray));
    }

    /**
     * Test get all price details with exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetAllPriceDetailsWithException() throws Exception {

        given(this.priceDetailsRepository.findAll())
                .willThrow(new RuntimeException());
        mockMvc.perform(get("/prices"))
                .andExpect(status().is5xxServerError());
    }

    /**
     * Test get all price details with invalid path.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetAllPriceDetailsWithInvalidPath() throws Exception {
        mockMvc.perform(get("/prices/123456"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test delete product by wrong path.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteProductByWrongPath() throws Exception {
        mockMvc.perform(delete("/price"))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test delete product details.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteProductDetails() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        doNothing().when(this.priceDetailsRepository).delete(anyInt());

        mockMvc.perform(delete("/price/151177"))
                .andExpect(status().isOk());
    }

    /**
     * Test delete product details invalid id.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteProductDetailsInvalidId() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(false);
        doNothing().when(this.priceDetailsRepository).delete(anyInt());

        mockMvc.perform(delete("/price/151177"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test delete price details with exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeletePriceDetailsWithException() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        willThrow(new RuntimeException()).given(this.priceDetailsRepository).delete(anyInt());
        mockMvc.perform(delete("/price/123456"))
                .andExpect(status().is5xxServerError());
    }

    /**
     * Test update product details.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductDetails() throws Exception {
        ProductPriceEntity product1 = new ProductPriceEntity(123456, 99.99, "USD");
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        given(priceDetailsRepository.save(any(ProductPriceEntity.class)))
                .willReturn(product1);

        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(put("/price").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    /**
     * Test update product details invalid id.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductDetailsInvalidId() throws Exception {
        ProductPriceEntity product1 = new ProductPriceEntity(123456, 99.99, "USD");
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(false);
        given(priceDetailsRepository.save(any(ProductPriceEntity.class)))
                .willReturn(product1);

        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(put("/price").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isNotFound());
    }

    /**
     * Test update product details with exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductDetailsWithException() throws Exception {
        ProductPriceEntity product1 = new ProductPriceEntity(123456, 99.99, "USD");
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        willThrow(new RuntimeException()).given(this.priceDetailsRepository).save(any(ProductPriceEntity.class));
        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(put("/price").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().is5xxServerError());
    }

    /**
     * Test update product by wrong path.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductByWrongPath() throws Exception {
        mockMvc.perform(put("/price/123456"))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test insert product details with existing product.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInsertProductDetailsWithExistingProduct() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        // given(this.priceDetailsRepository).save(any(ProductPriceEntity.class)).
        given(priceDetailsRepository.save(any(ProductPriceEntity.class)))
                .willThrow(ResourceNotFoundException.class);

        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(post("/price").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test insert product details with new product.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInsertProductDetailsWithNewProduct() throws Exception {
        ProductPriceEntity product1 = new ProductPriceEntity(123456, 99.99, "USD");
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(false);
        given(priceDetailsRepository.save(any(ProductPriceEntity.class)))
                .willReturn(product1);

        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(post("/price").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    /**
     * Test insert product details with exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInsertProductDetailsWithException() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(false);
        given(priceDetailsRepository.save(any(ProductPriceEntity.class)))
                .willThrow(RuntimeException.class);

        String jsonString = "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}";
        mockMvc.perform(post("/price").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().is5xxServerError());
    }

    /**
     * Test insert product by wrong path.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInsertProductByWrongPath() throws Exception {
        mockMvc.perform(post("/price/123456"))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test update products list.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductsList() throws Exception {
        ProductPriceEntity product1 = new ProductPriceEntity(123456, 99.99, "USD");
        ProductPriceEntity product2 = new ProductPriceEntity(646574, 77.69, "USD");
        List<ProductPriceEntity> list = new ArrayList<ProductPriceEntity>();
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        given(priceDetailsRepository.save(anyList()))
                .willReturn(anyList());

        String jsonArray = "[{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}," +
                "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}]";
        mockMvc.perform(put("/prices").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonArray))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved Successfully"));
    }

    /**
     * Test update product list with invalid id.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductListWithInvalidId() throws Exception {
        ProductPriceEntity product1 = new ProductPriceEntity(123456, 99.99, "USD");
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(false);
        given(priceDetailsRepository.save(anyList()))
                .willThrow(ResourceNotFoundException.class);

        String jsonArray = "[{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}," +
                "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}]";
        mockMvc.perform(put("/prices").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonArray))
                .andExpect(status().isNotFound());
    }

    /**
     * Test update products list with exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductsListWithException() throws Exception {
        given(this.priceDetailsRepository.exists(anyInt()))
                .willReturn(true);
        willThrow(new RuntimeException()).given(this.priceDetailsRepository).save(any(ProductPriceEntity.class));
        String jsonArray = "[{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}," +
                "{\"productId\":123456,\"productPrice\":99.99,\"currencyCode\":\"USD\"}]";
        mockMvc.perform(put("/prices").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(jsonArray))
                .andExpect(status().is5xxServerError());
    }

    /**
     * Test update products list by wrong path.
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateProductsListByWrongPath() throws Exception {
        mockMvc.perform(put("/prices/123456"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test index page.
     *
     * @throws Exception the exception
     */
    @Test
    public void testIndexPage() throws Exception {


        mockMvc.perform(get("/"))
                .andExpect(content().string("Use the following urls to see the interactions with the database:<br/>\"\n" +
                        " * GET '/prices': Get all price details.<br/>\"\n" +
                        " * GET '/price/productId': get the price info details of the passed product id.<br/>\"\n" +
                        " * DELETE '/price/productId': delete the price info of the passed product id.<br/>\"\n" +
                        " * POST '/price': create the price info with posted data.<br/>\"\n" +
                        " * PUT '/price': update the price info of the product id posted.<br/>\"\n" +
                        " * PUT '/prices': update the price info of the list of products."));
    }
}
