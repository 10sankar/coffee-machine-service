package com.learning.repo;

import com.learning.entity.IngredientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<IngredientEntity, Long> {
    Optional<IngredientEntity> findByName(String name);
}
