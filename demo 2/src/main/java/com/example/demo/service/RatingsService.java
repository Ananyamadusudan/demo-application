package com.example.demo.service;

import com.example.demo.dto.RatingDto;
import com.example.demo.dto.RatingWrapperDto;

public interface RatingsService {

  RatingWrapperDto getAllRatingsForProduct(Integer productId);

  boolean saveRatingForProduct(RatingDto ratingDto);

  boolean updateProductRating(RatingDto ratingDto);
}
