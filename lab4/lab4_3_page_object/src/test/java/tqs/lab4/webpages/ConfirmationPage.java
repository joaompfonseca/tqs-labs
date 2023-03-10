package tqs.lab4.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
    private WebDriver driver;
    @FindBy(tagName = "h1")
    private WebElement heading;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return heading.getText().equals("Thank you for your purchase today!");
    }

    public String getTitle() {
        return driver.getTitle();
    }
}