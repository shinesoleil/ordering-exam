package com.thoughtworks.ketsu.domain.user;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.ProductMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class User implements Record {
  @Inject
  OrderMapper orderMapper;

  @Inject
  ProductMapper productMapper;

  private int id;
  private String name;

  public User() {
  }

  public User(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPriceByProductId(int productId) {
    return productMapper.findById(productId).getPrice();
  }

  public Optional<Order> placeOrder(Map<String, Object> info) {
    info.put("user_id", getId());

    for (Map<String, Object> item: (List<Map<String, Object>>)info.get("order_items")) {
      item.put("amount", ( Integer.valueOf(String.valueOf(item.get("quantity")))) *
        getPriceByProductId(Integer.valueOf(String.valueOf(item.get("product_id")))));
    }

    orderMapper.save(info);
    int orderId = Integer.valueOf(String.valueOf(info.get("id")));

    return Optional.ofNullable(orderMapper.findById(orderId));
  }

  public Optional<Order> findOrderById(int orderId) {
    return Optional.ofNullable(orderMapper.findById(orderId));
  }

  public List<Order> findOrders() {
    return orderMapper.find();
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return null;
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap<String, Object>() {{
      put("id", String.valueOf(getId()));
      put("uri", new Routes().userUrl(User.this));
      put("name", getName());
    }};
  }
}