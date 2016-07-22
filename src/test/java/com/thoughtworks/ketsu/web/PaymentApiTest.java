package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class PaymentApiTest extends ApiSupport{

  @Inject
  ProductRepository productRepository;

  @Inject
  UserRepository userRepository;

  @Test
  public void should_return_url_location_when_post_payment_with_parameters() {
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

    Map<String, Object> paymentInfo = TestHelper.paymentMap();

    Response post = post("users/" + userId + "/orders/" + orderId + "/payment", paymentInfo);

    assertThat(post.getStatus(), is(201));
    assertThat(Pattern.matches(".*users/[0-9]+/orders/[0-9]+/payment.*", post.getLocation().toASCIIString()), is(true));

  }

  @Test
  public void should_return_400_when_post_with_invalid_Params() {
    Map<String, Object> paymentInfo = TestHelper.paymentMap();
    paymentInfo.replace("pay_type", null);

    Response post = post("users/1/orders/1/payment", paymentInfo);

    assertThat(post.getStatus(), is(400));
  }

  @Test
  public void should_return_payment_json_when_get_payment_by_id() {
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

    Map<String, Object> paymentInfo = TestHelper.paymentMap();
    order.pay(paymentInfo);

    Response get = get("users/" + userId + "/orders/" + orderId + "/payment");
    Map<String, Object> map = get.readEntity(Map.class);

    assertThat(get.getStatus(), is(200));
    assertThat(map.get("pay_type"), is("CASH"));
  }
}
