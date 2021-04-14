package com.learning.model;

import com.learning.service.IBeverage;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Beverage implements IBeverage {

    private String name;
    private List<Ingredient> ingredients;
    private Integer timeToPrepareInSec;

    @Override
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer timeToPrepare() {
        return timeToPrepareInSec;
    }

}
