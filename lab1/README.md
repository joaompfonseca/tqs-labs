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

| Package          | Class                | Instruction Coverage | Methods                                                                                                                                    |
| ---------------- | -------------------- | -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| tqs.euromillions | CuponEuroMillions    | 34%                  | format() (0%)<br>countDips() (0%)                                                                                                          |
| tqs.sets         | BoundedSetOfNaturals | 54%                  | fromArray(int[]) (0%)<br>add(int) (56%)<br>hashCode() (0%)<br>equals(Object) (76%)<br>size() (0%)<br>intersects(BoundedSetOfNaturals) (0%) |

**II. Are all possible [decision] branches being covered?** No. The following table presents some examples of low branch coverage in the project.

| Package          | Class                    | Branch Coverage |
| ---------------- | ------------------------ | --------------- |
| tqs.euromillions | CuponEuromillions<br>Dip | 0%<br>77%       |
| tqs.sets         | BoundedSetOfNaturals     | 50%             |

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
