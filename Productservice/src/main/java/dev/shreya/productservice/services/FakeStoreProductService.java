package dev.shreya.productservice.services;

import dev.shreya.productservice.dtos.FakeStoreProductDto;
import dev.shreya.productservice.exceptions.ProductNotFoundException;
import dev.shreya.productservice.models.Category;
import dev.shreya.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service("fakeStoreProductService")//give name
public class FakeStoreProductService implements  ProductService{

    private RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductResponse = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );
        FakeStoreProductDto fakeStoreProduct = fakeStoreProductResponse.getBody();

        if (fakeStoreProduct == null) {
            //here we will get this msg but with not custom statuscode and and also we will get complete stacktrace
            throw new ProductNotFoundException("Product with id: " + productId + " doesn't exist. Retry some other product.");
        }
        return fakeStoreProductResponse.getBody().toProduct();
    }

    @Override
    public List<Product> getProducts() {

        ResponseEntity<FakeStoreProductDto[]> fakeStoreProducts =
                restTemplate.getForEntity(
                        "https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class
                );
        if(fakeStoreProducts.getStatusCode() != HttpStatusCode.valueOf(200)){
            //throw exception
        }

        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProduct: fakeStoreProducts.getBody()) {
            products.add(fakeStoreProduct.toProduct());
        }

        return products;
    }

    @Override
    public List<Category> getAllProductsCategories() {
        ResponseEntity<Category[]> fakeStoreProductsCategories =
                restTemplate.getForEntity(
                        "https://fakestoreapi.com/products",
                        Category[].class
                );
        List<Category> categoryList = new ArrayList<>();

        for (Category category: fakeStoreProductsCategories.getBody()) {
            categoryList.add(category);
        }

        return categoryList;
    }

    @Override
    public List<Product> getProductInSpecificCategory(String category) {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProducts =
                restTemplate.getForEntity(
                        "https://fakestoreapi.com/products/category/" + category,
                        FakeStoreProductDto[].class
                );

        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDto fakeStoreProduct: fakeStoreProducts.getBody()) {
            products.add(fakeStoreProduct.toProduct());
        }

        return products;
    }

    @Override
    public Product createProduct(String title, String description, String category, double price, String image) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setCategory(category);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setDescription(description);

        FakeStoreProductDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products", // url
                fakeStoreProductDto, // request body
                FakeStoreProductDto.class // data type of response
        );

        if (response == null) return new Product();

        return response.toProduct();
    }

    @Override
    public Product updateProduct(Long productId,String title, String description, String category, double price, String image) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setCategory(category);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setDescription(description);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(
                MediaType.APPLICATION_JSON));

        // building a HttpEntity using HttpHeaders and updated
        // customer object to customize the request
        HttpEntity<FakeStoreProductDto> entity
                = new HttpEntity<>(fakeStoreProductDto, httpHeaders);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/"+productId ,//URL

                HttpMethod.PUT,
                entity, // request body
                FakeStoreProductDto.class // data type of response
        );

        if (response == null) return new Product();

        return response.getBody().toProduct();
    }
    @Override
    public Product deleteProduct(Long productId) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(
                MediaType.APPLICATION_JSON));

        // building a HttpEntity using HttpHeaders and updated
        // customer object to customize the request
        HttpEntity<FakeStoreProductDto> entity
                = new HttpEntity<>(httpHeaders);
       return restTemplate.exchange(
                "https://fakestoreapi.com/products/"+productId ,//URL

                HttpMethod.DELETE,
                entity, // request body
                FakeStoreProductDto.class // data type of response
        ).getBody().toProduct();


    }

    @Override
    public Page<Product> getAllPageProducts(int pageno,int pagesize,String sort) {
        return null;
    }
}
