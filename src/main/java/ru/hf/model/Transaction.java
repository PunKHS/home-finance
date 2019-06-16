package ru.hf.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
@Data
@NoArgsConstructor
public class Transaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "timestamp", nullable = false)
    @JsonView(View.Id.class)
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @Column(name = "quantity", columnDefinition = "INT", nullable = false)
    private int quantity;

    @Positive
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "comments", nullable = false)
    private String comments;
}
