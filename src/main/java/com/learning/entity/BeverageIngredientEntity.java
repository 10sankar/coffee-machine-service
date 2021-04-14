package com.learning.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "beverage_ingredient")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class BeverageIngredientEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "beverage_id")
    private BeverageEntity beverage;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ingredient_id")
    private IngredientEntity ingredient;

    @Column(name = "required_quantity")
    private Long requiredQuantity;

    public BeverageIngredientEntity(BeverageEntity beverage, IngredientEntity ingredient) {
        this.beverage = beverage;
        this.ingredient = ingredient;
    }
}
