package com.thoughtworks.ketsu.domain.payment;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.Map;

public class Payment implements Record {
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

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return null;
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return null;
  }
}
