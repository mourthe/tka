
## How to run the service locally? 

This project is configured to run with Docker Compose. You need to have docker and [Gradle](https://docs.gradle.org), version 7.2, and JDK 11 installed locally to run the API locally.
    
### Docker compose 
1. To start the service for the first time, you need to build it first. The build process will run all the tests and lint. 
        
    
    ./graldew build 
2. After the build run successfully, execute:


    docker-compose up --build -d

### Checking
1. Access the API

    
    http://localhost:8080/actuator/health

### Available endpoint
|  Path                                  | Method | Description                                                     |
|----------------------------------------|--------|-----------------------------------------------------------------|
| http://localhost:8080/api/v1/calculate | POST   | Returns the user risk score based on their personal information |

### Calculate risk score

**Request**
```shell
curl --location --request POST 'http://localhost:8080/api/v1/calculate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "age": 35,
    "dependents": 2,
    "house": {
        "ownership_status": "owned"
    },
    "income": 0,
    "marital_status": "married",
    "risk_questions": [
        0,
        1,
        0
    ],
    "vehicle": {
        "year": 2018
    }
}'
```

**Response**
```shell
Status 200 OK
```
```json
{
    "auto": "regular",
    "disability": "ineligible",
    "home": "economic",
    "life": "regular"
}
```

## Technical decisions
I developed this service using kotlin, the programming language I used the most in my current job with Gradle. As peer architecture, I decided to interpret the clean architecture with smaller size and complexity, making the development process faster.

The main packages on the service are as follows:
- **application**:  Responsible for all entry points of the service; in this case, only the controller handles the HTTP Request.
- **domain**:  Responsible for all the business rules implemented inside the package 'use-case' and models regarding its features.
- **infrastructure**: Responsible for all configuration and, if needed, any call to an external dependency, either another API or a database.  

I chose to use Springboot, and that decision made me use its components on the Controller class inside the application layer. Making it simple and effective to code.

I used Jackson to map the JSON to the class on the domain package. I understand that it's not ideal having the domain of the API knowing JSON, but it was a tradeoff. Creating another object to serve only as a DTO with the same attributes and structure and mapping the conversion would add unnecessary complexity.

Regarding the tests, I developed unit tests for the Controller and Use-cases using Mockk to mock an external class's behavior and JUnit to run it. The integration test uses SpringBootTest, with TestRestTemplate executing the HTTP Request and JUnit to run it.

The configuration package only has the MDC Extention to replace the placeholders on the log pattern, make all logs traceable by the id inserted there, and retrieve its value to pass to any other application that this API may call in the future and the LoggerConfiguration.

About the following steps, it depends on what will the feature needs. If other services store some user information, we can make the call and the scoring system asynchronous. Otherwise, maybe the payload increases to the point that a more complex rule engine is desired and necessary.

I tried to make the system as simple as can be. On my previous attempts, I've tried using Coroutines, and later a rule-engine, but felt as either one was over-engineering for the task at hand. I understand that business rules can get more complex as the system grows, with more user information to be processed or more lines of insurance to score. But for this number of rules and grades, I believe that simple is better. 