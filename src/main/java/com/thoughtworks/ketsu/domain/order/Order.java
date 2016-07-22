package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.Date;
import java.util.Map;

public class Order implements Record {
  private int id;
  private int userId;
  private String name;
  private String address;
  private String phone;
  private Date time;

  public Order() {
  }

  public Order(int id, int userId, String name, String address, String phone, Date time) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.time = time;
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

  @Override
  public Map<String, Object> toRefJson(Routes routes) {
    return null;
  }

  @Override
  public Map<String, Object> toJson(Routes routes) {
    return null;
  }
}
