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
