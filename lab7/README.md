## Lab 7 - Additional tools for integration tests (Test Containers, REST Assured)

##### Key concepts

- **REST Assured (DSL):** test library focused on REST APIs.
  
  - Standard (URL).
  
  - SpringBoot integration: **RestAssuredMockMvc**.

- **Test Containers:** makes it easy to deploy, run and dispose a container for a test. The container lifecycle is integrated in the runner environment.
  
  - Standard JPA init.
  
  - DB migrations (flyway).

### 7.1 - Rest Assured

To install RestAssured, add the dependency to the *pom.xml*.

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>
```

The use RestAssured, import the packages.

```java
import static io.restassured.RestAssured.*
import static io.restassured.matcher.RestAssuredMatchers.*
import static org.hamcrest.Matchers.*
```

Example of a RestAssured test.

```java
given()
    .param("x", "y")
.when()
    .get("/lotto")
.then()
    .statusCode(400)
    .body("lotto.lottoId", equalTo(6));
```

### 7.2 - Integration testes for Cars (with Rest Assured DSL)

To use RestAssured with Spring MockMvc, add the dependency to the *pom.xml*.

```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>spring-mock-mvc</artifactId>
    <scope>test</scope>
</dependency>
```

Implement the test as follows.

```java
@WebMvcTest(CarController.class)
public class e_CarControllerRestAssuredTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car opel = new Car("Opel", "Astra");

        // Loads the service mock with the proper expectation
        when(service.save(Mockito.any())).thenReturn(opel);

        // Performs the request
        RestAssuredMockMvc
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(JsonUtils.toJson(opel))
            .when()
                .post("/api/cars")
            .then()
                .statusCode(201)
                .body("maker", is(opel.getMaker()))
                .body("model", is(opel.getModel()));

        // Verifies that service#save was only called once
        verify(service, times(1)).save(Mockito.any());
    }
    // ...
}
```

**Note:** We do not need to provide a full URL for the request since the MockMvc is using a sliced environment, the **CarController** class (as specified in the **@WebMvcTest** anotation).

### 7.3 - Test Containers and database migrations

To use Test Containers (TC) with Flyway Migration in Spring Boot, add the dependencies to the *pom.xml*.

```xml
<dependencies>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
</dependencies>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>testcontainers-bom</artifactId>
            <version>1.17.6</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Implement the test as follows.

```java
@Testcontainers
@SpringBootTest
public class BookIT {

    @Autowired
    private BookRepository repository;

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("user")
            .withPassword("pass")
            .withDatabaseName("library");

    // Discovers the container URL
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Test
    void whenAddBook_thenBookIsPersisted() {
        Book book = new Book("Os Maias", "Eça de Queiroz");
        repository.save(book);

        assertThat(repository.findAll())
                .hasSize(1)
                .extracting(Book::getTitle)
                .containsOnly(book.getTitle());
    }
}
```

### 7.4 - Cars with integration test

An alternative to Flyway is to include the anotation **@TestPropertySource** with the database schema creation method. In this case, we are basing the schema off the classes anotated with **@Entity**.

```java
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create)
public class f_CarRestAssuredTestContainersIT {...}
```

In this test we do not use Flyway - instead, we resort to the default Spring Boot method for creating the database schema.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create") // Alternative to Flyway
public class f_CarRestAssuredTestContainersIT {

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("user")
            .withPassword("pass")
            .withDatabaseName("car_service");

    @LocalServerPort
    int localPort;
    Car opel, honda, mazda;

    @Autowired
    private CarRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        opel = repository.save(new Car("Opel", "Astra"));
        honda = repository.save(new Car("Honda", "Civic"));
        mazda = repository.save(new Car("Mazda", "3"));
    }

    @Test
    public void whenValidInput_thenCreateCar() throws Exception {
        Car tesla = new Car("Tesla", "Model 3");

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(JsonUtils.toJson(tesla))
            .when()
                .post(endpoint)
            .then()
                .statusCode(201)
                .body("maker", is(tesla.getMaker()))

           .body("model", is(tesla.getModel()));
    }
    // ...
}
```

**Note:** If Flyway is in the dependencies, add this to the *application.properties* to prevent the use of it.

```properties
spring.flyway.enabled=false
```
