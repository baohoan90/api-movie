package com.ivanfranchin.movieapi.mapper;

import com.ivanfranchin.movieapi.model.Product;
import com.ivanfranchin.movieapi.rest.dto.CreateProductRequest;
import com.ivanfranchin.movieapi.rest.dto.ProductDto;

public interface ProductMapper {

    Product toProduct(CreateProductRequest request);

    ProductDto toProductDto(Product entity);
}