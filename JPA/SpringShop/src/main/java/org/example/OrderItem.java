package org.example;

import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "order_price")
    private Double orderPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int count;

}
