//package com.myretail.pricedetails;
//
//import org.assertj.core.api.Assertions;
//import org.json.JSONObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by keerthiprasad on 8/20/16.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class PriceDetailsServiceTests {
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Test
//    public void testGetPriceByIdWrongPath() {
//        ResponseEntity<String> response = testRestTemplate.getForEntity("/price/", String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//    }
//
//    @Test
//    public void testGetPriceByInvalidId() {
//        ResponseEntity<String> response = testRestTemplate.getForEntity("/price/123456", String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void testGetPriceByValidId() {
//        ResponseEntity<String> response = testRestTemplate.getForEntity("/price/15117729", String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody()).contains("15117729");
//    }
//
//    @Test
//    public void testGetAllProductsPrices() {
//        ResponseEntity<String> response = testRestTemplate.getForEntity("/prices/", String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody().length()).isGreaterThanOrEqualTo(1);
//    }
//
//    @Test
//    public void testGetAllProductsPricesByWrongPath() {
//        ResponseEntity<String> response = testRestTemplate.getForEntity("/prices/15117729", String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void testDeleteProductByWrongPath() {
//        ResponseEntity<String> response = testRestTemplate.exchange("/price", HttpMethod.DELETE, null, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//    }
//
//    @Test
//    public void testDeleteProductByWrongPath2() {
//        ResponseEntity<String> response = testRestTemplate.exchange("/prices/139", HttpMethod.DELETE, null, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void testDeleteProductByWrongID() {
//        ResponseEntity<String> response = testRestTemplate.exchange("/price/12345", HttpMethod.DELETE, null, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void testDeleteProductByCorrectID() {
//        ResponseEntity<String> response = testRestTemplate.exchange("/price/1386042", HttpMethod.DELETE, null, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    public void testUpdateProductByWrongPath() {
//        ResponseEntity<String> response = testRestTemplate.exchange("/price/1386042", HttpMethod.PUT, null, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//    }
//
//    @Test
//    public void testUpdateSingleProduct() {
//        Map<String, Object> jsonObject = new HashMap<String, Object>();
//        jsonObject.put("productId", 15117729);
//        jsonObject.put("productPrice", 99.99);
//        jsonObject.put("currencyCode", "USD");
//        HttpEntity requestEntity = new HttpEntity(jsonObject);
//        ResponseEntity<String> response = testRestTemplate.exchange("/price", HttpMethod.PUT, requestEntity, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(new JSONObject(response.getBody())).isEqualToComparingFieldByField(new JSONObject(jsonObject));
//    }
//
//    @Test
//    public void testUpdateInvalidProduct() {
//        Map<String, Object> jsonObject = new HashMap<String, Object>();
//        jsonObject.put("productId", 151177);
//        jsonObject.put("productPrice", 99.99);
//        HttpEntity requestEntity = new HttpEntity(jsonObject);
//        ResponseEntity<String> response = testRestTemplate.exchange("/price", HttpMethod.PUT, requestEntity, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    @Test
//    public void testUpdateWithoutProductPrice() {
//        Map<String, Object> jsonObject = new HashMap<String, Object>();
//        jsonObject.put("productId", 15117729);
//        HttpEntity requestEntity = new HttpEntity(jsonObject);
//        ResponseEntity<String> response = testRestTemplate.exchange("/price", HttpMethod.PUT, requestEntity, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void testSaveWithoutProductPrice() {
//        Map<String, Object> jsonObject = new HashMap<String, Object>();
//        jsonObject.put("productId", 15117729);
//        HttpEntity requestEntity = new HttpEntity(jsonObject);
//        ResponseEntity<String> response = testRestTemplate.exchange("/price", HttpMethod.POST, requestEntity, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void testSaveWithInvalidProduct() {
//        Map<String, Object> jsonObject = new HashMap<String, Object>();
//        jsonObject.put("productId", 1511719);
//        jsonObject.put("productPrice", 99.99);
//        HttpEntity requestEntity = new HttpEntity(jsonObject);
//        ResponseEntity<String> response = testRestTemplate.exchange("/price", HttpMethod.POST, requestEntity, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void testSaveWithExistingProductID() {
//        Map<String, Object> jsonObject = new HashMap<String, Object>();
//        jsonObject.put("productId", 1511719);
//        jsonObject.put("productPrice", 99.99);
//        HttpEntity requestEntity = new HttpEntity(jsonObject);
//        ResponseEntity<String> response = testRestTemplate.exchange("/price", HttpMethod.POST, requestEntity, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//    }
//
//    @Test
//    public void testSaveWithINvalidRequest() {
//        Map<String, Object> jsonObject = new HashMap<String, Object>();
//        jsonObject.put("productId", 1511719);
//        jsonObject.put("productPrice", 99.99);
//        HttpEntity requestEntity = new HttpEntity(jsonObject);
//        ResponseEntity<String> response = testRestTemplate.exchange("/prices", HttpMethod.POST, requestEntity, String.class);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
//    }
//}
