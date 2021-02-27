package com.task.cafemanager.data.entities;

import com.task.cafemanager.data.entities.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@javax.persistence.Table(name = "`order`")
public class Order {

    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "order_name")
    private String orderName;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductInOrder> productInOrders = new HashSet<>();
}
