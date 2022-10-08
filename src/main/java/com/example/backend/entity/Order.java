package com.example.backend.entity;

import com.example.backend.enums.Role;
import com.example.backend.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @SequenceGenerator(name = "orderId", allocationSize = 1)
    @GeneratedValue(generator = "orderId", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer totalPrice;

    @ManyToOne
    @JoinColumn(name = "customerId", updatable = false, referencedColumnName = "id")
    private User user;

    @OneToOne
    private Product product;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.PENDING;
}
