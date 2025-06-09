### Scenario
Fetching the latest 20 orders for a customer (`findRecentOrders()`) suddenly became slow (~3 s).

### Objective
Rewrite the query or tune the database so the endpoint responds in < 200 ms.


### Steps to Reproduce
Compile with mvn clean package.
Run unit tests to observe failure using 'mvn test'
Final objective is to make test case pass.
You can run test cases many times and refactor your code.
