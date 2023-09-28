package com.teamvoy.testJava.services;

import com.teamvoy.testJava.dto.OrderDTO;
import com.teamvoy.testJava.models.Good;
import com.teamvoy.testJava.models.Order;
import com.teamvoy.testJava.models.OrderPosition;
import com.teamvoy.testJava.repositories.OrderPositionRepository;
import com.teamvoy.testJava.repositories.OrderRepository;
import com.teamvoy.testJava.repositories.UserRepository;
import com.teamvoy.testJava.serviceInterfaces.GoodsService;
import com.teamvoy.testJava.serviceInterfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final GoodsService goodsService;
    private final UserRepository userRepository;
    private final OrderPositionRepository orderPositionRepository;

    @Override
    @Transactional
    public void prepareOrder(List<OrderDTO> orderDTOS, String customerEmail) {
        Order order = new Order();
        order.setPaid(false);
        order.setUser(userRepository.findByEmail(customerEmail));
        order.setOrderPositions(new ArrayList<>());
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        double fullPrice = 0;
        for (OrderDTO orderDTO : orderDTOS) {
            Good good = goodsService.getGoodById(orderDTO.getGoodId());
            OrderPosition orderPosition =
                    OrderPosition.builder()
                            .order(order)
                            .good(good)
                            .quantity(orderDTO.getQuantity()).build();
            order.getOrderPositions().add(orderPosition);
            orderPositionRepository.save(orderPosition);
            fullPrice += good.getPrice() * orderDTO.getQuantity();
        }
        order.setFullPrice(fullPrice);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserEmail(String email) {
        return orderRepository.findAllByUserEmail(email);
    }

    @Override
    public void payOrder(long id) {
        Order order = orderRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        order.setPaid(true);
        orderRepository.save(order);
    }

    @Scheduled(fixedRate = 60000)//1 minute
    public void deleteNotPaidOrdersAfter10Minutes() {
        for (Order order : orderRepository.findAll()){
            if (!order.isPaid() && order.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now())){
                orderRepository.delete(order);
            }
        }
    }
}
