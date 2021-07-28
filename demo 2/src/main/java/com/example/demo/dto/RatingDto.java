package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ananyamadhusudan
 */
@Data
public class RatingDto {
  @NotNull(message = "PRODUCT ID CANNOT BE NULL")
  private Integer productId;
  @NotNull(message = "RATING CANNOT BE NULL")
  private Integer rating;
  @NotNull(message = "USER ID CANNOT BE NULL")
  private Integer userId;
}
