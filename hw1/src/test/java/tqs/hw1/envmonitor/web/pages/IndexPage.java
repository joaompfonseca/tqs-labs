package tqs.hw1.envmonitor.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage {

    private WebDriver driver;
    @FindBy(id = "search_input")
    private WebElement searchInput;
    @FindBy(id = "search_button")
    private WebElement searchButton;

    public IndexPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setQuery(String query) {
        searchInput.sendKeys(query);
    }

    public String getQuery() {
        return searchInput.getAttribute("value");
    }

    public void search() {
        searchButton.click();
    }
}
