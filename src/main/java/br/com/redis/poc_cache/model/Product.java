package br.com.redis.poc_cache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

  private Long id;
  private String name;
  private String description;
  private Double price;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDate bestBefore;
  private Long expiration;
}
