package com.learning.service;

import com.learning.exception.BeverageNotFoundException;
import com.learning.exception.IngredientNotFoundException;
import com.learning.exception.InsufficientIngredientException;
import com.learning.model.Beverage;
import com.learning.model.Ingredient;
import com.learning.model.IngredientTray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ICustomerServiceTest {

    @Autowired
    private ICustomerService service;

    @Autowired
    private IBeverageService beverageService;

    @Autowired
    private IIngredientService ingredientService;


    @Test
    void prepareBeverage() throws BeverageNotFoundException {
        String beverage = "coffee";
        Beverage beverageObj =
                beverageService.listBeverages().stream().filter(x -> x.getName().equals(beverage)).findFirst().get();

        Map<String, Long> beforeConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));

        // Coffee machines prepares ingredient
        service.prepareBeverage(beverage);

        Map<String, Long> afterConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));

        service.prepareBeverage(beverage);

        for (Ingredient ingredient : beverageObj.getIngredients()) {
            String currentIngredient = ingredient.getName();
            Long requiredQuantity = ingredient.getQuantity();

            assertThat(afterConsumption.get(currentIngredient))
                    .isEqualTo(beforeConsumption.get(currentIngredient) - requiredQuantity);

        }
    }

    @Test
    void prepareBeverageWithExecutorService() throws InterruptedException {

        String beverage = "coffee";
        Beverage beverageObj =
                beverageService.listBeverages().stream().filter(x -> x.getName().equals(beverage)).findFirst().get();

        Map<String, Long> beforeConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));

        // coffee machines prepares list of ingredient
        List<String> beverageList = Arrays.asList(beverage, beverage, beverage);

        ExecutorService executor = Executors.newFixedThreadPool(beverageList.size());
        AtomicInteger count = new AtomicInteger();
        for (String b : beverageList) {
            executor.execute(() -> {
                try {
                    service.prepareBeverage(b);
                    count.getAndIncrement();
                } catch (BeverageNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(3, TimeUnit.SECONDS);

        Map<String, Long> afterConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));

        for (Ingredient ingredient : beverageObj.getIngredients()) {
            String currentIngredient = ingredient.getName();
            Long requiredQuantity = ingredient.getQuantity();
            assertThat(afterConsumption.get(currentIngredient))
                    .isEqualTo(beforeConsumption.get(currentIngredient) - (requiredQuantity * beverageList.size()));
        }

    }

    @Test
    void prepareBeverageWithExecutorServiceInsufficientIngredient() throws InterruptedException {
        String beverage = "partial-beverage";

        AtomicInteger actualFailCount = new AtomicInteger();
        AtomicInteger actualSuccessCount = new AtomicInteger();

        Beverage beverageObj =
                beverageService.listBeverages().stream().filter(x -> x.getName().equals(beverage)).findFirst().get();

        Map<String, Long> beforeConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));

        // coffee machines prepares list of ingredient
        List<String> beverageList = Arrays.asList(beverage, beverage, beverage, beverage, beverage, beverage);
        ExecutorService executor = Executors.newFixedThreadPool(beverageList.size());

        for (String b : beverageList) {
            executor.execute(() -> {
                try {
                    service.prepareBeverage(b);
                    actualSuccessCount.getAndIncrement();
                } catch (BeverageNotFoundException e) {
                    e.printStackTrace();
                } catch (InsufficientIngredientException e) {
                    actualFailCount.getAndIncrement();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        Map<String, Long> afterConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));

        for (Ingredient ingredient : beverageObj.getIngredients()) {
            String currentIngredient = ingredient.getName();
            Long requiredQuantity = ingredient.getQuantity();

            assertThat(afterConsumption.get(currentIngredient))
                    .isEqualTo(beforeConsumption.get(currentIngredient) - (requiredQuantity * (beverageList.size() - actualFailCount.get())));
            int actualTotalCount = actualFailCount.get() + actualSuccessCount.get();
            assertThat(actualTotalCount).isEqualTo(beverageList.size());
        }
    }

    @Test
    void prepareBeverageInvalidName() {
        String beverage = "tea";
        assertThatExceptionOfType(BeverageNotFoundException.class).isThrownBy(() -> service.prepareBeverage(beverage))
                .withMessageContaining(beverage);
    }

    @Test
    void prepareBeverageInsufficientIngredient() {
        String beverage = "low-beverage";
        String ingredient = "low-ingredient";

        assertThatExceptionOfType(InsufficientIngredientException.class)
                .isThrownBy(() -> service.prepareBeverage(beverage)).withMessageContaining(ingredient);
    }

    @Test
    void runningLowIngredients() {
        List<Ingredient> ingredients = service.runningLowIngredients();
        assertThat(ingredients).hasSize(4);
    }

    @Test
    void fillIngredient() {
        String validIngredient = "milk";
        String invalidIngredient = "oil";

        assertThatExceptionOfType(IngredientNotFoundException.class)
                .isThrownBy(() -> ingredientService.fillIngredient(invalidIngredient))
                .withMessageContaining(invalidIngredient);

        assertThatCode(() -> ingredientService.fillIngredient(validIngredient)).doesNotThrowAnyException();

    }
}
