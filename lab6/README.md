## Lab 6 - Static Code analysis (with SonarQube)

##### Key concepts

- **Static code analysis:** assesses a code base to produce quality metrics such as:
  
  - Problems likely to produce errors.
  
  - Vulnerabilities (security/reliability concerns).
  
  - Code smells (bad/poor practice or coding style).
  
  - Coverage (ratio tested/total).
  
  - Code complexity.

- **Technical debt:** estimated effort to correct the vulnerabilities. Used by software quality engineers to obtain realistic information of it.

- **Sonar vs linters:**
  
  - **Linter:** tool that runs and provides feedback.
  
  - **Sonar:** tool that combines several linters over different programming languages, and provides feedback through the means of an information system.

- **SonarQube:** self-hosted solution of Sonar, can be run inside a Docker container. Alternatives are SonarLint (runs in the IDE) and SonarCloud (runs as a SaaS).

### 6.1 - Local analysis

##### Installing a local instance of SonarQube

1. Run a local instance of SonarQube server using Docker.
   
   ```bash
   docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
   ```

2. Access [http://localhost:9000](http://localhost:9000) and insert the login credentials.
   
   - **login:** admin
   
   - **password:** admin

3. Change the credentials.

##### Analysing a project

1. Create a project *manually*.

2. Provide a name - **lab6_1_local_analysis**.

3. Analyse the project *locally*.

4. Generate a token - **sqp_04ed513da0a7242ab7498d8d84606b96635a2da6**.
   
   **Considering a Maven project**

5. Add the Sonar plugin to the *pom.xml*.
   
   ```xml
   <plugin>
     <groupId>org.sonarsource.scanner.maven</groupId>
     <artifactId>sonar-maven-plugin</artifactId>
     <version>3.9.1.2184</version>
   </plugin>
   ```

6. Run the analysis in a **Maven** project (use **CMD** instead of **Powershell**).
   
   ```bash
   mvn clean verify sonar:sonar -Dsonar.projectKey=lab6_1_local_analysis -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_04ed513da0a7242ab7498d8d84606b96635a2da6
   ```

##### Questions

**f) Has your project passed the defined quality gate?**

The assessed project (Euromillions from Lab 1.2) **passed** the defined quality gate. The quality gate that was considered was the built-in provided by SonarQube - **Sonar Way**.

**Conditions on New Code**

| Metric                     | Operator        | Value                                      |
| -------------------------- | --------------- | ------------------------------------------ |
| Coverage                   | is less than    | 80.0%                                      |
| Duplicated Lines (%)       | is greater than | 3.0%                                       |
| Maintainability Rating     | is worse than   | A (Technical debt ratio is less than 5.0%) |
| Reliability Rating         | is worse than   | A (No bugs)                                |
| Security Hotspots Reviewed | is less than    | 100%                                       |
| Security Rating            | is worse than   | A (No vulnerabilities)                     |

**Conditions on Overall Code**

None.

**g) Explore the analysis results and complete with a few sample issues, as applicable.**

The assessed project (Euromillions from Lab 1.2) contained **only** code smell issues.

| Issue              | Problem description                                                                                                                              | How to solve                                                                                                                                                                                   |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Code smell (major) | **Invoke method(s) only conditionally.**<br>"Preconditions" and logging arguments should not require evaluation.                                 | Compute the logging arguments before passing it to the method.                                                                                                                                 |
| Code smell (major) | **Refactor the code in order to not assign to this loop counter from within the loop body.** <br>"for" loop stop conditions should be invariant. | Do not change the loop counter variable inside the loop itself. It should be invariant during an entire loop iteration, and should be only changed in the loop header, right before it begins. |

### 6.2 - Technical debt

##### SonarQube configuration

- **Name:** lab6_2_technical_debt

- **Token:** sqp_4f68bf7b3b4c588fbce70146a8e2e919435493ff

Command to run the analysis:

