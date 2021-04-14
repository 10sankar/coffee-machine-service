package com.learning.config;


import com.learning.repo.BeverageRepository;
import com.learning.repo.IngredientRepository;
import com.learning.service.IBeverageService;
import com.learning.service.ICustomerService;
import com.learning.service.IIngredientService;
import com.learning.service.impl.BeverageService;
import com.learning.service.impl.CustomerService;
import com.learning.service.impl.IngredientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.learning.repo")
@EnableRetry
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public IBeverageService beverageService(BeverageRepository bRepository) {
        return new BeverageService(bRepository);
    }

    @Bean
    public IIngredientService ingredientService(IngredientRepository iRepository) {
        return new IngredientService(iRepository);
    }

    @Bean
    public ICustomerService customerService(IBeverageService bService, IIngredientService iService) {
        return new CustomerService(bService, iService);
    }
}
