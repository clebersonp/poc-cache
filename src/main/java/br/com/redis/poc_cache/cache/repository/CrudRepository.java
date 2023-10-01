package br.com.redis.poc_cache.cache.repository;

import java.util.Optional;

public interface CrudRepository<T,ID> {
  T save(T entity);

  Optional<T> findById(ID id);

  void deleteById(ID id);
}
