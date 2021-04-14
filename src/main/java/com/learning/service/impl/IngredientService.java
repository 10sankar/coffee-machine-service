package com.learning.service.impl;

import com.learning.entity.BeverageIngredientEntity;
import com.learning.entity.IngredientEntity;
import com.learning.exception.IngredientNotFoundException;
import com.learning.exception.InsufficientIngredientException;
import com.learning.model.IngredientTray;
import com.learning.repo.IngredientRepository;
import com.learning.service.IIngredientService;
import com.learning.util.MapperHelper;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientService implements IIngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientTray> listIngredients() {
        List<IngredientEntity> all = (List<IngredientEntity>) ingredientRepository.findAll();
        return all.stream().map(MapperHelper::entityToIngredientTray).collect(Collectors.toList());
    }

    @Override
    @Retryable(value = {StaleStateException.class}, maxAttempts = 10)
    public void fillIngredient(String ingredient) throws IngredientNotFoundException {
        IngredientEntity ingredientEntity = findIngredient(ingredient);
        ingredientEntity.setAvailableQuantity(ingredientEntity.getCapacity());
        ingredientRepository.save(ingredientEntity);

    }

    public IngredientEntity findIngredient(String ingredient) throws IngredientNotFoundException {
        return ingredientRepository.findByName(ingredient).orElseThrow(() -> new IngredientNotFoundException(ingredient));
    }

    @Override
    @Retryable(value = {StaleStateException.class}, maxAttempts = 10)
    @Transactional(rollbackFor = {InsufficientIngredientException.class})
    public void consumeIngredients(Set<BeverageIngredientEntity> ingredients) throws InsufficientIngredientException {
        for (BeverageIngredientEntity beverageIngredientEntity : ingredients) {
            Long requiredIngredient = beverageIngredientEntity.getRequiredQuantity();
            Long availableIngredient = beverageIngredientEntity.getIngredient().getAvailableQuantity();

            if (requiredIngredient > availableIngredient) {
                throw new InsufficientIngredientException(beverageIngredientEntity.getIngredient().getName());
            } else {
                IngredientEntity ingredientEntity =
                        beverageIngredientEntity.getIngredient().setAvailableQuantity(availableIngredient - requiredIngredient);
                ingredientRepository.save(ingredientEntity);
            }
        }
    }
}
