package com.learning.service.impl;

import com.learning.entity.BeverageEntity;
import com.learning.exception.BeverageNotFoundException;
import com.learning.model.Beverage;
import com.learning.repo.BeverageRepository;
import com.learning.service.IBeverageService;
import com.learning.util.MapperHelper;

import java.util.List;
import java.util.stream.Collectors;

public class BeverageService implements IBeverageService {

    private final BeverageRepository beverageRepository;

    public BeverageService(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    @Override
    public List<Beverage> listBeverages() {
        List<BeverageEntity> allEntities = (List<BeverageEntity>) beverageRepository.findAll();
        return allEntities.stream().map(MapperHelper::entityToBeverage).collect(Collectors.toList());

    }

    @Override
    public BeverageEntity findBeverage(String name) throws BeverageNotFoundException {
        return beverageRepository.findByName(name).orElseThrow(() -> new BeverageNotFoundException(name));
    }
}
