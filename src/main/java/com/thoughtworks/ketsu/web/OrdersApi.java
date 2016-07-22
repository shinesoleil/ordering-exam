package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Path("users/{userId}/orders")
public class OrdersApi {

  @Context
  UserRepository userRepository;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createOrder(HashMap<String, Object> info,
                              @PathParam("userId") int userId) {
    List<String> invalidParamList = new ArrayList();
    if (info.get("name") == null) {
      invalidParamList.add("name");
    }
    if (info.get("address") == null) {
      invalidParamList.add("address");
    }
    if (info.get("phone") == null) {
      invalidParamList.add("phone");
    }
    if (info.get("time") == null) {
      invalidParamList.add("time");
    }
    if (invalidParamList.size() > 0) {
      throw new InvalidParameterException(invalidParamList);
    }

    User user = userRepository.findById(userId).get();

    Optional<Order> orderOptional = user.placeOrder(info);

    if (orderOptional.isPresent()) {
      return Response.created(new Routes().orderUrl(orderOptional.get())).build();
    } else {
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }
}
