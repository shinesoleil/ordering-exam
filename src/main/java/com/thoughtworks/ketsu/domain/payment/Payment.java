package com.thoughtworks.ketsu.domain.payment;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.infrastructure.mybatis.mappers.OrderMapper;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class Payment implements Record {
  @Inject
  OrderMapper orderMapper;

  private int orderId;
  private String payType;
  private String payTime;
  private double amount;

  public Payment() {
  }

  public Payment(int orderId, String payType, String payTime, double amount) {
    this.orderId = orderId;
    this.payType = payType;
    this.payTime = payTime;
    this.amount = amount;
  }

  public int getOrderId() {
    return orderId;
  }

  public String getPayType() {
    return payType;
  }

  public String getPayTime() {
    return payTime;
  }

  public double getAmount() {
    return amount;
  }

  public int getUserId() {
    return orderMapper.findById(orderId).getUserId();
  }

  public Order getOrder() {
    return orderMapper.findById(orderId);
  }

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return null;
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return new HashMap<String, Object>() {{
      put("uri", new Routes().paymentUrl(Payment.this, getUserId()));
      put("order_uri", new Routes().orderUrl(getOrder()));
      put("pay_type", getPayType());
      put("pay_time", getPayTime());
      put("amount", getAmount());
    }};
  }
}
