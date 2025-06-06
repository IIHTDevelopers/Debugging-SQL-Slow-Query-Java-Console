### Scenario
Fetching the latest 20 orders for a customer (`/orders?customerId=...`) suddenly became slow (~3 s).

### Objective
Rewrite the query or tune the database so the endpoint responds in < 200 ms.


#### Steps to Reproduce
1. Compile with `mvn clean package`.
2. Run unit tests or invoke main method to observe failure.
