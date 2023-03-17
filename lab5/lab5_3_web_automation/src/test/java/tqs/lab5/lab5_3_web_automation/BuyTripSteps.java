package tqs.lab5.lab5_3_web_automation;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BuyTripSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.get(url);
    }

    @And("I choose a trip from {string} to {string}")
    public void iChooseATrip(String from, String to) {
        driver.findElement(By.name("fromPort")).sendKeys(from);
        driver.findElement(By.name("toPort")).sendKeys(to);
    }

    @And("I click find flights")
    public void iClickFindFlights() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I should see the list of flights from {string} to {string}")
    public void iShouldSeeFlightListPage(String from, String to) {
        WebElement element = driver.findElement(By.cssSelector("h3"));
        assertThat(element.getText()).isEqualTo(String.format("Flights from %s to %s:", from, to));
    }

    @When("I choose the flight in row {int}")
    public void iChooseTheFlight(int row) {
        driver.findElement(By.cssSelector(String.format("tr:nth-child(%d) .btn", row))).click();
    }

    @Then("I should see the purchase form page")
    public void iShouldSeePurchaseFormPage() {
        WebElement element = driver.findElement(By.cssSelector("h2"));
        assertThat(element.getText()).isEqualTo("Your flight from TLV to SFO has been reserved.");
    }

    @When("I fill in the form with")
    public void iFillInTheFormWith(List<Map<String, String>> entries) {
        entries.forEach(entry -> {
            String key = entry.get("key");
            String value = entry.get("value");
            if (key.equals("rememberMe")) {
                if (Boolean.parseBoolean(value)) {
                    driver.findElement(By.name(key)).click();
                }
            } else {
                driver.findElement(By.name(key)).sendKeys(value);
            }
        });
    }

    @Then("I should see the remember me checkbox checked")
    public void iShouldSeeRememberMeChecked() {
        WebElement element = driver.findElement(By.name("rememberMe"));
        assertThat(element.isSelected()).isTrue();
    }

    @When("I click purchase flight")
    public void iClickPurchaseFlight() {
        driver.findElement(By.cssSelector(".btn-primary")).submit();
    }

    @Then("I should see the confirmation page")
    public void iShouldSeeConfirmationPage() {
        WebElement element = driver.findElement(By.cssSelector("h1"));
        assertThat(element.getText()).isEqualTo("Thank you for your purchase today!");
    }

    @And("I should see {string} in the title")
    public void iShouldSeeInTheTitle(String title) {
        assertThat(driver.getTitle()).isEqualTo(title);
    }
}
