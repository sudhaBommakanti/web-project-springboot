package com.example.spring.products;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRestTests {
    @Autowired
    ProductService productService;
    @Autowired
    TestRestTemplate testRestTemplate;
    @Test
    public void testGetAll() {

    }

    public void testGetAllReturnEmptyArray() {
       //Arrange

       //Act
        Product[] responseProducts = testRestTemplate.getForObject("/products",Product[].class);
//        String response = testRestTemplate.getForObject("/products",String.class);

        //Assert
        Assertions.assertEquals(0,responseProducts.length);
//        Assertions.assertEquals("[]",response);
    }

    public void testCreate() {
        //Arrange
        Product requestProduct = new Product(null,"Test Title","Test Description");

        //Act
        Product responseProduct = testRestTemplate.postForObject("/products",requestProduct,Product.class);
        //another way
        /*HttpEntity<Product> requestProductHttpEntity = new HttpEntity<>(requestProduct);
        HttpEntity<Product> response = testRestTemplate.exchange("/products",HttpMethod.POST,requestProductHttpEntity,Product.class);
        Product responseProduct = response.getBody();*/

        // Third way
       /* HttpEntity<Product> response = testRestTemplate.postForEntity("/products",requestProduct,Product.class);
        Product responseProduct = response.getBody();*/
        //Assert
        Assertions.assertEquals(requestProduct.getName(),responseProduct.getName());
        Assertions.assertEquals(requestProduct.getDescription(),responseProduct.getDescription());

        // check that product is added
        Product getResponseByProductId = testRestTemplate.getForObject("/products/" + responseProduct.getId().toString(),Product.class);
        Assertions.assertEquals(requestProduct.getName(), getResponseByProductId.getName());
        Assertions.assertEquals(requestProduct.getDescription(),getResponseByProductId.getDescription());

        //Clean up
        testRestTemplate.delete("/products"+responseProduct.getId().toString());
    }

    @Test
    public void testUpdate() {
        //Arrange
        Product originalProduct = new Product(null,"Test Title","Test Description");
        originalProduct = productService.create(originalProduct);

        Product updatedProduct = new Product(originalProduct.getId(),"Test updated Title","Test updated Description");
        //Act - replacing this with common method private putForProduct
        /*HttpEntity<Product> requestBody = new HttpEntity<>(updatedProduct);
        HttpEntity<Product> response = testRestTemplate.exchange("/products", HttpMethod.PUT,requestBody,Product.class);
        Product responseProduct = response.getBody();*/

        Product responseProduct = putForProduct(updatedProduct);

        //Assert
       /* Assertions.assertEquals(updatedProduct.getName(),responseProduct.getName());
        Assertions.assertEquals(updatedProduct.getDescription(),responseProduct.getDescription());*/
    }

    private Product putForProduct(Product requestBody) {
        HttpEntity<Product> requestEntity = new HttpEntity<>(requestBody);
        HttpEntity<Product> response = testRestTemplate.exchange("/products",HttpMethod.PUT,requestEntity,Product.class);
        return response.getBody();
    }
}
