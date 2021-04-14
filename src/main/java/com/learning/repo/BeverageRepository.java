package com.learning.repo;

import com.learning.entity.BeverageEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface BeverageRepository extends CrudRepository<BeverageEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<BeverageEntity> findByName(String name);

}
