# Wealth Management  

## Built With
* Java
* Spring Boot - Framework
* Maven - Build tool
* h2 - In-memory database

### Other libraries used
* Junit5 - For testing
* Mockito - For mocking in tests
* Lombok - For logging, auto code generation

## Follow steps to build and run the project using command line
```bash
mvn clean install

java -jar target/wealthmangement-0.0.1-SNAPSHOT.jar

```

## Services offer following endpoints
Service loads customer.csv and strategy.csv on application load. 

#### GET /api/strategies 
Returns all saved strategies read from csv
```console
curl -X GET http://localhost:8018/api/strategies
```

#### GET /api/customers 
Returns all saved customers read from csv
```console
curl -X GET http://localhost:8018/api/customers
```

#### POST /api/strategies?customerId
Finds and assigns a suitable strategy to customer based on min/max risk level and min/max years of retirement. 
The logic of choosing the best fit strategy is shifted to database level and it may return multiple suitable strategy. 
This can be extended furter with more specified requirements. For now, it returns top 1 of the suitable strategies. 
If no suitable strategy is found default cash strategy is assigned to the customer.
 
```console
curl -X POST http://localhost:8018/api/strategies?customerId=1
```

#### POST /api/customers/{customerId}/rebalance
Rebalances customer portfolio of a given customer based on assigned strategy to the customer.  
```console
curl -X POST http://localhost:8018/api/customers/1/rebalance
```
