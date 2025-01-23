package dev.shreya.productservice.services;

import dev.shreya.productservice.exceptions.ProductNotFoundException;
import dev.shreya.productservice.models.Category;
import dev.shreya.productservice.models.Product;
import dev.shreya.productservice.repositories.CategoryRepository;
import dev.shreya.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService  implements ProductService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    public SelfProductService(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findByIdIs(productId);
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Category> getAllProductsCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getProductInSpecificCategory(String category) {
        return productRepository.findAllByCategoryTitle(category);
    }

    @Override
    public Product createProduct(String title, String description, String category, double price, String image) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);
        //first check if category already exist then use that else craete new category
          Category categoryFromDatabase  = categoryRepository.findByTitle(category);
          if(categoryFromDatabase  == null){
              Category newCategory = new Category();
              newCategory.setTitle(category);
              categoryFromDatabase = newCategory;
          }
        // if the category was found from DB -> category1 will be having an ID
        // else: category1 won't have any ID
        product.setCategory(categoryFromDatabase);
          //coz cascaeetype is persist so first create category on save product if category does not exist
        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public Product updateProduct(Long productId, String title, String description, String category, double price, String image) {
       Product productFromDatabase = productRepository.findByIdIs(productId);
     //  PATCH
//        if(title != null) productFromDatabase.setTitle(title);
//        if(description != null) productFromDatabase.setDescription(description);
//        if(price != 0.0) productFromDatabase.setPrice(price);
//        if(image != null) productFromDatabase.setImageUrl(image);
//        Category category1 = categoryRepository.findByTitle(category);
//        category1.setTitle(category);
//        if(category != null) productFromDatabase.setCategory(category1);
//        return productRepository.save(productFromDatabase);

        ///////////PUT///////////////////

        Product p = new Product();
        p.setId(productId);
        if(title != null) p.setTitle(title);
        if(description != null) p.setDescription(description);
        if(price != 0.0) p.setPrice(price);
        if(image != null) p.setImageUrl(image);


        Category c = categoryRepository.findByTitle(category);
        if(c  == null){
            Category newCategory = new Category();
            newCategory.setTitle(category);
            c = newCategory;
        }
        // if the category was found from DB -> category1 will be having an ID
        // else: category1 won't have any ID

         p.setCategory(c);
        Product savedProduct = productRepository.save(p);
        return savedProduct;
    }

    @Override
    public Product deleteProduct(Long productId) {
        this.productRepository.deleteById(productId);
        Product p = new Product();
        p.setId(productId);
        return p;
    }

    @Override
    public Page<Product> getAllPageProducts(int pageno,int pagesize,String sort) {
        Pageable pageable = null;
        if(sort != null){
            pageable = PageRequest.of(pageno,pagesize, Sort.Direction.ASC,sort);
        }else{
            pageable = PageRequest.of(pageno,pagesize);
        }
        return  productRepository.findAll(pageable);
    }
}
