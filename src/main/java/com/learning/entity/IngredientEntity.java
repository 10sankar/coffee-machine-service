package com.learning.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
@Accessors(chain = true)
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "available_quantity")
    private Long availableQuantity;

    @Column(name = "description")
    private String description;

    @Column(name = "capacity")
    private Long capacity;

    @Version
    private Long version;

    @OneToMany(mappedBy = "ingredient", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<BeverageIngredientEntity> beverages = new HashSet<>();

}
