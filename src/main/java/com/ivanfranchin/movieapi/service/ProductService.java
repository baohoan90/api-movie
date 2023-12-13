package com.ivanfranchin.movieapi.service;

import com.ivanfranchin.movieapi.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    List<Product> getProductsContainingText(String text);

    Product validateAndGetProduct(String imdb);

    Product saveProduct(Product product);

    void deleteProduct(Product product);
}
