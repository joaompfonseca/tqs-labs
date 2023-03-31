package tqs.lab3.lab3_2_cars_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
// @TestPropertySource( locations = "application-integrationtest.properties")
public class d_CarRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateCar() {
        Car opel = new Car("Opel", "Astra");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", opel, Car.class);

        List<Car> found = repository.findAll();
        Assertions.assertThat(found).extracting(Car::getMaker).containsOnly(opel.getMaker());
    }

    @Test
    void whenValidId_thenCarShouldExist() {
        Car opel = new Car("Opel", "Astra");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", opel, Car.class);
        assertThat(entity.getBody()).isNotNull();

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/" + entity.getBody().getCarId(), Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).isEqualTo(opel.getMaker());
    }

    @Test
    void given3Cars_whenGetAll_thenStatus200() {
        Car opel = new Car("Opel", "Astra");
        Car honda = new Car("Honda", "Civic");
        Car mazda = new Car("Mazda", "3");

        repository.save(opel);
        repository.save(honda);
        repository.save(mazda);
        repository.flush();

        ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(3).extracting(Car::getMaker).containsExactly(opel.getMaker(), honda.getMaker(), mazda.getMaker());
    }
}
