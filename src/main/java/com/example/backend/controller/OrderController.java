package com.example.backend.controller;

import com.example.backend.dto.GetOrderResponse;
import com.example.backend.dto.OrderRequest;
import com.example.backend.dto.OrderResponse;
import com.example.backend.dto.SummaryResponse;
import com.example.backend.entity.Order;
import com.example.backend.entity.User;
import com.example.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private OrderService orderService;

    @GetMapping("/")
    public List<GetOrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/byCustomer/{customerId}")
    public List<GetOrderResponse> getOrderByUserId(@PathVariable("customerId") Long customerId){
        return orderService.getOrderByCustomerId(customerId);
    }

    @GetMapping("/bySupplier")
    public List<GetOrderResponse> getOrderBySupplier(){
        return orderService.getOrderBySupplier();
    }

    @GetMapping("/byCustomer")
    public List<GetOrderResponse> getOrderByCustomer(){
        return orderService.getOrdersByCustomer();
    }


    @GetMapping("/bySupplier/{supplierId}")
    public List<GetOrderResponse> getOrderBySupplierId(@PathVariable("supplierId") Long supplierId){
        return orderService.getOrderBySupplierId(supplierId);
    }


    @PostMapping("/")
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest order) {
        System.out.println("the order is: " + order);
        return new ResponseEntity<OrderResponse>(orderService.saveOrder(order), HttpStatus.CREATED);
    }
    @PutMapping("/status/{id}")
    public List<GetOrderResponse> updateOrderStatus(@PathVariable("id") Long orderId){
        return  orderService.updateOrderStatus(orderId);
    }

    @GetMapping("/summary")
    public SummaryResponse getSummary(){
         return orderService.getSummary();
    }




}
