package com.example.demo.builder;

import com.example.demo.dto.ProductRatingDto;
import com.example.demo.dto.RatingDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.Ratings;
import com.example.demo.entity.User;

/**
 * @author ananyamadhusudan
 */
public class ProductRatingDtoBuilder {

  public static ProductRatingDto buildDtoFromEntity(Ratings ratings,
                                                    Product product,
                                                    User user) {
    ProductRatingDto productRatingDto = ProductRatingDto.builder()
        .rating(ratings.getRatingValue())
        .productName(product.getProductName())
        .userName(user.getUserName())
        .build();
    return productRatingDto;
  }

  public static Ratings buildEntityFromDto(RatingDto ratingDto) {
    Ratings ratings = new Ratings();
    ratings.setProductId(ratingDto.getProductId());
    ratings.setRatingValue(ratingDto.getRating());
    ratings.setUserId(ratingDto.getUserId());
    return ratings;
  }
}
