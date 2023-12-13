package com.ivanfranchin.movieapi.repository;

import com.ivanfranchin.movieapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findAllByOrderByTitle();

    List<Product> findByImdbContainingOrSlugContainingIgnoreCaseOrderBySlug(String imdb, String slug);
}
