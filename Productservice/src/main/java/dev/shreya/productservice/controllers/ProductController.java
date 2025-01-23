package dev.shreya.productservice.controllers;

import dev.shreya.productservice.client.AuthenticationClient;
import dev.shreya.productservice.client.dtos.Role;
import dev.shreya.productservice.client.dtos.ValidateTokenResponseDto;
import dev.shreya.productservice.dtos.CreateProductRequestDto;
import dev.shreya.productservice.dtos.SessionStatus;
import dev.shreya.productservice.exceptions.ProductNotFoundException;
import dev.shreya.productservice.models.Category;
import dev.shreya.productservice.models.Product;
import dev.shreya.productservice.services.ProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    private RestTemplate restTemplate;
    private AuthenticationClient authenticationClient;
    public ProductController(@Qualifier("selfProductService")  ProductService productService,
                             AuthenticationClient authenticationClient){
        this.productService=productService;
        this.authenticationClient = authenticationClient;
    }
    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
        return productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPrice(),
                request.getImage()
        );
    }
    @PutMapping("/product/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId,@RequestBody CreateProductRequestDto request) {
        return productService.updateProduct(
                productId,
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPrice(),
                request.getImage()
        );
    }

    //Type this in URL - http://localhost:8080/products/1
    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return productService.getSingleProduct(productId);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@Nullable  @RequestHeader("AUTH_TOKEN") String token,
                                                        @Nullable @RequestHeader("USER_ID") Long userId)  {
               // check if token exists
        if (token == null || userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ValidateTokenResponseDto response = authenticationClient.validate(token, userId);
        List<Product> products = productService.getProducts();
        // check if token is valid
        if (response.getSessionStatus().equals(SessionStatus.INVALID)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //        // check if user has permissions
        boolean isUserAdmin = false;
        Role role = new Role();
        role.setName("ADMIN");
        response.getUser().getRoles().add(role);
        for (Role role1: response.getUser().getRoles()) {
            if (role1.getName() != null && role1.getName().equals("ADMIN")) {
                isUserAdmin = true;
            }
        }

        if (!isUserAdmin) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
//        throw new ProductNotFoundException("Bla bla bla");
        return new ResponseEntity<>(products, HttpStatus.OK);
//        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.OK);
//        return response;
    }
    @GetMapping("/product/categories")
    public List<Category> getAllProductsCategories()  {

        List<Category> categories = productService.getAllProductsCategories();

//        throw new ProductNotFoundException("Bla bla bla");

      //  ResponseEntity<List<String>> response = new ResponseEntity<>(categories, HttpStatus.OK);
        return categories;
    }
    @GetMapping("/products/category/{category}")
    public ResponseEntity<List<Product>> getProductInSpecificCategory(@PathVariable("category") String category)  {

        List<Product> products = productService.getProductInSpecificCategory(category);

//        throw new ProductNotFoundException("Bla bla bla");

        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.OK);
        return response;
    }
    @DeleteMapping("/product/{id}")
    public Product  deleteProductById(@PathVariable("id") Long productId){
        return productService.deleteProduct(productId);
    }
//        @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundExeption(ProductNotFoundException exception) {
//
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(exception.getMessage());
//
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
////        return null;
//    }

    // Limited to only the exceptions thrown from this controller
    // Controller Advices: Global

    // if this controller ever ends up throwing ProductNotFoundException.class
    // for any reason, don't throw that exception as is.
    // Instead call this method and return what this method is returning

    @GetMapping("/products/{pageSize}/{pageNumber}")
    public ResponseEntity  getAllPageProducts(@PathVariable("pageSize") int pageSize,
                                              @PathVariable("pageNumber") int pageNumber         )  {

        //sorted by id by default
    Page<Product> productPage = productService.getAllPageProducts(pageNumber,pageSize,null);
    return  ResponseEntity.ok(productPage.getContent());
    }
    @GetMapping("/productsByPrice/{pageSize}/{pageNumber}")
    public ResponseEntity  getAllPageProductsByPrice(@PathVariable("pageSize") int pageSize,
                                              @PathVariable("pageNumber") int pageNumber         )  {

        Page<Product> productPage = productService.getAllPageProducts(pageNumber,pageSize,"price");
        return  ResponseEntity.ok(productPage.getContent());
    }
}

