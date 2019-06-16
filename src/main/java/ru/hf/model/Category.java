package ru.hf.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category extends BaseEntity {

    @NotBlank
    @Column(name = "main_category", nullable = false)
    private String mainCategory;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "description")
    private String description;
}
