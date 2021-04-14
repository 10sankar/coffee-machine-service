package com.learning.service.impl;

import com.learning.entity.BeverageEntity;
import com.learning.exception.BeverageNotFoundException;
import com.learning.exception.InsufficientIngredientException;
import com.learning.model.Ingredient;
import com.learning.model.IngredientTray;
import com.learning.service.IBeverageService;
import com.learning.service.ICustomerService;
import com.learning.service.IIngredientService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CustomerService implements ICustomerService {

    private static final Long RUNNING_LOW_PERCENTAGE = 28L;
    private final IBeverageService beverageService;
    private final IIngredientService ingredientService;

    @Override
    public void prepareBeverage(String name) throws InsufficientIngredientException, BeverageNotFoundException {
        BeverageEntity beverageEntity = beverageService.findBeverage(name);
        ingredientService.consumeIngredients(beverageEntity.getIngredients());
    }

    @Override
    public List<Ingredient> runningLowIngredients() {
        List<IngredientTray> allIngredients = ingredientService.listIngredients();
        return allIngredients.stream().filter(t -> currentCapacityPercentage(t.getAvailableQuantity(), t.getCapacity())
                < RUNNING_LOW_PERCENTAGE).map(m -> new Ingredient(m.getName(), m.getAvailableQuantity()))
                .collect(Collectors.toList());

    }

    private Long currentCapacityPercentage(Long capacity, Long totalCapacity) {
        return capacity * 180 / totalCapacity;
    }
}
