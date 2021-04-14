package com.learning.util;

import com.learning.entity.BeverageEntity;
import com.learning.entity.IngredientEntity;
import com.learning.model.Beverage;
import com.learning.model.Ingredient;
import com.learning.model.IngredientTray;

import java.util.List;
import java.util.stream.Collectors;

public class MapperHelper {

    private MapperHelper() {
    }

    public static Beverage entityToBeverage(BeverageEntity beverageEntity) {
        List<Ingredient> collect = beverageEntity.getIngredients().stream()
                .map(bEn -> new Ingredient(bEn.getIngredient().getName(), bEn.getRequiredQuantity()))
                .collect(Collectors.toList());
        return new Beverage(beverageEntity.getName(), collect, beverageEntity.getTimeToPrepareInSecs());

    }

    public static IngredientTray entityToIngredientTray(IngredientEntity ingredientEntity) {
        return new IngredientTray(ingredientEntity.getName())
                .setCapacity(ingredientEntity.getCapacity())
                .setDescription(ingredientEntity.getDescription())
                .setAvailableQuantity(ingredientEntity.getAvailableQuantity());
    }
}
