//package dev.shreya.productservice;
//
//import dev.shreya.productservice.models.Category;
//import dev.shreya.productservice.models.Product;
//import dev.shreya.productservice.repositories.CategoryRepository;
//import dev.shreya.productservice.repositories.ProductRepository;
//import dev.shreya.productservice.repositories.projections.ProductWithIdAndTitle;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//class ProductserviceApplicationTests {
// @Autowired
//    ProductRepository productRepository;
//   @Autowired
//   CategoryRepository categoryRepository;
//    @Test
//    void contextLoads() {
//    }
//    @Test
//    void productRepoTest(){
//       List<ProductWithIdAndTitle>  productWithIdAndTitles= productRepository.getNameOfProductWithProjection("t2", 2L);
//         //();
//       System.out.println(productWithIdAndTitles.get(0).getTitle());
//       //Native
//       List<ProductWithIdAndTitle>  productWithIdAndTitles1= productRepository.getNameOfProductWithProjection("t2", 2L);
//       //();
//       System.out.println(productWithIdAndTitles1.get(0).getTitle());
//    }
//   @Test
//   void testingQueries() {
//       //as fetch type is lazy by default of list of product
//      // select c1_0.id,c1_0.created_at,c1_0.is_deleted,c1_0.last_updated_at,c1_0.title from category c1_0 where c1_0.id=?
//      Optional<Category> optionalCategory  =categoryRepository.findById(1l);//no join happens
//      Category c = optionalCategory.get();
//      System.out.println("Fetch the category object");
//      //2 query is executed when u fetch product
//      List<Product> productList = c.getProducts();
//
//
//   }
//
//}
