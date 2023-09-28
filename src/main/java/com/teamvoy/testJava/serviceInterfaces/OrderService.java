package com.teamvoy.testJava.serviceInterfaces;

import com.teamvoy.testJava.dto.OrderDTO;
import com.teamvoy.testJava.models.Order;

import java.util.List;

public interface OrderService {
    void prepareOrder(List<OrderDTO> orderDTOS, String customerEmail);

    List<Order> getOrdersByUserEmail(String email);

    void payOrder(long id);
}
