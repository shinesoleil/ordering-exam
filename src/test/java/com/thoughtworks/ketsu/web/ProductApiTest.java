package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport{

  @Test
  public void should_return_url_location_when_post_product_with_parameters() {
    Map<String, Object> info = TestHelper.productMap();

    Response post = post("products", info);

    assertThat(post.getStatus(), is(201));
    assertThat(Pattern.matches(".*products/[0-9]+.*", post.getLocation().toASCIIString()), is(true));
  }

  @Test
  public void should_return_400_when_post_product_with_invalid_params() {
    Map<String, Object> info = TestHelper.productMap();
    info.replace("name", null);

    Response post = post("products", info);

    assertThat(post.getStatus(), is(400));
  }

}
