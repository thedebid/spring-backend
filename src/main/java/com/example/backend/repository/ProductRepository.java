package com.example.backend.repository;

import com.example.backend.dto.GetProductResponse;
import com.example.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "select p.*,u.id as supplierId, u.full_name as supplierName from products as p left join users as u on  p.supplier_id = u.id",nativeQuery = true)
    List<GetProductResponse> findAllBy();

    @Query(value = "select p.*,u.id as supplierId, u.full_name as supplierName from products as p left join users as u on  p.supplier_id = u.id where p.supplier_id=?1",nativeQuery = true)
    List<GetProductResponse> findAllBySupplierId(long id);

}
