package com.learning.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", plugin = {"json:target/cucumber/coffee-machine.json", "junit:target/cucumber/coffee-machine.xml"}, glue = "classpath:com.learning.bdd.stepdefs")
public class CucumberIntegrationTest {
}

