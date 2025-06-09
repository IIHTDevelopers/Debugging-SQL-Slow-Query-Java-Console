package com.genspark.task3;

import org.junit.jupiter.api.Test;

import java.sql.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.genspark.task3.utils.TestUtils.businessTestFile;
import static com.genspark.task3.utils.TestUtils.currentTest;
import static com.genspark.task3.utils.TestUtils.testReport;
import static com.genspark.task3.utils.TestUtils.yakshaAssert;

public class QueryTest {

    @Test
    public void testFindRecentOrdersPerformance() throws Exception {
        // Set up in-memory H2 database
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        Statement stmt = conn.createStatement();

        // Create table and index on customer_id and created_at
        stmt.execute("CREATE TABLE orders (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "customer_id INT, " +
                "created_at TIMESTAMP, " +
                "details VARCHAR(255))");

        stmt.execute("CREATE INDEX idx_customer_created ON orders(customer_id, created_at DESC)");

        // Insert sample data
        PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO orders (customer_id, created_at, details) VALUES (?, ?, ?)");
        for (int i = 0; i < 1000; i++) {
            insertStmt.setInt(1, 12345);
            insertStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis() - i * 1000));
            insertStmt.setString(3, "Order " + i);
            insertStmt.executeUpdate();
        }

        Main main = new Main(conn);

        // Measure execution time
        long start = System.currentTimeMillis();
        ResultSet rs = main.findRecentOrders(12345);
        long duration = System.currentTimeMillis() - start;

        // Count results to consume ResultSet
        int count = 0;
        while (rs.next()) {
            count++;
        }
        System.out.println("Duration : " + duration);
        // Assertions
        yakshaAssert(currentTest(), (duration < 200 && count <=20), businessTestFile);
        //assertTrue(duration < 20, "Query took too long: " + duration + " ms");
        //assertTrue(count <= 20, "Expected max 20 orders, but got: " + count);
    }
}
