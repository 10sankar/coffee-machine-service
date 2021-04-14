package com.learning.service;


import com.learning.entity.BeverageEntity;
import com.learning.exception.BeverageNotFoundException;
import com.learning.model.Beverage;

import java.util.List;

public interface IBeverageService {
    List<Beverage> listBeverages();

    BeverageEntity findBeverage(String name) throws BeverageNotFoundException;
}
