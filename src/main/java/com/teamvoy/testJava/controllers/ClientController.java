package com.teamvoy.testJava.controllers;

import com.teamvoy.testJava.config.JWTUtil;
import com.teamvoy.testJava.dto.OrderDTO;
import com.teamvoy.testJava.models.Order;
import com.teamvoy.testJava.serviceInterfaces.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@SecurityRequirement(name = "bearer-token")
@Tag(name = "Client")
public class ClientController {
    private final OrderService orderService;
    private final JWTUtil jwtUtil;

    @PostMapping("/order")
    public ResponseEntity order(@RequestBody List<OrderDTO> orderDTOS,
                                @Parameter(hidden = true) @RequestHeader(value = "Authorization") String token) {
        orderService.prepareOrder(orderDTOS, jwtUtil.getEmail(token));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public List<Order> orders(
            @Parameter(hidden = true) @RequestHeader(value = "Authorization") String token) {
        return orderService.getOrdersByUserEmail(jwtUtil.getEmail(token));
    }

    @PostMapping("/order/{id}")
    public ResponseEntity payOrder(@PathVariable long id) {
        orderService.payOrder(id);
        return ResponseEntity.ok().build();
    }
}
