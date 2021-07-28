package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author ananyamadhusudan
 */
@Data
@Builder
public class ProductRatingDto {
  private Integer rating;
  private String productName;
  private String userName;
}
