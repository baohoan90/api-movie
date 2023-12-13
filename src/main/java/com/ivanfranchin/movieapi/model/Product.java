package com.ivanfranchin.movieapi.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

	@Id
	private String imdb;

	private String title;
	private BigDecimal price;
	private Double discount;
	private String imageUrl;
	private Double rating;
	private String category;
	private String slug;

	private ZonedDateTime createdAt;

	public Product(String imdb, String title, BigDecimal price, Double discount, String imageUrl, Double rating, String category, String slug) {
		this.imdb = imdb;
		this.title = title;
		this.imageUrl = imageUrl;
		this.price = price;
		this.discount = discount;
		this.rating = rating;
		this.category = category;
		this.slug = slug;
	}

	@PrePersist
	public void onPrePersist() {
		createdAt = ZonedDateTime.now();
	}
}
