package com.learning.service;

import com.learning.entity.BeverageIngredientEntity;
import com.learning.exception.IngredientNotFoundException;
import com.learning.exception.InsufficientIngredientException;
import com.learning.model.IngredientTray;
import org.hibernate.StaleStateException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface IIngredientService {
    List<IngredientTray> listIngredients();

    @Retryable(value = {StaleStateException.class}, maxAttempts = 10)
    void fillIngredient(String ingredient) throws IngredientNotFoundException;

    @Retryable(value = {StaleStateException.class}, maxAttempts = 10)
    @Transactional(rollbackFor = {InsufficientIngredientException.class})
    void consumeIngredients(Set<BeverageIngredientEntity> ingredients);

}
