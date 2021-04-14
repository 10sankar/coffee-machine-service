package com.learning.service;


import com.learning.exception.BeverageNotFoundException;
import com.learning.exception.InsufficientIngredientException;
import com.learning.model.Ingredient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ICustomerService {

    void prepareBeverage(String name) throws InsufficientIngredientException, BeverageNotFoundException;

    List<Ingredient> runningLowIngredients();

}
