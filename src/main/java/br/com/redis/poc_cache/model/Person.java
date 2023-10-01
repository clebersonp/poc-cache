package br.com.redis.poc_cache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements Serializable {

  private UUID id;
  private String name;
  private Boolean active;
  private LocalDateTime createdAt;
  private LocalDate birthday;
  private Long expiration;
}
