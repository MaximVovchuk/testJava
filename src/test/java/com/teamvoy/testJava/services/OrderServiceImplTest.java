package com.teamvoy.testJava.services;

import com.teamvoy.testJava.dto.OrderDTO;
import com.teamvoy.testJava.models.Good;
import com.teamvoy.testJava.models.Order;
import com.teamvoy.testJava.models.OrderPosition;
import com.teamvoy.testJava.models.User;
import com.teamvoy.testJava.repositories.OrderPositionRepository;
import com.teamvoy.testJava.repositories.OrderRepository;
import com.teamvoy.testJava.repositories.UserRepository;
import com.teamvoy.testJava.serviceInterfaces.GoodsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.ArgumentCaptor;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private GoodsService goodsService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderPositionRepository orderPositionRepository;

    @Test
    public void testPrepareOrder_SuccessfulOrder() {
        OrderDTO orderDTO1 = new OrderDTO(1L, 2);
        OrderDTO orderDTO2 = new OrderDTO(2L, 3);
        List<OrderDTO> orderDTOS = List.of(orderDTO1, orderDTO2);
        String customerEmail = "test@example.com";

        User user = new User();
        user.setEmail(customerEmail);

        when(userRepository.findByEmail(customerEmail)).thenReturn(user);
        when(goodsService.getGoodById(1L)).thenReturn(new Good(1L, "Good 1", 10.0));
        when(goodsService.getGoodById(2L)).thenReturn(new Good(2L, "Good 2", 20.0));

        orderService.prepareOrder(orderDTOS, customerEmail);

        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(2)).save(orderCaptor.capture());

        List<Order> capturedOrders = orderCaptor.getAllValues();
        assertEquals(2, capturedOrders.size());

        Order savedOrder = capturedOrders.get(0);
        assertFalse(savedOrder.isPaid());
        assertEquals(user, savedOrder.getUser());
        assertEquals(2, savedOrder.getOrderPositions().size());
        assertEquals(80.0, savedOrder.getFullPrice());

        ArgumentCaptor<OrderPosition> orderPositionCaptor = ArgumentCaptor.forClass(OrderPosition.class);
        verify(orderPositionRepository, times(2)).save(orderPositionCaptor.capture());
    }

    @Test
    public void testGetOrdersByUserEmail() {
        String userEmail = "test@example.com";
        List<Order> expectedOrders = new ArrayList<>();
        when(orderRepository.findAllByUserEmail(userEmail)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.getOrdersByUserEmail(userEmail);

        assertSame(expectedOrders, actualOrders);
    }

    @Test
    public void testPayOrder() {
        long orderId = 1L;
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.payOrder(orderId);

        assertTrue(order.isPaid());
        verify(orderRepository).save(order);
    }

    @Test
    public void testScheduledDeleteNotPaidOrders() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Order unpaidOrder = new Order();
        unpaidOrder.setCreatedAt(now.minusMinutes(11));
        Order paidOrder = new Order();
        paidOrder.setPaid(true);
        paidOrder.setCreatedAt(now.minusMinutes(9));

        List<Order> allOrders = List.of(unpaidOrder, paidOrder);
        when(orderRepository.findAll()).thenReturn(allOrders);
        doNothing().when(orderRepository).delete(unpaidOrder);

        orderService.deleteNotPaidOrdersAfter10Minutes();

        verify(orderRepository).delete(unpaidOrder);
        verify(orderRepository, never()).delete(paidOrder);
    }
}