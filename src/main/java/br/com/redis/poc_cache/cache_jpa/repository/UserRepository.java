package br.com.redis.poc_cache.cache_jpa.repository;

import br.com.redis.poc_cache.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
}
