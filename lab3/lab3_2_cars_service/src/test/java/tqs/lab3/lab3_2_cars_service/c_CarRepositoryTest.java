package tqs.lab3.lab3_2_cars_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class c_CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository repository;

    @Test
    void whenFindByCarId_thenReturnCar() {
        Car opel = new Car("Opel", "Astra");
        entityManager.persistAndFlush(opel);

        Car found = repository.findByCarId(opel.getCarId());
        assertThat(found).extracting(Car::getMaker).isEqualTo(opel.getMaker());
    }

    @Test
    void whenFindByInvalidCarId_thenReturnNull() {
        Car found = repository.findByCarId(0L);
        assertThat(found).isNull();
    }

    @Test
    void given3Cars_whenGetAll_thenReturn3Records() {
        Car opel = new Car("Opel", "Astra");
        Car honda = new Car("Honda", "Civic");
        Car mazda = new Car("Mazda", "3");

        entityManager.persist(opel);
        entityManager.persist(honda);
        entityManager.persist(mazda);
        entityManager.flush();

        List<Car> allCars = repository.findAll();
        Assertions.assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsExactly(opel.getMaker(), honda.getMaker(), mazda.getMaker());
    }
}
