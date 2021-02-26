package com.task.cafemanager.data.entities;

import com.task.cafemanager.data.entities.enums.ProductInOrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product_in_order")
public class ProductInOrder {

    @Id
    @SequenceGenerator(name = "productInOrder_sequence", sequenceName = "productInOrder_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productInOrder_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "orderName")
    private String orderName;

    @Column(name = "amount")
    private int amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductInOrderStatus status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Order order;
}
