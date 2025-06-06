
package com.genspark.task3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    private final Connection conn;

    public Main(Connection conn) {
        this.conn = conn;
    }

    public ResultSet findRecentOrders(int customerId) throws Exception {
        // BUG: leading wildcard like '%?%' prevents index use; also ORDER BY missing index column
       String sql = "SELECT * FROM orders WHERE CAST(customer_id AS VARCHAR) LIKE '%" + customerId + "%' ORDER BY created_at DESC LIMIT 20";
        PreparedStatement ps = conn.prepareStatement(sql);
    	//String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY created_at DESC LIMIT 20";
    	//PreparedStatement ps = conn.prepareStatement(sql);
    	//ps.setInt(1, customerId);
        return ps.executeQuery();
    }
}
