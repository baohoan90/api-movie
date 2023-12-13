package com.ivanfranchin.movieapi.service;

import com.ivanfranchin.movieapi.exception.MovieNotFoundException;
import com.ivanfranchin.movieapi.model.Product;
import com.ivanfranchin.movieapi.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAllByOrderByTitle();
    }

    @Override
    public List<Product> getProductsContainingText(String text) {
        return productRepository.findByImdbContainingOrSlugContainingIgnoreCaseOrderBySlug(text, text);
    }

    @Override
    public Product validateAndGetProduct(String imdb) {
        return productRepository.findById(imdb)
                .orElseThrow(() -> new MovieNotFoundException(String.format("Product with imdb %s not found", imdb)));
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Product product) {
    	productRepository.delete(product);
    }
}
