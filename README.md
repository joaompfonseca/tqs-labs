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
