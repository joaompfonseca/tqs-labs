# TQS - Lab activities

## Lab 1 - Unit testing (with JUnit 5)

### 1.2 - Euromillions

#### 2.c - Assessing the test coverage using JaCoCo

##### Steps

1. Declare **JaCoCo** inside *pom.xml* plugins.

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.8</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

2. Run `mvn clean test jacoco:report` to execute **test** and **jacoco:report** goals.

3. The HTML report should be under *target/site/jacoco*.

##### Questions

**I. Which classes/methods offer less coverage?** 

As a rule of thumb, unit tests should cover at least 80% of the class. The following table presents some examples of low instruction coverage in the project.

| Package          | Class                | Instruction Coverage | Methods                                                                                                                                         |
| ---------------- | -------------------- | -------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| tqs.euromillions | CuponEuroMillions    | 34%                  | format() (0%)<br/>countDips() (0%)                                                                                                              |
| tqs.sets         | BoundedSetOfNaturals | 54%                  | fromArray(int[]) (0%)<br/>add(int) (56%)<br/>hashCode() (0%)<br/>equals(Object) (76%)<br/>size() (0%)<br/>intersects(BoundedSetOfNaturals) (0%) |

**II. Are all possible [decision] branches being covered?**
No. The following table presents some examples of low branch coverage in the project.

| Package          | Class                     | Branch Coverage |
| ---------------- | ------------------------- | --------------- |
| tqs.euromillions | CuponEuromillions<br/>Dip | 0%<br/>77%      |
| tqs.sets         | BoundedSetOfNaturals      | 50%             |

**III. Collect evidence of the coverage for “BoundedSetOfNaturals”.**

According to JaCoCo, this class has:

- a instruction coverage of 54%;

- a branch coverage of 50%.

**IV. What kind of unit tests are worth writing for proper validation of BoundedSetOfNaturals?**

Taking into consideration the expected contract of the class, we should test for:

- addition of numbers not found in a set;

- addition of numbers already in a set;

- presence of all natural numbers;

- max size of a set;

- set operations (contains, intersection).

#### 2.d - BoundedSetOfNaturals before and after results

| Method                           | Instruction Coverage - Before (54%) | Branch Coverage - Before (50%) | Instruction Coverage - After (87%) | Branch Coverage - After (80%) |
| -------------------------------- | ----------------------------------- | ------------------------------ | ---------------------------------- | ----------------------------- |
| add(int)                         | 56%                                 | 50%                            | 83%                                | 83%                           |
| add(int[])                       | 100%                                | 100%                           | 100%                               | 100%                          |
| BoundedSetOfNaturals(int)        | 100%                                | -                              | 100%                               | -                             |
| contains(Integer)                | 100%                                | -                              | 100%                               | -                             |
| equals(Object)                   | 76%                                 | 50%                            | 76%                                | 50%                           |
| fromArray(int[])                 | 0%                                  | 0%                             | 100%                               | 100%                          |
| hashCode()                       | 0%                                  | -                              | 0%                                 | -                             |
| intersects(BoundedSetOfNaturals) | 0%                                  | -                              | 100%                               | 100%                          |
| iterator()                       | 100%                                | -                              | 100%                               | -                             |
| maxSize()                        | -                                   | -                              | 100%                               | -                             |
| size()                           | 0%                                  | -                              | 100%                               | -                             |

## Lab 2 - Mocking dependencies (for unit testing)

### 2.1 - Stocks portfolio

#### 1.a - Test for StockPortfolio#getTotalValue

##### Setup

1. Add **Mockito** to *pom.xml*.
   
   ```xml
   <dependency>
       <groupId>org.mockito</groupId>
       <artifactId>mockito-junit-jupiter</artifactId>
       <version>5.1.1</version>
       <scope>test</scope>
   </dependency>
   ```

2. Add the **@ExtendWith** annotation to integrate the Mockito framework.
   
   ```java
   @ExtendWith(MockitoExtension.class)
   class StocksPortfolioTest { ... }
   ```

3. Add the **@MockitoSettings** annotation to avoid exceptions on unused stubs.
   
   ```java
   @MockitoSettings(strictness = Strictness.LENIENT)
   class StocksPortfolioTest { ... }
   ```

##### Outline

1. Prepare a mock to substitute the remote service (@Mock annotation).
   
   ```java
   @Mock
   IStockmarketService market;
   ```

2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
   
   ```java
    @InjectMocks
    StocksPortfolio portfolio;
   ```

3. Load the mock with the proper expectations (when...thenReturn)
   
   ```java
   @Test
   public void getTotalValue() {
       when(market.lookUpPrice("EBAY")).thenReturn(4.0);
       when(market.lookUpPrice("MSFT")).thenReturn(1.5);
       when(market.lookUpPrice("NOTUSED")).thenReturn(1.5);
       // ...
   }
   ```

