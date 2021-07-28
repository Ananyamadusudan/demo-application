package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author ananyamadhusudan
 */
@Data
@Builder
public class RatingWrapperDto {
  private List<ProductRatingDto> productRatingDtoList;
  private Double averageRating;
  private Integer count;
}
