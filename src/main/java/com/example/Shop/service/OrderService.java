package com.example.Shop.service;

import com.example.Shop.dto.OrderDTO;
import com.example.Shop.dto.OrderRequestDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderRequestDTO request);
    List<OrderDTO> getAllOrders();
}
