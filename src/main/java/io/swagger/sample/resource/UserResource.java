/**
 *  Copyright 2016 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.swagger.sample.resource;

import io.swagger.annotations.*;
import io.swagger.sample.data.UserData;
import io.swagger.sample.exception.ApiException;
import io.swagger.sample.exception.NotFoundException;
import io.swagger.sample.model.User;
import io.swagger.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/user")
@Api(value="/user", description = "Operations about user")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResource {
  static UserData userData = new UserData();

  private static Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

  @POST
  @ApiOperation(value = "Create user",
    notes = "This can only be done by the logged in user.",
    position = 1, consumes = "application/json")
  public Response createUser(
      @ApiParam(value = "Created user object", required = true) User user) {
    if (user == null) {
      return Response.status(405).entity(new io.swagger.sample.model.ApiResponse(405, "no data")).build();
    }
    try {
      LOGGER.info("createUser ID {} STATUS {}", user.getId(), user.getUsername());
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("createUser {}", Json.mapper().writeValueAsString(user));
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    userData.addUser(user);
    return Response.ok().entity(new io.swagger.sample.model.ApiResponse(200, String.valueOf(user.getId()))).build();
  }

  @POST
  @Path("/createWithArray")
  @ApiOperation(value = "Creates list of users with given input array",
    position = 2, consumes = "application/json")
  public Response createUsersWithArrayInput(@ApiParam(value = "List of user object", required = true) User[] users) {
    if (users == null) {
      return Response.status(405).entity(new io.swagger.sample.model.ApiResponse(405, "no data")).build();
    }
      try {
        LOGGER.info("createUsersWithArrayInput");
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("createUsersWithArrayInput {}", Json.mapper().writeValueAsString(users));
        }
      } catch (Throwable e) {
        e.printStackTrace();
      }
      for (User user : users) {
          userData.addUser(user);
      }
      return Response.ok().entity(new io.swagger.sample.model.ApiResponse(200, String.valueOf("ok"))).build();
  }

  @POST
  @Path("/createWithList")
  @ApiOperation(value = "Creates list of users with given input array",
    position = 3, consumes = "application/json")
  public Response createUsersWithListInput(@ApiParam(value = "List of user object", required = true) java.util.List<User> users) {
    if (users == null) {
      return Response.status(405).entity(new io.swagger.sample.model.ApiResponse(405, "no data")).build();
    }
      try {
        LOGGER.info("createUsersWithListInput");
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("createUsersWithListInput {}", Json.mapper().writeValueAsString(users));
        }
      } catch (Throwable e) {
        e.printStackTrace();
      }
      for (User user : users) {
          userData.addUser(user);
      }
      return Response.ok().entity(new io.swagger.sample.model.ApiResponse(200, String.valueOf("ok"))).build();
  }

  @PUT
  @Path("/{username}")
  @ApiOperation(value = "Updated user",
    notes = "This can only be done by the logged in user.",
    position = 4, consumes = "application/json")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid user supplied"),
      @ApiResponse(code = 404, message = "User not found") })
  public Response updateUser(
      @ApiParam(value = "name that need to be updated", required = true) @PathParam("username") String username,
      @ApiParam(value = "Updated user object", required = true) User user) {
    if (user == null) {
      return Response.status(405).entity(new io.swagger.sample.model.ApiResponse(405, "no data")).build();
    }
    try {
      LOGGER.info("updateUser ID {} STATUS {}", user.getId(), user.getUsername());
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("updateUser {}", Json.mapper().writeValueAsString(user));
      }
    } catch (Throwable e) {
      e.printStackTrace();
    }
    userData.addUser(user);
    return Response.ok().entity(new io.swagger.sample.model.ApiResponse(200, String.valueOf(user.getId()))).build();
  }

  @DELETE
  @Path("/{username}")
  @ApiOperation(value = "Delete user",
    notes = "This can only be done by the logged in user.",
    position = 5)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid username supplied"),
      @ApiResponse(code = 404, message = "User not found") })
  public Response deleteUser(
      @ApiParam(value = "The name that needs to be deleted", required = true) @PathParam("username") String username) {
    LOGGER.debug("deleteUser {}", username);
    if (userData.removeUser(username)) {
          return Response.ok().entity(new io.swagger.sample.model.ApiResponse(200, String.valueOf(username))).build();
      } else {
          return Response.status(Response.Status.NOT_FOUND).build();
      }
  }

  @GET
  @Path("/{username}")
  @ApiOperation(value = "Get user by user name",
    response = User.class,
    position = 0)
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Invalid username supplied"),
      @ApiResponse(code = 404, message = "User not found") })
  public Response getUserByName(
      @ApiParam(value = "The name that needs to be fetched. Use user1 for testing. ", required = true) @PathParam("username") String username)
    throws ApiException {
    LOGGER.debug("getUserByName {}", username);
    User user = userData.findUserByName(username);
    if (null != user) {
      return Response.ok().entity(user).build();
    } else {
      throw new NotFoundException(404, "User not found");
    }
  }

  @GET
  @Path("/login")
  @ApiOperation(value = "Logs user into the system",
    response = String.class,
    position = 6,
    responseHeaders = {
      @ResponseHeader(name = "X-Expires-After", description = "date in UTC when token expires", response = Date.class),
      @ResponseHeader(name = "X-Rate-Limit", description = "calls per hour allowed by the user", response = Integer.class)
    })
  @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid username/password supplied") })
  public Response loginUser(
      @ApiParam(value = "The user name for login", required = true) @QueryParam("username") String username,
      @ApiParam(value = "The password for login in clear text", required = true) @QueryParam("password") String password) {
    LOGGER.debug("loginUser {}", username);
    LOGGER.trace("loginUser {}", password);
    Date date = new Date(System.currentTimeMillis() + 3600000);
    return Response.ok()
      .header("X-Expires-After", date.toString())
      .header("X-Rate-Limit", String.valueOf(5000))
      .entity(new io.swagger.sample.model.ApiResponse(200, "logged in user session:" + System.currentTimeMillis()))
      .build();
  }



  @GET
  @Path("/logout")
  @ApiOperation(value = "Logs out current logged in user session",
    position = 7)
  public Response logoutUser() {
    return Response.ok().entity(new io.swagger.sample.model.ApiResponse(200, String.valueOf("ok"))).build();
  }
}
