package com.ivanfranchin.movieapi.rest.dto;

import java.math.BigDecimal;

public record ProductDto(String imdb, String title, BigDecimal price, Double discount, String imageUrl, Double rating,
		String category, String slug, String createdAt) {}
