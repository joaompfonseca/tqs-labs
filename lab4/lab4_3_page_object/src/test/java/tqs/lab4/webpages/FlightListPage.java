package tqs.lab4.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class FlightListPage {
    private WebDriver driver;
    @FindBy(tagName = "h3")
    private WebElement heading;
    @FindBys(@FindBy(tagName = "form"))
    private List<WebElement> flights;

    public FlightListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return heading.getText().equals("Flights from Boston to New York:");
    }

    public void chooseFlight(Integer order) {
        flights.get(order).submit();
    }
}
