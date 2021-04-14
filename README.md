# Coffee-machine service

### Services beverages in N outlets.

## Useful Links

swagger: http://localhost:8889/swagger-ui/

h2-db : http://localhost:8889/h2-console

### Tables

SELECT FROM BEVERAGE; (name)

SELECT FROM BEVERAGE INGREDIENT; (fk(beverage), fk(ingredient), required quality)

SELECT FROM INGREDIENT; (name, capacity, available quality) 

#### BEVERAGE (One to Many) BEVERAGE_INGREDIENT (Many to One) INGREDIENT

schemas are created from Entity class.

Initial values are loaded from data.sql.

### Features beverage-controller

GET /api/v1/coffee-machine/beverage/all --> list all Beverages

POST /api/v1/coffee-machine/beverage/order --> prepares given Beverage

GET /api/v1/coffee machine/ingredient/low --> provides ingredients that are low (Less than 20% capacity)

POST /api/v1/coffee-machine/ingredient/refill --> refills given Ingredient to max capacity

GET /api/v1/coffee-machine/ingredient/tray --> lists all Ingredient Tray (available_quantity, capacity, name...)

Used RestControllerAdvice for ErrorHandling.

# Concurrency Control

for reference:

```
@Test
void prepareBeverageWithExecutorService_insufficientIngredient() {
    ...
     Map<String, Long> beforeConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));
    ...
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
    ...
     Map<String, Long> afterConsumption = ingredientService.listIngredients().stream()
                .collect(Collectors.toMap(IngredientTray::getName, IngredientTray::getAvailableQuantity));
    ...
}
```
