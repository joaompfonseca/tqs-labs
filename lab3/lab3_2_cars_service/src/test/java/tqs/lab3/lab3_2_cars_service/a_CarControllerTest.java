package tqs.lab3.lab3_2_cars_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class a_CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car opel = new Car("Opel", "Astra");

        // Loads the service mock with the proper expectation
        when(service.save(Mockito.any())).thenReturn(opel);

        // Performs the request
        mvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(opel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is(opel.getMaker())))
                .andExpect(jsonPath("$.model", is(opel.getModel())));

        // Verifies that service#save was only called once
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void whenGetCars_thenGetCars() throws Exception {
        Car opel = new Car("Opel", "Astra");
        Car honda = new Car("Honda", "Civic");
        Car mazda = new Car("Mazda", "3");

        List<Car> allCars = List.of(opel, honda, mazda);

        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(get("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(allCars)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(opel.getMaker())))
                .andExpect(jsonPath("$[0].model", is(opel.getModel())))
                .andExpect(jsonPath("$[1].maker", is(honda.getMaker())))
                .andExpect(jsonPath("$[1].model", is(honda.getModel())))
                .andExpect(jsonPath("$[2].maker", is(mazda.getMaker())))
                .andExpect(jsonPath("$[2].model", is(mazda.getModel())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void whenGetCarById_thenGetCarWithId() throws Exception {
        Car opel = new Car("Opel", "Astra");

        when(service.getCarDetails(0L)).thenReturn(Optional.of(opel));

        mvc.perform(get("/api/cars/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(opel.getMaker())))
                .andExpect(jsonPath("$.model", is(opel.getModel())));

        verify(service, times(1)).getCarDetails(0L);
    }
}
