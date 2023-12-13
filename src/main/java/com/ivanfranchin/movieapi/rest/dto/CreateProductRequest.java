package com.ivanfranchin.movieapi.rest.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProductRequest {

    @Schema(example = "tt0117998")
    @NotBlank
    private String imdb;

    @Schema(example = "Twister Orange")
    @NotBlank
    private String title;

    @Schema(example = "https://m.media-amazon.com/images/M/MV5BODExYTM0MzEtZGY2Yy00N2ExLTkwZjItNGYzYTRmMWZlOGEzXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg")
    private String imageUrl;

    @Schema(example = "3.5")
    private Double rating;

    @Schema(example = "100.250")
    private BigDecimal price;

    @Schema(example = "1")
    private String category;

    @Schema(example = "1.5")
    private Double discount;

    @Schema(example = "twister-orange")
    private String slug;

}
