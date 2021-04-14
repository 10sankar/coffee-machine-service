package com.learning.controller;

import com.learning.exception.BeverageNotFoundException;
import com.learning.exception.IngredientNotFoundException;
import com.learning.model.Beverage;
import com.learning.model.Ingredient;
import com.learning.model.IngredientTray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SwaggerApi {

    @GetMapping(value = "/beverage/all")
    ResponseEntity<List<Beverage>> listBeverages();

    @PostMapping(value = "/beverage/order")
    ResponseEntity<String> prepareBeverage(@RequestParam(value = "name") String name) throws BeverageNotFoundException;

    @GetMapping(value = "/ingredient/low")
    ResponseEntity<List<Ingredient>> runningLowIngredients();

    @GetMapping(value = "/ingredient/tray")
    ResponseEntity<List<IngredientTray>> listIngredientTray();

    @PostMapping(value = "/ingredient/refill")
    ResponseEntity<String> refillIngredient(@RequestParam(value = "name") String name) throws IngredientNotFoundException;

}
