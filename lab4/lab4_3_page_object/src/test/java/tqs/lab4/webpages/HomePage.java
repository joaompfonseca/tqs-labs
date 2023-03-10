package tqs.lab4.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;
    @FindBy(tagName = "h1")
    private WebElement heading;
    @FindBy(name = "fromPort")
    private WebElement fromPort;
    @FindBy(name = "toPort")
    private WebElement toPort;
    @FindBy(how = How.CSS, using = ".btn-primary")
    private WebElement findButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.get("https://blazedemo.com");
    }

    public boolean isLoaded() {
        return heading.getText().equals("Welcome to the Simple Travel Agency!");
    }

    public void submitFindFlights(String from, String to) {
        fromPort.sendKeys(from);
        toPort.sendKeys(to);
        findButton.click();
    }
}