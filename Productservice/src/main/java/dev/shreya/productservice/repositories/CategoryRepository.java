package dev.shreya.productservice.repositories;

import dev.shreya.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByTitle(String title);
    Category save(Category category);

    List<Category> findAll();
}
