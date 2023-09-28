package com.teamvoy.testJava.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_position")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPosition {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "good_id", referencedColumnName = "id")
    private Good good;

    @Column(name = "quantity")
    private int quantity;
}
