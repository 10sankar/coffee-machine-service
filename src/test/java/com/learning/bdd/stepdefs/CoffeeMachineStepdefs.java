package com.learning.bdd.stepdefs;

import com.learning.Application;
import com.learning.client.TestClient;
import com.learning.exception.BeverageNotFoundException;
import com.learning.exception.IngredientNotFoundException;
import com.learning.model.Beverage;
import com.learning.model.Ingredient;
import feign.FeignException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = DEFINED_PORT)
@CucumberContextConfiguration
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
@EnableFeignClients(clients = TestClient.class)
public class CoffeeMachineStepdefs {

    @Autowired
    private TestClient client;

    private List<String> actualList;
    private String actualResponse;

    @When("user checks beverages")
    public void userChecksBeverages() {
        List<Beverage> actual = client.listBeverages().getBody();
        actualList = actual.stream().map(Beverage::getName).collect(Collectors.toList());
    }

    @Then("machine should list below")
    public void machineShouldListBelow(DataTable dataTable) {
        List<String> expectedBeverages = dataTable.asList(String.class);
        assertThat(actualList).containsExactlyInAnyOrderElementsOf(expectedBeverages);
    }

    @When("user picks {string}")
    public void userPicks(String beverage) throws BeverageNotFoundException {
        try {
            actualResponse = client.prepareBeverage(beverage).getBody();
        } catch (FeignException e) {
            actualResponse = e.contentUTF8();
        }
    }

    @Then("machine should respond {string}")
    public void machineShouldRespond(String expectedResponse) {
        assertThat(actualResponse).containsIgnoringCase(expectedResponse);
    }

    @When("user want to see running low ingredients")
    public void userWantToSeeRunningLowIngredients() {
        List<Ingredient> actual = client.runningLowIngredients().getBody();
        actualList = actual.stream().map(Ingredient::getName).collect(Collectors.toList());
    }

    @When("user want to fill below ingredients")
    public void userWantToFillBelowIngredients(DataTable dataTable) throws IngredientNotFoundException {
        List<String> ingredientsToFill = dataTable.asList(String.class);
        for (String i : ingredientsToFill) {
            actualResponse = client.refillIngredient(i).getBody();
        }
    }
}
