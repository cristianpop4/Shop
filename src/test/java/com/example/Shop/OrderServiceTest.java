package com.example.Shop;

import com.example.Shop.dto.OrderDTO;
import com.example.Shop.dto.OrderRequestDTO;
import com.example.Shop.entity.Order;
import com.example.Shop.entity.Product;
import com.example.Shop.entity.User;
import com.example.Shop.repository.OrderRepository;
import com.example.Shop.repository.ProductRepository;
import com.example.Shop.repository.UserRepository;
import com.example.Shop.service.serviceimpl.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderSuccessfully() {

        User user = new User(1L, "John", "john@mail.com", null);

        Product product1 =
                new Product(1L, "Laptop", "Gaming laptop", 2000.0, 5, null);

        Product product2 =
                new Product(2L, "Mouse", "Wireless mouse", 100.0, 10, null);

        OrderRequestDTO request =
                new OrderRequestDTO(1L, List.of(1L, 2L));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(productRepository.findAllById(List.of(1L, 2L)))
                .thenReturn(List.of(product1, product2));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setId(10L); // simulate DB auto-id
                    order.setCreatedAt(new Date());
                    return order;
                });

        OrderDTO result = orderService.createOrder(request);

        assertNotNull(result);

        assertEquals(2100.0, result.total());

        assertEquals("John", result.customerName());

        assertEquals(2, result.productNames().size());
        assertTrue(result.productNames().contains("Laptop"));
        assertTrue(result.productNames().contains("Mouse"));

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        OrderRequestDTO request =
                new OrderRequestDTO(99L, List.of(1L));

        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> orderService.createOrder(request));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllOrders() {

        User user = new User(1L, "Alice", "alice@mail.com", null);

        Product product =
                new Product(1L, "Keyboard", "Mechanical", 300.0, 3, null);

        Order order = new Order(
                1L,
                user,
                List.of(product),
                300.0,
                new Date()
        );

        when(orderRepository.findAll())
                .thenReturn(List.of(order));

        List<OrderDTO> result = orderService.getAllOrders();

        assertEquals(1, result.size());

        OrderDTO dto = result.get(0);

        assertEquals("Alice", dto.customerName());
        assertEquals("Keyboard", dto.productNames().get(0));
        assertEquals(300.0, dto.total());

        verify(orderRepository).findAll();
    }
}