package com.ivanfranchin.movieapi.mapper;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.ivanfranchin.movieapi.model.Product;
import com.ivanfranchin.movieapi.rest.dto.CreateProductRequest;
import com.ivanfranchin.movieapi.rest.dto.ProductDto;

@Service
public class ProductMapperImpl implements ProductMapper {

	@Override
	public Product toProduct(CreateProductRequest request) {
		if (request == null) {
			return null;
		}
		Product product = new Product();
		product.setImdb(request.getImageUrl());
		product.setTitle(request.getTitle());
		product.setPrice(request.getPrice());
		product.setDiscount(request.getDiscount());
		product.setImageUrl(request.getImageUrl());
		product.setCategory(request.getCategory());
		product.setRating(request.getRating());
		product.setSlug(request.getSlug());
		return product;
	}

	@Override
	public ProductDto toProductDto(Product entity) {
		if (entity == null) {
			return null;
		}
		return new ProductDto(entity.getImdb(), entity.getTitle(), entity.getPrice(), entity.getDiscount(),
				entity.getImageUrl(), entity.getRating(), entity.getCategory(), entity.getSlug(),
				DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(entity.getCreatedAt()));
	}
}
