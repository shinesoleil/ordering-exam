package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.domain.payment.Payment;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.PaymentMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.*;

public class Order implements Record {

  @Inject
  PaymentMapper paymentMapper;

  private int id;
  private int userId;
  private String name;
  private String address;
  private String phone;
  private Date time;
  private List<OrderItem> orderItems;

  public Order() {
  }

  public Order(int id, int userId, String name, String address, String phone, Date time, List<OrderItem> orderItems) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.time = time;
    this.orderItems = orderItems;
  }

  public int getId() {
    return id;
  }

  public int getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }

  public Date getTime() {
    return time;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public Optional<Payment> pay(Map<String, Object> info) {
    paymentMapper.save(info);
    return Optional.ofNullable(paymentMapper.findByOrderId(this.id));
  }

  public Optional<Payment> findPaymentByOrderId(int orderId) {
    return Optional.ofNullable(paymentMapper.findByOrderId(orderId));
  }


  public double getTotalPrice() {
    double totalPrice = 0;
    for (OrderItem orderItem: orderItems) {
      totalPrice += orderItem.getAmount();
    }
    return totalPrice;
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return new HashMap<String, Object>() {{
      put("uri", new Routes().orderUrl(Order.this));
      put("name", getName());
      put("address", getAddress());
      put("phone", getPhone());
      put("total_price", getTotalPrice());
      put("created_at", getTime());
    }};
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap<String, Object>() {{
      put("uri", new Routes().orderUrl(Order.this));
      put("name", getName());
      put("address", getAddress());
      put("phone", getPhone());
      put("total_price", getTotalPrice());
      put("created_at", getTime());
      put("order_items", getOrderItems());
    }};
  }
}
