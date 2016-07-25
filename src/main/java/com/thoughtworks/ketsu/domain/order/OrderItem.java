package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.ProductMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class OrderItem implements Record {

  @Inject
  ProductMapper productMapper;

  private int orderId;
  private int productId;
  private int quantity;
  private double amount;
  private double order_id;
  private double product_id;

  public OrderItem() {
  }

  public OrderItem(int orderId, int productId, int quantity, double amount) {
    this.orderId = orderId;
    this.productId = productId;
    this.quantity = quantity;
    this.amount = amount;
  }

  public String getOrder_id() {
    return String.valueOf(orderId);
  }

  public String getProduct_id() {
    return String.valueOf(productId);
  }

  public int getOrderId() {
    return orderId;
  }

  public int getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getAmount() {
    return amount;
  }

//  public Product getProduct() {
//    return productMapper.findById(getProductId());
//  }

  public String getUri() {
    return "products";
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return new HashMap<String, Object>() {{
//      put("hahaha", new Routes().productUrl(getProduct()));
      put("product_id", getProductId());
      put("quantity", getQuantity());
      put("amount", getAmount());
    }};
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap<String, Object>() {{
//      put("uri", new Routes().productUrl(getProduct()));
      put("product_id", getProductId());
      put("quantity", getQuantity());
      put("amount", getAmount());
    }};
  }
}
