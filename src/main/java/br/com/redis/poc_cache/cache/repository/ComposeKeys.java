package br.com.redis.poc_cache.cache.repository;

public interface ComposeKeys<K, V> {
  V composeKeys(K key);
}
