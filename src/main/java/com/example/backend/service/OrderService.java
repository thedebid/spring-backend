package com.example.backend.service;

import com.example.backend.dto.GetOrderResponse;
import com.example.backend.dto.OrderRequest;
import com.example.backend.dto.OrderResponse;
import com.example.backend.dto.SummaryResponse;
import com.example.backend.entity.Order;
import com.example.backend.entity.Product;
import com.example.backend.enums.Status;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.image.RescaleOp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private ProductService productService;
    private UserRepository userRepository;

    public OrderResponse saveOrder(OrderRequest order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        com.example.backend.entity.User loggedInUser = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Supplier not found with username or email:" + user.getUsername()));

        Product product = productService.getProductDataById(order.getProductId());

        product.setStock(product.getStock() - order.getQuantity());

        productRepository.save(product);


        Order productOrder = new Order();
        productOrder.setProduct(product);
        productOrder.setUser(loggedInUser);
        productOrder.setQuantity(order.getQuantity());
        productOrder.setTotalPrice(order.getTotalPrice());

        Order orderResponse = orderRepository.save(productOrder);

        OrderResponse newOrderResponse = new OrderResponse();
        newOrderResponse.setId(orderResponse.getId());
        newOrderResponse.setQuantity(orderResponse.getQuantity());
        newOrderResponse.setTotalPrice(orderResponse.getTotalPrice());
        newOrderResponse.setOrderDate(orderResponse.getCreatedAt());

        newOrderResponse.setSupplierId(orderResponse.getProduct().getUser().getId());
        newOrderResponse.setSupplierName(orderResponse.getProduct().getUser().getFullName());

        newOrderResponse.setCustomerId(orderResponse.getUser().getId());
        newOrderResponse.setCustomerName(orderResponse.getUser().getFullName());

        newOrderResponse.setProductId(orderResponse.getProduct().getId());
        newOrderResponse.setProductName(orderResponse.getProduct().getName());

        newOrderResponse.setStatus(orderResponse.getStatus());
        return newOrderResponse;
    }

    public List<GetOrderResponse> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public List<GetOrderResponse> getOrdersByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        com.example.backend.entity.User loggedInUser = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Supplier not found with username or email:" + user.getUsername()));
        return orderRepository.getOrdersByUserId(loggedInUser.getId());
    }

    public List<GetOrderResponse> getOrderBySupplierId(Long supplierId) {
        return orderRepository.getOrdersBySupplierId(supplierId);
    }

    public List<GetOrderResponse> getOrderBySupplier() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        com.example.backend.entity.User loggedInUser = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Supplier not found with username or email:" + user.getUsername()));
        return orderRepository.getOrdersBySupplierId(loggedInUser.getId());
    }

    public List<GetOrderResponse> getOrderByCustomerId(Long customerId) {
        return orderRepository.getOrdersByUserId(customerId);
    }

    public List<GetOrderResponse> updateOrderStatus(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new Error("Product not found with id " + id));
        order.setStatus(Status.COMPLETED);
        orderRepository.save(order);
        return this.getOrderBySupplier();
    }

    public SummaryResponse getSummary() {

        LocalDate todayDate = LocalDate.now();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        com.example.backend.entity.User loggedInUser = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Supplier not found with username or email:" + user.getUsername()));


        Optional<Integer> ordersCountByDate = orderRepository.getOrderSummaryByDate(loggedInUser.getId(),todayDate);
        Optional<Integer> ordersCount = orderRepository.getOrderSummary(loggedInUser.getId());

        SummaryResponse summaryResponse = new SummaryResponse();
        summaryResponse.setTotalNewTransaction(ordersCountByDate);
        summaryResponse.setTotalTransaction(ordersCount);

        Optional<Integer> productsCountByDate = orderRepository.getProductsSummaryByDate(loggedInUser.getId(),todayDate);
        Optional<Integer> productsCount = orderRepository.getProductSummary(loggedInUser.getId());
        summaryResponse.setTotalProducts(productsCount);
        summaryResponse.setTotalNewProducts(productsCountByDate);





        Optional<Integer> amountByDate = orderRepository.getAmountSummaryByDate(loggedInUser.getId(),todayDate);
        Optional<Integer> amountCount = orderRepository.getAmountSummary(loggedInUser.getId());
        summaryResponse.setTotalAmount(amountCount);
        summaryResponse.setTotalNewAmount(amountByDate);


         return summaryResponse;
    }
}
