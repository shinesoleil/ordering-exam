package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(DatabaseTestRunner.class)
public class OrderRepositoryTest {

  @Inject
  ProductRepository productRepository;

  @Inject
  UserRepository userRepository;

  @Test
  public void should_create_order_with_items_and_find_order_by_id() {
    Map<String, Object> info = TestHelper.productMap();
    productRepository.create(info);
    int productId = Integer.valueOf(String.valueOf(info.get("id")));

    Map<String, Object> userInfo = TestHelper.userMap();
    userRepository.create(userInfo);
    int userId = Integer.valueOf(String.valueOf(userInfo.get("id")));
    User user = userRepository.findById(userId).get();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    user.placeOrder(orderInfo);
    int orderId = Integer.valueOf(String.valueOf(orderInfo.get("id")));
    Order order = user.findOrderById(orderId).get();

    assertThat(order.getId(), is(orderId));
    assertThat(order.getOrderItems().size(), is(1));
    assertThat(order.getOrderItems().get(0).getQuantity(), is(2));
  }

  @Test
  public void should_find_orders() {
    Map<String, Object> info = TestHelper.productMap();
    productRepository.create(info);
    int productId = Integer.valueOf(String.valueOf(info.get("id")));

    Map<String, Object> userInfo = TestHelper.userMap();
    userRepository.create(userInfo);
    int userId = Integer.valueOf(String.valueOf(userInfo.get("id")));
    User user = userRepository.findById(userId).get();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    user.placeOrder(orderInfo);
    int orderId = Integer.valueOf(String.valueOf(orderInfo.get("id")));

    List<Order> orderList = user.findOrders();

    assertThat(orderList.size(), is(1));
    assertThat(orderList.get(0).getId(), is(orderId));
  }
}
