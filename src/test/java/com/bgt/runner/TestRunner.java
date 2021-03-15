package com.bgt.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        publish = false,
        plugin = {"pretty", "html:target/cucumber-report.html"},
        tags="@SmokeTests",
        features = {"classpath:features"},
        glue = {"com.bgt.stepdefs", "com.bgt.hooks"}
)
public class TestRunner {
    private static final Logger log = LogManager.getLogger(TestRunner.class);

}
