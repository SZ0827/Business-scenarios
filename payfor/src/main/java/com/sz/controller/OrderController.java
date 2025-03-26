package com.sz.controller;

import com.sz.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @RequestMapping("/create")
    public ResponseEntity<Long> createOrder(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int amount) {
        Long orderId = orderService.createOrder(userId, productId, amount);
        return ResponseEntity.ok(orderId);
    }
}