4. Execute the test (use the service in the SuT)
   
   ```java
   @Test
   public void getTotalValue() {
       // ...
       portfolio.addStock(new Stock("EBAY", 2));
       portfolio.addStock(new Stock("MSFT", 4));
       double result = portfolio.getTotalValue();
       // ...
   }
   ```

5. Verify the result (assert) and the use of the mock (verify)
   
   ```java
   @Test
   public void getTotalValue() {
       // ...
       assertThat(result, equalTo(14.0));
   }
   ```

#### 1.b - Hamcrest library

##### Setup

1. Add **Hamcrest** to *pom.xml*.
   
   ```xml
   <dependency>
       <groupId>org.hamcrest</groupId>
       <artifactId>hamcrest-all</artifactId>
       <version>1.3</version>
   </dependency>
   ```

##### Usage

1. Replace **JUnit** core asserts with more human-readable assertions from the **Hamcrest** library.
   
   ```java
   // assertEquals(14.0, result);
   assertThat(result, equalTo(14.0));
   ```

### 2.2 - Geocoding

#### 2.b - Testing AddressResolver#findAddressForLocation

##### Questions

**I. Which is the SuT (subject under test)?**

The SuT is the class **AddressResolver**.

**II. Which is the service to *mock*?**

The service to mock is the **MapQuest API**.

### 2.3 - Integration

- Integration tests invoke the **real services**, not a mock as in unit tests. 

- We **don't need to have any mocks** in integration tests.

- Integration tests might take more time to run (seconds) than unit tests (milliseconds).

##### Maven conventions

We should follow the **Maven** class naming convention for tests.

| Type              | Naming Convention                |
| ----------------- | -------------------------------- |
| Unit Tests        | **Test... {}** or **...Test {}** |
| Integration Tests | **...IT {}**                     |

##### Some Maven commands

| Command                       | Description             |
| ----------------------------- | ----------------------- |
| mvn test                      | Runs unit tests.        |
| mvn package                   | Runs unit tests.        |
| mvn package -Dskiptests=true  | Skips unit tests.       |
| mvn failsafe:integration-test | Runs integration tests. |

## Lab 3 - Multi-layer application testing (with Spring Boot)

##### Key concepts

- **@SpringBootTest:** loads entire application context.

- **@DataJpaTest:** only loads @Repository components.

- **@WebMvcTest:** used to test REST APIs exposed through controllers. Beans used by controller need to be mocked.

- Isolate the functionality to be tested by limiting the context of loaded frameworks/components. In some cases we can get away with standard unit testing.

### 3.1 - Employee manager example

##### Questions

**a) Identify a couple of examples that use AssertJ expressive methods chaining.**

|                                                                                                           | Line                                           | Example                                                                                                                                                                                                                                                                                                                                                                                                  |
| --------------------------------------------------------------------------------------------------------- | ---------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **A_EmployeeRepositoryTest**                                                                              | 37<br/>43<br/>52<br/>53<br/>59<br/>75          | assertThat(found).isEqualTo(alex);<br/>assertThat(fromDb).isNull();<br/>assertThat(fromDb).isNotNull();<br/>assertThat(fromDb.getEmail()).isEqualTo(emp.getEmail());<br/>assertThat(fromDb).isNull();<br/>assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());                                                                  |
| **B_EmployeeService_UnitTest**                                                                            | 63<br/>69<br/>77<br/>85<br/>93<br/>102<br/>113 | assertThat(found.getName()).isEqualTo(name);<br/>assertThat(fromDb).isNull();<br/>assertThat(doesEmployeeExist).isEqualTo(true);<br/>assertThat(doesEmployeeExist).isEqualTo(false);<br/>assertThat(fromDb.getName()).isEqualTo("john");<br/>assertThat(fromDb).isNull();<br/>assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName()); |
| **C_EmployeeController_WithMockServiceTest**                                                              | N/A                                            | N/A                                                                                                                                                                                                                                                                                                                                                                                                      |
| **D_EmployeeRestControllerIT**                                                                            | 58                                             | assertThat(found).extracting(Employee::getName).containsOnly("bob");                                                                                                                                                                                                                                                                                                                                     |
| **E_EmployeeRestControllerTemplateIT**                                                                    | 53<br/>66<br/>67                               | assertThat(found).extracting(Employee::getName).containsOnly("bob");<br/>assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);                                                                                                                                                                                                                                                                  |
| <br/>        assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex"); |                                                |                                                                                                                                                                                                                                                                                                                                                                                                          |

**b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).**
In **B_EmployeeService_UnitTest** we mock the behaviour of the repository with **Mockito**.

```java
@ExtendWith(MockitoExtension.class)
class B_EmployeeService_UnitTest {

    @Mock(lenient = true)
    private EmployeeRepository employeeRepository;
    // ...

    @BeforeEach
    public void setUp() {
        // ...
        Employee alex = new Employee("alex", "alex@deti.com");
        // ...
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        // ...
    }

    @Test
    void whenSearchValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Employee found = employeeService.getEmployeeByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }
}
```

