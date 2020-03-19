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

import io.swagger.sample.model.Order;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class StoreData {

  static Map<Long, Order> orders = Collections.synchronizedMap(new LinkedHashMap<Long, Order>());

  static {
    orders.put(1L, createOrder(1, 1, 2, new Date(), "placed"));
    orders.put(2L, createOrder(2, 1, 2, new Date(), "delivered"));
    orders.put(3L, createOrder(3, 2, 2, new Date(), "placed"));
    orders.put(4L, createOrder(4, 2, 2, new Date(), "delivered"));
    orders.put(5L, createOrder(5, 3, 2, new Date(), "placed"));
    orders.put(6L, createOrder(6, 3, 2, new Date(), "placed"));
    orders.put(7L, createOrder(7, 3, 2, new Date(), "placed"));
    orders.put(8L, createOrder(8, 3, 2, new Date(), "placed"));
    orders.put(9L, createOrder(9, 3, 2, new Date(), "placed"));
    orders.put(10L, createOrder(10, 3, 2, new Date(), "placed"));
  }

  public Order findOrderById(long orderId) {

    return orders.get(Long.valueOf(orderId));

  }

  public Order placeOrder(Order order) {
    if(order.getId() <1) {
      long maxId = 0;
      for (Long orderId: orders.keySet()) {
        if(orderId > maxId) {
          maxId = orderId;
        }
      }
      long newId = maxId > Long.MAX_VALUE -1 ? maxId : maxId + 1;
      order.setId(newId);
    }
    orders.put(order.getId(), order);
    if (orders.size() > PetData.MAX_SIZE) {
      Long id = orders.keySet().iterator().next();
      orders.remove(id);
    }

    return order;
  }

  public boolean deleteOrder(long orderId) {
    return orders.remove(Long.valueOf(orderId)) == null ? false : true;
  }


  private static Order createOrder(long id, long petId, int quantity,
      Date shipDate, String status) {
    Order order = new Order();
    order.setId(id);
    order.setPetId(petId);
    order.setQuantity(quantity);
    order.setShipDate(shipDate);
    order.setStatus(status);
    return order;
  }
}
