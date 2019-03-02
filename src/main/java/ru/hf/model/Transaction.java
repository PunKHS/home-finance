package ru.hf.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transID;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "TIME", nullable = false)
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @Column(name = "QUANTITY", columnDefinition = "INT", nullable = false)
    private int quantity;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "COMMENTS", nullable = false)
    private String comments;

    public Transaction() {
    }

    public Transaction(User user, Timestamp timestamp, Category category, int quantity, BigDecimal price, String comments) {
        this.user = user;
        this.timestamp = timestamp;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.comments = comments;
    }
}
