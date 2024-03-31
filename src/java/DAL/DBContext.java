/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DBContext {

    protected static Connection connection;

    public DBContext() {
        try {
            // Edit URL , username, password to authenticate with your MS SQL Server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=RestaurantDemo";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
    protected boolean insertUpdateDelete(String query, Object... parameters) throws SQLException {
        boolean check = false;
        PreparedStatement psm = null;

        try {
            psm = connection.prepareStatement(query);

            for (int i = 0; i < parameters.length; i++) {
                psm.setObject(i + 1, parameters[i]);
            }

            check = psm.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            //closeResources(psm, null);
        }

        return check;
    }

    protected void closeResources(PreparedStatement st, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
    }
    
}