```bash
mvn clean verify sonar:sonar -Dsonar.projectKey=lab6_2_technical_debt -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_4f68bf7b3b4c588fbce70146a8e2e919435493ff
```

##### Questions

**a) Take note of the technical debt found. Explain what this value means. Document the analysis findings with a screenshot.**

The technical debt is 40 min. This value is a measure of the effort required to fix all code smells. When the value is shown in days, it is assumed a 8-hour day.

<img title="" src=".\images\lab6_2_1_dashboard.png" alt="" data-align="center">

**b) Correct the severe (critical and major) code smells.**

There were no code smells in those categories.

**d) Run the static analysis with coverage enabled (by adding the jacoco plugin). How many lines are "not covered"? And how many conditions?**

There are 5 uncovered lines and 31 uncovered conditions

<img title="" src=".\images/lab6_2_2_dashboard.png" alt="" data-align="center">

### 6.3 - Custom QG

For this exercise, I considered the backend module of my IES group project, available [here](https://github.com/BusRush/IES_Project/tree/main/busrush).

##### SonarQube configuration

- **Name:** lab6_3_custom_qg

- **Token:** sqp_326aca85210da606594345e943adc2de3693c85e

Command to run the analysis:

```bash
mvn clean verify sonar:sonar -Dsonar.projectKey=lab6_3_custom_qg -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_326aca85210da606594345e943adc2de3693c85e -Dmaven.test.skip=true
```

For this analysis to work, I had to disable the SCM sensor in SonarQube project settings, otherwise it wouldn't detect any source files.

#### 3.a - Defining a custom Quality Gate

After a first analysis, this was the resulting dashboard.

<img title="" src=".\images\lab6_3_1_dashboard.png" alt="" data-align="center">

To define the custom Quality Gate, the following ideas were taken into account:

- **Code Coverage:** removed, no tests were created at the time.

- **Duplicated Lines (5%):** increased to 5%, it is still an acceptable amount.

- **Maintainability Rating:** kept the same, complies with the Clean as You Code methodology.

- **Reliability Rating:** maintained, complies with the Clean as You Code methodology.

- **Security Hotspots Reviewed:** removed, it wasn't considered at the time.

- **Security Rating:** removed, it wasn't considered at the time.

In the end, it doesn't fully comply to the Clean as You Code methodology, but it needs to be taking into account that the project was developed without following it.

<img title="" src=".\images\lab6_3_2_custom_qg.png" alt="" data-align="center">

Applying this quality gate to the project, after another analysis these were the results.

<img title="" src=".\images\lab6_3_3_dashboard.png" alt="" data-align="center">

To break the quality gate, we could just introduce **1 bug**. A simple bug is the very popular NullPointerException, that occurs when a variable's attributes/methods are tried to be accessed, altought its value is null.

Let's consider the following piece of working code.

```java
public ResponseEntity<UserCrudDto> getUserByUsername(String username) {
    try {
        Optional<User> _user = userRepository.findByUsername(username);
        if (_user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User user = _user.get();

        UserCrudDto userCrudDto = new UserCrudDto(
                user.getUsername(),
                user.getPassword()
        );
        return new ResponseEntity<>(userCrudDto, HttpStatus.OK);
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

Let's introduce a possible NullPointerException, by trying to access **user#getUsername** when **user** is null.

```java
public ResponseEntity<UserCrudDto> getUserByUsername(String username) {
    try {
        User user = userRepository.findByUsername(username).orElse(null);

        UserCrudDto userCrudDto = new UserCrudDto(
                user.getUsername(),
                user.getPassword()
        );
        return new ResponseEntity<>(userCrudDto, HttpStatus.OK);
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

After another analysis these were the results.

<img title="" src=".\images\lab6_3_4_dashboard.png" alt="" data-align="center">

<img title="" src=".\images\lab6_3_5_issue.png" alt="" data-align="center">

By viewing the dashboard, we can clearly see the quality gate was broken, and the bug it detected in the analysis was the one we introduced in the code. The reliability went from A to C.
