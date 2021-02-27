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
    @SequenceGenerator(name = "product_in_order_sequence", sequenceName = "product_in_order_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_in_order_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private int amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductInOrderStatus status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Product product;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Order order;
}
