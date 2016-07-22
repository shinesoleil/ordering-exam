package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Path("users")
public class UsersApi {

  @Context
  UserRepository userRepository;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(HashMap<String, Object> info) {
    List<String> invalidParamList = new ArrayList();
    if (info.get("name") == null) {
      invalidParamList.add("name");
    }

    if (invalidParamList.size() > 0) {
      throw new InvalidParameterException(invalidParamList);
    }

    Optional<User> userOptional = userRepository.create(info);

    if (userOptional.isPresent()) {
      return Response.created(new Routes().userUrl(userOptional.get())).build();
    } else {
      throw new WebApplicationException(Response.Status.BAD_REQUEST);
    }
  }
}
