package tqs.lab4.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class PurchaseFormPage {
    private WebDriver driver;
    @FindBy(tagName = "h2")
    private WebElement heading;
    @FindBy(name = "inputName")
    private WebElement name;
    @FindBy(name = "address")
    private WebElement address;
    @FindBy(name = "city")
    private WebElement city;
    @FindBy(name = "state")
    private WebElement state;
    @FindBy(name = "zipCode")
    private WebElement zipCode;
    @FindBy(name = "cardType")
    private WebElement cardType; // Selector
    @FindBy(name = "creditCardNumber")
    private WebElement creditCardNumber;
    @FindBy(name = "creditCardMonth")
    private WebElement creditCardMonth;
    @FindBy(name = "creditCardYear")
    private WebElement creditCardYear;
    @FindBy(name = "nameOnCard")
    private WebElement nameOnCard;
    @FindBy(name = "rememberMe")
    private WebElement rememberMe; // Checkbox
    @FindBy(how = How.CSS, using = ".btn-primary")
    private WebElement purchaseButton;

    public PurchaseFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return heading.getText().equals("Your flight from TLV to SFO has been reserved.");
    }

    public boolean isRememberMeSelected() {
        return rememberMe.isSelected();
    }

    public void fillForm(String name, String address, String city, String state, String zipCode, String cardType, String creditCardNumber, String creditCardMonth, String creditCardYear, String nameOnCard, boolean rememberMe) {
        this.name.sendKeys(name);
        this.address.sendKeys(address);
        this.city.sendKeys(city);
        this.state.sendKeys(state);
        this.zipCode.sendKeys(zipCode);
        this.cardType.sendKeys(cardType);
        this.creditCardNumber.sendKeys(creditCardNumber);
        this.creditCardMonth.sendKeys(creditCardMonth);
        this.creditCardYear.sendKeys(creditCardYear);
        this.nameOnCard.sendKeys(nameOnCard);
        if (rememberMe) {
            this.rememberMe.click();
        }
    }

    public void submitPurchase() {
        purchaseButton.submit();
    }
}
