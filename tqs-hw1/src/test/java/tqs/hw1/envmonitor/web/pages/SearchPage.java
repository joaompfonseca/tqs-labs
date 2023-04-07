package tqs.hw1.envmonitor.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

    private WebDriver driver;
    @FindBy(id = "search_input")
    private WebElement searchInput;
    @FindBy(id = "search_button")
    private WebElement searchButton;
    @FindBy(id = "search_results")
    private WebElement searchResults;
    @FindBy(id = "current_empty")
    private WebElement currentEmpty;
    @FindBy(id = "current_table")
    private WebElement currentTable;
    @FindBy(id = "forecast_empty")
    private WebElement forecastEmpty;
    @FindBy(id = "forecast_chart")
    private WebElement forecastChart;
    @FindBy(id = "forecast_chart")
    private WebElement forecastTable;

    public SearchPage(WebDriver driver) {
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

    public String getResults() {
        return searchResults.getText();
    }

    public Boolean hasCurrentEmpty() {
        return currentEmpty.getText().equals("Nothing to show here.");
    }

    public Boolean hasCurrentTable() {
        return currentTable.isDisplayed();
    }

    public Boolean hasForecastEmpty() {
        return currentEmpty.getText().equals("Nothing to show here.");
    }

    public Boolean hasForecastChart() {
        return forecastChart.isDisplayed();
    }

    public Boolean hasForecastTable() {
        return forecastTable.isDisplayed();
    }
}
