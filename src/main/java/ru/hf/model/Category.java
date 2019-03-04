package ru.hf.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CATEGORY")
public class Category {

    @Id
    @Column(name = "CATEGORY_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    @Column(name = "MAIN_CATEGORY", nullable = false)
    private String mainCategory;

    @Column(name = "SUB_CATEGORY", nullable = false)
    private String subCategory;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public Category() {
    }

    public Category(Long categoryID) {
        this.categoryID = categoryID;
    }
}
