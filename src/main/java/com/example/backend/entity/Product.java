package com.example.backend.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @SequenceGenerator(name = "id",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id")
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private int price;
    private String image;

    @ManyToOne
    @JoinColumn(name = "supplierId", updatable = false, referencedColumnName = "id")
    private User user;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
