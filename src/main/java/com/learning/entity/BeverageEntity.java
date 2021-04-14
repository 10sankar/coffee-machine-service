package com.learning.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "beverage")
@Getter
@Setter
@Accessors(chain = true)
public class BeverageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time_to_prepare_in_secs")
    private Integer timeToPrepareInSecs;

    @Version
    private Long version;

    @OneToMany(mappedBy = "beverage", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BeverageIngredientEntity> ingredients = new HashSet<>();

    public BeverageEntity addIngredient(IngredientEntity ingredient, Long quantity) {
        BeverageIngredientEntity beverageIngredientEntity = new BeverageIngredientEntity(this, ingredient)
                .setRequiredQuantity(quantity);
        ingredients.add(beverageIngredientEntity);
        ingredient.getBeverages().add(beverageIngredientEntity);
        return this;
    }

    public void removeIngredient(IngredientEntity ingredient) {
        Optional<BeverageIngredientEntity> biE = ingredients.
                stream().filter(bi -> bi.getBeverage().getName().equals(this.getName()) &&
                bi.getIngredient().getName().equals(ingredient.getName())).findFirst();
        biE.ifPresent(entity -> {
            ingredients.remove(entity);
            this.getIngredients().remove(entity);
        });
    }
}
