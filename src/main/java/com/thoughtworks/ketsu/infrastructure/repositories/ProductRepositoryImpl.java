package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.ProductMapper;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

  @Inject
  ProductMapper productMapper;

  @Override
  public Optional<Product> create(Map<String, Object> info) {
    productMapper.save(info);
    int id = Integer.valueOf(String.valueOf(info.get("id")));
    return Optional.ofNullable(productMapper.findById(id));
  }

  @Override
  public Optional<Product> findById(int id) {
    return Optional.ofNullable(productMapper.findById(id));
  }
}