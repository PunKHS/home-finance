package ru.hf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "CATEGORY")
public class Category {

    @Id
    @Column(name = "CATEGORY_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MAIN_CATEGORY", nullable = false)
    private String mainCategory;

    @Column(name = "SUB_CATEGORY", nullable = false)
    private String subCategory;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
