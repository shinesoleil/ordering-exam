package com.thoughtworks.ketsu.domain.product;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class Product implements Record{
  private int id;
  private String name;
  private String description;
  private double price;

  public Product() {
  }

  public Product(int id, String name, String description, double price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getPrice() {
    return price;
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return new HashMap<String, Object>() {{
      put("id", String.valueOf(getId()));
      put("uri", new Routes().productUrl(Product.this));
      put("name", getName());
      put("description", getDescription());
      put("price", getPrice());
    }};
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap<String, Object>() {{
      put("uri", new Routes().productUrl(Product.this));
      put("name", getName());
      put("description", getDescription());
      put("price", getPrice());
    }};
  }
}
