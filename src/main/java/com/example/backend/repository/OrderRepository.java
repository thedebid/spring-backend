package com.example.backend.repository;

import com.example.backend.dto.GetOrderResponse;
import com.example.backend.dto.OrderResponse;
import com.example.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query(value = "select product.*,o.id, o.quantity, o.total_price as totalPrice, o.created_at as orderDate, o.customer_id as customerId,o.status, u.full_name as customerName from orders o inner join users u on o.customer_id=u.id inner join (select p.id as productId, p.name as productName,p.price as productPrice, p.supplier_id as supplierId, u.full_name as supplierName from products p inner join users u on p.supplier_id=u.id ) as product on o.product_id=product.productId", nativeQuery = true)
    List<GetOrderResponse> getAllOrders();
    @Query(value = "select product.*,o.id, o.quantity, o.total_price as totalPrice, o.created_at as orderDate, o.customer_id as customerId,o.status, u.full_name as customerName from orders o inner join users u on o.customer_id=u.id inner join (select p.id as productId, p.name as productName,p.price as productPrice, p.supplier_id as supplierId, u.full_name as supplierName from products p inner join users u on p.supplier_id=u.id ) as product on o.product_id=product.productId where product.supplierId=?1",nativeQuery = true)
    List<GetOrderResponse> getOrdersBySupplierId(Long supplierId);

    @Query(value = "select product.*,o.id, o.quantity, o.total_price as totalPrice, o.created_at as orderDate, o.customer_id as customerId,o.status, u.full_name as customerName from orders o inner join users u on o.customer_id=u.id inner join (select p.id as productId, p.name as productName,p.price as productPrice, p.supplier_id as supplierId, u.full_name as supplierName from products p inner join users u on p.supplier_id=u.id ) as product on o.product_id=product.productId where o.customer_id=?1",nativeQuery = true)
    List<GetOrderResponse> getOrdersByUserId(Long userId);


    @Query(value="SELECT COUNT(*) as totalPrice from orders o  left join products p  on o.product_id=p.id where p.supplier_id=?1", nativeQuery=true)
    Optional<Integer> getOrderSummary(Long id);
    @Query(value="SELECT COUNT(*) as totalPrice from orders o  left join products p  on o.product_id=p.id where p.supplier_id=?1 and o.created_at  > ?2", nativeQuery=true)
    Optional<Integer> getOrderSummaryByDate(Long id, LocalDate todayDate);

    @Query(value="SELECT COUNT(*) as totalPrice from products p where p.supplier_id=?1", nativeQuery=true)
    Optional<Integer> getProductSummary(Long id);
    @Query(value="SELECT COUNT(*) as totalPrice from products p  where p.supplier_id=?1 and p.created_at  > ?2", nativeQuery=true)
    Optional<Integer> getProductsSummaryByDate(Long id, LocalDate todayDate);


    @Query(value="SELECT SUM(total_price) as totalPrice from orders o  left join products p  on o.product_id=p.id where p.supplier_id=?1", nativeQuery=true)
    Optional<Integer> getAmountSummary(Long id);
    @Query(value="SELECT SUM(total_price) as totalPrice from orders o  left join products p  on o.product_id=p.id where p.supplier_id=?1 and o.created_at  > ?2", nativeQuery=true)
    Optional<Integer> getAmountSummaryByDate(Long id, LocalDate todayDate);

}
