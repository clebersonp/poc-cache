package br.com.redis.poc_cache.controller;

import br.com.redis.poc_cache.model.Product;
import br.com.redis.poc_cache.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(ProductController.PRODUCTS)
public class ProductController {

  public static final String PRODUCTS = "/products";
  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody Product product) {
    Product productCreated = this.service.create(product);
    Map<String, Object> map = new HashMap<>();
    map.put("id", productCreated.getId());
    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(PRODUCTS + "/{id}")
        .buildAndExpand(map).toUri();
    return ResponseEntity.created(location).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> get(@PathVariable("id") Long id) {
    Optional<Product> productOpt = this.service.getById(id);
    return productOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
    this.service.deleteById(id);
    return ResponseEntity.ok().build();
  }

}
