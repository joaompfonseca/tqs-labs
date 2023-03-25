## Lab 5 - Behavior-driven development (Cucumber)

##### Key concepts

- **Gherkin:** used to describe features (tests scenarios) in a language readable by non-programmers.

- **Cucumber:** executes features written in Gherkin.

- Each step in a scenario is mapped into a Java test method, by annotating them with matching expressions (Regex or Cucumber expessions).

### 5.1 - Getting started

A `.feature` will match with a "test runner" class that activates the test steps inside the same package. This class should be anotated with:

- **@Suite**

- **@IncludeEngines("cucumber")**

- **@SelectClasspathResource("tqs/lab5/lab5_1_getting_started")**

### 5.2 - Books

##### Cucumber parameter types

| Parameter Type | Description                                                                                                                                                                                                                                                                                                       |
| -------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `{int}`        | Matches integers, for example `71` or `-19`. Converts to a 32-bit signed integer if the platform supports it.                                                                                                                                                                                                     |
| `{float}`      | Matches floats, for example `3.6`, `.8` or `-9.2`. Converts to a 32 bit float if the platform supports it.                                                                                                                                                                                                        |
| `{word}`       | Matches words without whitespace, for example `banana` (but not `banana split`).                                                                                                                                                                                                                                  |
| `{string}`     | Matches single-quoted or double-quoted strings, for example `"banana split"` or `'banana split'` (but not `banana split`). Only the text between the quotes will be extracted. The quotes themselves are discarded. Empty pairs of quotes are valid and will be matched and passed to step code as empty strings. |
| `{}` anonymous | Matches anything (`/.*/`).                                                                                                                                                                                                                                                                                        |
| `{bigdecimal}` | Matches the same as `{float}`, but converts to a `BigDecimal` if the platform supports it.                                                                                                                                                                                                                        |
| `{double}`     | Matches the same as `{float}`, but converts to a 64 bit float if the platform supports it.                                                                                                                                                                                                                        |
| `{biginteger}` | Matches the same as `{int}`, but converts to a `BigInteger` if the platform supports it.                                                                                                                                                                                                                          |
| `{byte}`       | Matches the same as `{int}`, but converts to an 8 bit signed integer if the platform supports it.                                                                                                                                                                                                                 |
| `{short}`      | Matches the same as `{int}`, but converts to a 16 bit signed integer if the platform supports it.                                                                                                                                                                                                                 |
| `{long}`       | Matches the same as `{int}`, but converts to a 64 bit signed integer if the platform supports it.                                                                                                                                                                                                                 |

To handle dates or <u>parameter types not supported</u> by Cucumber, use **@ParameterType** and use a matching expression inside brackets:

```java
@ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
public LocalDate iso8601Date(String year, String month, String day) {
    return Utils.isoTextToLocalDate(year, month, day);
}
```

To handle <u>data tables</u> in the feature description, use **@DataTableType** which converts each row of the table into a key-value pair of Strings (where the key is the name of the column):

```java
@DataTableType
public Book bookEntry(Map<String, String> entry) {
    return new Book(
            entry.get("title"),
            entry.get("author"),
            Utils.isoTextToLocalDate(entry.get("published")));
}

@Given("the library has the following books:")
public void addBooks(List<Book> books) {
    books.forEach(library::addBook);
}
```

### 5.3 - Web automation

The test scenario was adapted from the previous laboratory exercises:

```gherkin
Feature: BlazeDemo

  Scenario: Buying a trip
    When I navigate to 'https://blazedemo.com'
    And I choose a trip from 'Boston' to 'New York'
    And I click find flights
    Then I should see the list of flights from 'Boston' to 'New York'

    When I choose the flight in row 1
    Then I should see the purchase form page

    When I fill in the form with
      | key              | value                  |
      | inputName        | João Fonseca           |
      | address          | Universidade de Aveiro |
      | city             | Aveiro                 |
      | state            | Aveiro                 |
      | zipCode          | 12345                  |
      | cardType         | Diner's Club           |
      | creditCardNumber | 12345                  |
      | creditCardMonth  | 3                      |
      | creditCardYear   | 2023                   |
      | nameOnCard       | João Fonseca           |
      | rememberMe       | True                   |
    Then I should see the remember me checkbox checked

    When I click purchase flight
    Then I should see the confirmation page
    And I should see 'BlazeDemo Confirmation' in the title
```
