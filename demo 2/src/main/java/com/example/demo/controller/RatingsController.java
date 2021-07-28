package com.example.demo.controller;

import com.example.demo.dto.RatingDto;
import com.example.demo.service.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ananyamadhusudan
 */
@RestController
@RequestMapping("/v1/ratings")
public class RatingsController {
  private final RatingsService ratingsService;

  @Autowired
  public RatingsController(RatingsService ratingsService) {
    this.ratingsService = ratingsService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getAllProductRatings(@PathVariable("id") Integer productId) {
    return new ResponseEntity<>(ratingsService.getAllRatingsForProduct(productId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> saveRatings(@Valid @RequestBody RatingDto ratingDto) {
    boolean isSaved = ratingsService.saveRatingForProduct(ratingDto);
    if (isSaved) {
      return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }
    return new ResponseEntity<>("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @PutMapping
  public ResponseEntity<?> updateRatings(@Valid @RequestBody RatingDto ratingDto) {
    boolean isSaved = ratingsService.updateProductRating(ratingDto);
    if (isSaved) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
