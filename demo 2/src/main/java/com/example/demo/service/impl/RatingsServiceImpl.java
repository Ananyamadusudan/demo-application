package com.example.demo.service.impl;

import com.example.demo.builder.ProductRatingDtoBuilder;
import com.example.demo.dto.ProductRatingDto;
import com.example.demo.dto.RatingDto;
import com.example.demo.dto.RatingWrapperDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.Ratings;
import com.example.demo.entity.User;
import com.example.demo.repo.ProductRepository;
import com.example.demo.repo.RatingsRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.RatingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ananyamadhusudan
 */

@Service
@Slf4j
public class RatingsServiceImpl implements RatingsService {

  private final RatingsRepository ratingsRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  @Autowired
  public RatingsServiceImpl(RatingsRepository ratingsRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
    this.ratingsRepository = ratingsRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
  }

  @Override
  public RatingWrapperDto getAllRatingsForProduct(Integer productId) {
    List<Ratings> ratings = ratingsRepository.findAllByProductId(productId);
    Product product = productRepository.getById(productId);
    if (!CollectionUtils.isEmpty(ratings)) {
      List<Integer> userIds = ratings.stream().map(Ratings::getUserId)
          .collect(Collectors.toList());
      List<User> users = userRepository.findAllById(userIds);
      Map<Integer,User> userMap = users.stream()
          .collect(Collectors.toMap(User::getId, user -> user));
      List<ProductRatingDto> productRatingDtos = ratings.stream()
          .map(rating -> ProductRatingDtoBuilder.buildDtoFromEntity(rating,
              product, userMap.get(rating.getUserId())))
          .collect(Collectors.toList());
      DoubleSummaryStatistics doubleSummaryStatistics = ratings.stream()
          .map(Ratings::getRatingValue).mapToDouble(Integer::doubleValue)
          .summaryStatistics();
      RatingWrapperDto ratingWrapperDto = RatingWrapperDto.builder()
          .productRatingDtoList(productRatingDtos)
          .averageRating(doubleSummaryStatistics.getAverage())
          .count(productRatingDtos.size())
          .build();
      return ratingWrapperDto;
    }
    log.error("COULD NOT FIND RATINGS FOR THE GIVEN PRODUCT ID :: {}", productId);
    return null;
  }

  /**
   * save the rating for the product. It is assumed that the UI will get the
   * product Id with other API(like product listing) and user ID also(since the user
   * will be logged in)
   * @param ratingDto the ratings of a product
   * @return if entity is saved or not
   */
  @Override
  public boolean saveRatingForProduct(RatingDto ratingDto) {
    try {
      ratingsRepository.save(ProductRatingDtoBuilder.buildEntityFromDto(ratingDto));
      return true;
    } catch (Exception e) {
      log.error("ERROR WHILE SAVING THE RATINGS FOR PRODUCT ID :: {}",
          ratingDto.getProductId());
      return false;
    }
  }

  @Override
  public boolean updateProductRating(RatingDto ratingDto) {
    Ratings ratings = ratingsRepository.findByProductIdAndUserId(ratingDto.getProductId(),
        ratingDto.getUserId());
    if (!ObjectUtils.isEmpty(ratings)) {
      ratings.setRatingValue(ratingDto.getRating());
      ratings.setProductId(ratingDto.getProductId());
      ratings.setUserId(ratingDto.getUserId());
      try {
        ratingsRepository.save(ratings);
      } catch (Exception e) {
        log.error("ERROR WHILE SAVING THE RATINGS FOR PRODUCT ID :: {}",
            ratingDto.getProductId());

      }
      return true;
    }
    log.error("NO PREVIOUS RATING FOUND FOR THIS PRODUCT AND USER");
    return false;
  }
}
