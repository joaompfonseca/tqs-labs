package tqs.lab3.lab3_2_cars_service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class b_CarManagerServiceTest {

    @Mock(lenient = true)
    private CarRepository repository;

    @InjectMocks
    private CarManagerService service;

    @BeforeEach
    public void setUp() {
        Car opel = new Car("Opel", "Astra");
        Car honda = new Car("Honda", "Civic");
        Car mazda = new Car("Mazda", "3");

        List<Car> allCars = List.of(opel, honda, mazda);

        // Loads the mock repository with the proper expectations
        when(repository.findByCarId(0L)).thenReturn(opel);
        when(repository.findByCarId(1L)).thenReturn(honda);
        when(repository.findByCarId(2L)).thenReturn(mazda);
        when(repository.findByCarId(3L)).thenReturn(null);
        when(repository.findAll()).thenReturn(allCars);
    }

    @Test
    void whenValidId_thenCarShouldExist() {
        Optional<Car> car = service.getCarDetails(0L);
        assertThat(car.isPresent()).isTrue();

        verify(repository, times(1)).findByCarId(0L);
    }

    @Test
    void whenInvalidId_thenCarShouldNotExist() {
        Optional<Car> car = service.getCarDetails(3L);
        assertThat(car.isPresent()).isFalse();

        verify(repository, times(1)).findByCarId(3L);
    }

    @Test
    void given3Cars_whenGetAll_thenReturn3Records() {
        Car opel = new Car("Opel", "Astra");
        Car honda = new Car("Honda", "Civic");
        Car mazda = new Car("Mazda", "3");

        List<Car> allCars = service.getAllCars();
        Assertions.assertThat(allCars).hasSize(3).contains(opel, honda, mazda);
        verify(repository, times(1)).findAll();
    }
}
