package com.learning.service;

import com.learning.model.Ingredient;

import java.util.List;

public interface IBeverage {

    List<Ingredient> getIngredients();

    String getName();

    Integer timeToPrepare();

}
