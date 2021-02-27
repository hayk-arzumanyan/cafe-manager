package com.task.cafemanager.data.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@javax.persistence.Table(name = "`table`")
public class Table {

    @Id
    @SequenceGenerator(name = "table_sequence", sequenceName = "table_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_sequence")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "waiter_name")
    private String waiterName;

    @Column(name = "is_assigned")
    private Boolean isAssigned;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();
}