**c) What is the difference between standard @Mock and @MockBean?**

- **@Mock:** anotation from **Mockito** used for unit testing. Creates a mock object of a class or an interface.

- **@MockBean:** anotation from **SpringBoot** used for integration testing. Replaces a Spring bean with a mock object.

**d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?**

The file is a SpringBoot properties file for configuring integrations tests. It overrides similar configurations that are contained in the default configuration properties, *application.properties*, located in *src/main/resources/*.

**Example:** We use a hosted MySQL database in production, but want to a local MySQL database for testing. Inside *application-integrationtest.properties* we should have the following configurations:

```properties
# application-integrationtest.properties
spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.username=demo
spring.datasource.password=demo
```

To load these configurations, we should use the anotation **@TestPropertySource** in the integration test:

```java
@TestPropertySource(locations = "application-integrationtest.properties")
class D_EmployeeRestControllerIT { ... }
```

**e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?**

| Strategy | What's Tested                  | Characteristics                                                                                                                                                                                                                                                                              |
| -------- | ------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| C        | Controller                     | Simple and light environment;<br/>Simulates the behaviour of an application server (**@WebMvcTest** anotation);<br/>The server context is accessed through a special testing servlet (**MockMvc** object), that provides an expressive API which supports method chaining to build the mock. |
| D        | Full App with MockMvc          | Exercises all the layers of the SpringBoot application (**@SpringBootTest**);<br/>The server context is accessed through a <u>special testing servlet</u> (**MockMvc** object), that provides an expressive API which supports method chaining to build the mock.                            |
| E        | Full App with TestRestTemplate | Exercises all the layers of the SpringBoot application (**@SpringBootTest**);<br/>The requests to the server are made by an <u>API client</u> (**TestRestTemplate** object), which involves the un/marshaling of the messages.                                                               |

### 3.2 - Cars service

#### 2.a - CarController Test

The following code tests the POST */api/cars* endpoint using a **MockMvc** instance (mvc) and a **@MockBean** of the **CarManagerService** instance (service).

```java
@WebMvcTest(CarController.class)
public class a_CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;
    // ...

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car opel = new Car("Opel", "Astra");

        // Loads the service mock with the proper expectation
        when(service.save(opel)).thenReturn(opel);

        // Performs the request and expects certain results
        mvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(opel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Opel")))
                .andExpect(jsonPath("$.model", is("Astra")));

        // Verifies that service#save was only called once
        verify(service, times(1)).save(Mockito.any());
    } 
    // ...
}
```

#### 2.b - CarManagerService Test

The following code sets up the mock repository, that is injected into the service with the anotation **@InjectMocks**, and tests the service using it. This is a strandard unit test without envolvement of the tests features of Spring Boot.

```java
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
    // ...
}
```

#### 2.c - CarRepository Test

The following code tests the repository using an **TestEntityManager** instance that adds the entities to the database. After that, the repository is used to retrive the entities.

```java
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
        assertThat(found).isEqualTo(opel);
    }
    // ...
}
```

#### 2.d - Integration Test

The following code test the API as a whole using an **TestRestTemplate** instance that acts as a REST client and creates realistic requests involving un/marshalling of the messages. The test database is the in-memory solution H2, and it's automatically configured, as expressed by the  **AutoConfigureTestDatabase** anotation.

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
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
        assertThat(found).extracting(Car::getMaker).containsOnly(opel.getMaker());
    }
    // ...
}
```

### 3.3 - Integration Test (with a real database)

Taking the previous developed integration test, we will now use a real database instead of the in-memory solution H2.

##### Setup

1. Start an instance of **MySQL** inside a Docker container.
   
   ```bash
   docker run --name tqs_lab3_3 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tqs_lab3_3 -e MYSQL_USER=user -e MYSQL_PASSWORD=user -p 33060:3306 -d mysql/mysql-server:5.7
   ```

2. Include a dependency for **MySQL** inside the project's *pom.xml*.
   
   ```xml
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

3. Add *application-integrationtest.properties* inside *src/test/resources/tqs/lab3/lab3_2_cars_service/*. This file should <u>follow the folder structure where the tests are located</u>, *src/test/java/tqs/lab3/lab3_2_cars_service/*.
   
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:33060/tqs_lab3_3
   spring.jpa.hibernate.ddl-auto=create-drop
   spring.datasource.username=user
   spring.datasource.password=user
   ```

4. Use the **@TestPropertySource** anotation instead of **@AutoConfigureTestDatabase**.
   
   ```java
   @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
   // @AutoConfigureTestDatabase
   @TestPropertySource( locations = "application-integrationtest.properties")
   public class d_CarRestControllerTemplateIT { ... }
   ```
