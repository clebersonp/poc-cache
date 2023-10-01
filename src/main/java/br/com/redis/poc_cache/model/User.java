package br.com.redis.poc_cache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash(value = User.USERS)
public class User implements Serializable {

  public static final String USERS = "users";

  @Id
  private UUID id;
  private String name;
  private Boolean active;
  private LocalDate birthday;
  private LocalDateTime createdAt;
  @TimeToLive
  private Long expiration;
}
