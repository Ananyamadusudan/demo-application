package com.example.demo.repo;

import com.example.demo.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Long> {

  List<Ratings> findAllByProductId(Integer productId);

  Ratings findByProductIdAndUserId(Integer productId, Integer userId);
}