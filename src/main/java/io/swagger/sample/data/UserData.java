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

package io.swagger.sample.data;

import io.swagger.sample.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserData {
  static Map<Long, User> users = Collections.synchronizedMap(new LinkedHashMap<Long, User>());

  static {
    users.put(1L, createUser(1, "user1", "first name 1", "last name 1",
        "email1@test.com", "123-456-7890", 1));
    users.put(2L, createUser(2, "user2", "first name 2", "last name 2",
        "email2@test.com", "123-456-7890", 2));
    users.put(3L, createUser(3, "user3", "first name 3", "last name 3",
        "email3@test.com", "123-456-7890", 3));
    users.put(4L, createUser(4, "user4", "first name 4", "last name 4",
        "email4@test.com", "123-456-7890", 1));
    users.put(5L, createUser(5, "user5", "first name 5", "last name 5",
        "email5@test.com", "123-456-7890", 2));
    users.put(6L, createUser(6, "user6", "first name 6", "last name 6",
        "email6@test.com", "123-456-7890", 3));
    users.put(7L, createUser(7, "user7", "first name 7", "last name 7",
        "email7@test.com", "123-456-7890", 1));
    users.put(8L, createUser(8, "user8", "first name 8", "last name 8",
        "email8@test.com", "123-456-7890", 2));
    users.put(9L, createUser(9, "user9", "first name 9", "last name 9",
        "email9@test.com", "123-456-7890", 3));
    users.put(10L, createUser(10, "user10", "first name 10", "last name 10",
        "email10@test.com", "123-456-7890", 1));
    users.put(11L, createUser(11, "user?10", "first name ?10", "last name ?10",
        "email101@test.com", "123-456-7890", 1));

  }

  public User findUserByName(String username) {
    for (User user : users.values()) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  public void addUser(User user) {
    if(user.getUsername() == null)
      return;
    if(user.getId() <1) {
      long maxId = 0;
      for (Long userId: users.keySet()) {
        if(userId > maxId) {
          maxId = userId;
        }
      }
      long newId = maxId > Long.MAX_VALUE -1 ? maxId : maxId + 1;
      user.setId(newId);
    }
    users.put(user.getId(), user);
    if (users.size() > PetData.MAX_SIZE) {
      Long id = users.keySet().iterator().next();
      users.remove(id);
    }
  }

  public boolean removeUser(String username) {
    if (users.isEmpty() || StringUtils.isBlank(username)) {
      return false;
    }
    for (User user : users.values()) {
      if (username.equals(user.getUsername())) {
        return users.remove(user.getId()) != null;
      }
    }
    return false;
  }

  private static User createUser(long id, String username, String firstName,
      String lastName, String email, String phone, int userStatus) {
    User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setPassword("XXXXXXXXXXX");
    user.setPhone(phone);
    user.setUserStatus(userStatus);
    return user;
  }
}
