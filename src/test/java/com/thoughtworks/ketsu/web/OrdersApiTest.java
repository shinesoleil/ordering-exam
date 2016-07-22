package com.thoughtworks.ketsu.web;

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
import java.util.List;
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
  public void should_return_list_of_order_json_when_get_orders() {
    Map<String, Object> info = TestHelper.productMap();
    productRepository.create(info);
    int productId = Integer.valueOf(String.valueOf(info.get("id")));

    Map<String, Object> userInfo = TestHelper.userMap();
    userRepository.create(userInfo);
    int userId = Integer.valueOf(String.valueOf(userInfo.get("id")));
    User user = userRepository.findById(userId).get();

    Map<String, Object> orderInfo = TestHelper.orderMap(productId);
    user.placeOrder(orderInfo);

    Response get = get("users/" + userId + "/orders");
    List<Map<String, Object>> mapList = get.readEntity(List.class);

    assertThat(get.getStatus(), is(200));
    assertThat(mapList.size(), is(1));
    assertThat(mapList.get(0).get("name"), is("firstOrder"));
  }

  @Test
  public void should_return_order_json_when_get_order_by_id() {
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

    Response get = get("users/" + userId + "/orders/" + orderId);
    Map<String, Object> map = get.readEntity(Map.class);

    assertThat(get.getStatus(), is(200));
    assertThat(map.get("name"), is("firstOrder"));
  }

  @Test
  public void should_return_404_when_get_json_by_id_not_found() {
    Map<String, Object> userInfo = TestHelper.userMap();
    userRepository.create(userInfo);
    int userId = Integer.valueOf(String.valueOf(userInfo.get("id")));

    Response get = get("users/" + userId + "/orders/1");

    assertThat(get.getStatus(), is(404));
  }
}
