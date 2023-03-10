package tqs.lab4.tests;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import tqs.lab4.webpages.ConfirmationPage;
import tqs.lab4.webpages.FlightListPage;
import tqs.lab4.webpages.HomePage;
import tqs.lab4.webpages.PurchaseFormPage;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BuyTripPageObjectTest {

    @Test
    public void buyTrip(ChromeDriver driver) {
        // Home Page
        HomePage home = new HomePage(driver);
        assertThat(home.isLoaded()).isTrue();
        home.submitFindFlights("Boston", "New York");

        // Flight List Page
        FlightListPage flightList = new FlightListPage(driver);
        assertThat(flightList.isLoaded()).isTrue();
        flightList.chooseFlight(1);

        // Purchase Form Page
        PurchaseFormPage purchaseForm = new PurchaseFormPage(driver);
        assertThat(purchaseForm.isLoaded()).isTrue();
        purchaseForm.fillForm("João Fonseca", "Universidade de Aveiro", "Aveiro", "Aveiro", "12345", "Diner's Club", "12345", "3", "2023", "João Fonseca", true);
        assertThat(purchaseForm.isRememberMeSelected()).isTrue();
        purchaseForm.submitPurchase();

        // Confirmation Page
        ConfirmationPage confirmation = new ConfirmationPage(driver);
        assertThat(confirmation.isLoaded()).isTrue();
        assertThat(confirmation.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }
}
