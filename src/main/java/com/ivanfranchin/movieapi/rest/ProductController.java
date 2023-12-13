package com.ivanfranchin.movieapi.rest;

import static com.ivanfranchin.movieapi.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivanfranchin.movieapi.mapper.ProductMapper;
import com.ivanfranchin.movieapi.model.Product;
import com.ivanfranchin.movieapi.rest.dto.ProductDto;
import com.ivanfranchin.movieapi.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("public/api/products")
public class ProductController {

	private final ProductMapper productMapper;
	private final ProductService productService;

	@Operation(/* security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) } */)
	@GetMapping
	public List<ProductDto> getProducts(@RequestParam(value = "text", required = false) String text) {

		List<Product> products = (text == null) ? productService.getProducts()
				: productService.getProductsContainingText(text);
		return products.stream().map(productMapper::toProductDto).toList();
	}

	@Operation(/* security = { @SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME) } */)
	@GetMapping("/{slug}")
	public ProductDto getProductDetail(@PathVariable String slug) {
		List<Product> products = productService.getProductsContainingText(slug);
		return products.stream().findFirst().map(productMapper::toProductDto).get();
	}
}
