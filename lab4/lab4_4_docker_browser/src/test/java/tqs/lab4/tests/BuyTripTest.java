package tqs.lab4.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BuyTripTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void buyTrip() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(766, 970));
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Boston']")).click();
        }
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'New York']")).click();
        }
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector("html")).click();
        assertThat(driver.findElement(By.cssSelector("h3")).getText(), is("Flights from Boston to New York:"));
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
        assertThat(driver.findElement(By.cssSelector("h2")).getText(), is("Your flight from TLV to SFO has been reserved."));
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("João Fonseca");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("Universidade de Aveiro");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("Aveiro");
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("Aveiro");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("12345");
        driver.findElement(By.id("cardType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            // dropdown.findElement(By.xpath("//option[. = 'Diner\'s Club']")).click();
            dropdown.findElement(By.xpath("//option[. = \"Diner's Club\"]")).click();
        }
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("12345");
        driver.findElement(By.cssSelector("html")).click();
        driver.findElement(By.id("creditCardMonth")).sendKeys("3");
        driver.findElement(By.cssSelector("body")).click();
        driver.findElement(By.id("creditCardYear")).sendKeys("2023");
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("João Fonseca");
        driver.findElement(By.id("rememberMe")).click();
        assertTrue(driver.findElement(By.id("rememberMe")).isSelected());
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.findElement(By.cssSelector("h1")).getText(), is("Thank you for your purchase today!"));
        assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
    }
}
