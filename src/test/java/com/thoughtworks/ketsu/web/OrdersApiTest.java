package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.product.ProductRepository;
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
public class OrdersApiTest extends ApiSupport{

  @Inject
  ProductRepository productRepository;

  @Inject
  UserRepository userRepository;

  @Test
  public void should_return_url_location_when_post_order() {
    Map<String, Object> info = TestHelper.productMap();
    productRepository.create(info);
    int productId = Integer.valueOf(String.valueOf(info.get("id")));

    Map<String, Object> userInfo = TestHelper.userMap();
    userRepository.create(userInfo);
    int userId = Integer.valueOf(String.valueOf(userInfo.get("id")));

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);

    Response post = post("users/" + userId + "/orders", orderInfo);

    assertThat(post.getStatus(), is(201));
    assertThat(Pattern.matches(".*users/[0-9]+/orders/[0-9]+.*", post.getLocation().toASCIIString()), is(true));
  }

  @Test
  public void should_return_400_when_post_order_with_invalid_params() {
    Map<String, Object> info = TestHelper.productMap();
    productRepository.create(info);
    int productId = Integer.valueOf(String.valueOf(info.get("id")));

    Map<String, Object> userInfo = TestHelper.userMap();
    userRepository.create(userInfo);
    int userId = Integer.valueOf(String.valueOf(userInfo.get("id")));

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    orderInfo.replace("name", null);

    Response post = post("users/" + userId + "/orders", orderInfo);

    assertThat(post.getStatus(), is(400));
  }

  @Test
  public void should_return_200_when_get_orders() {
    Map<String, Object> info = TestHelper.productMap();
    productRepository.create(info);
    int productId = Integer.valueOf(String.valueOf(info.get("id")));

    Map<String, Object> userInfo = TestHelper.userMap();
    userRepository.create(userInfo);
    int userId = Integer.valueOf(String.valueOf(userInfo.get("id")));

    Response get = get("users/" + userId + "/orders");

    assertThat(get.getStatus(), is(200));
  }
}
