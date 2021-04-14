package com.learning.controller;

import com.learning.exception.BeverageNotFoundException;
import com.learning.exception.IngredientNotFoundException;
import com.learning.model.Beverage;
import com.learning.model.Ingredient;
import com.learning.model.IngredientTray;
import com.learning.service.IBeverageService;
import com.learning.service.ICustomerService;
import com.learning.service.IIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(value = "${coffee-machine.api}")
public class BeverageController implements SwaggerApi {

    private final ICustomerService service;

    private final IBeverageService beverageService;

    private final IIngredientService ingredientService;

    public BeverageController(ICustomerService service, IBeverageService beverageService, IIngredientService ingredientService) {
        this.service = service;
        this.beverageService = beverageService;
        this.ingredientService = ingredientService;
    }

    @Override
    public ResponseEntity<List<Beverage>> listBeverages() {
        return new ResponseEntity<>(beverageService.listBeverages(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> prepareBeverage(String name) throws BeverageNotFoundException {
        service.prepareBeverage(name);
        String response = MessageFormat.format("Your {0} is ready. Enjoy the drink...", name);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Ingredient>> runningLowIngredients() {
        return new ResponseEntity<>(service.runningLowIngredients(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<List<IngredientTray>> listIngredientTray() {
        return new ResponseEntity<>(ingredientService.listIngredients(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> refillIngredient(String name) throws IngredientNotFoundException {
        ingredientService.fillIngredient(name);
        return new ResponseEntity<>("successfully filled : " + name, HttpStatus.OK);
    }
}
