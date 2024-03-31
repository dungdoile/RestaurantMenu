/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author Admin
 */
import Model.Customers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersDAO extends DBContext {

    private static final String GET_ALL_CUSTOMERS = "SELECT CustomerPhoneNumber, CustomerName FROM [Customers]";
    private static final String GET_CUSTOMER_BY_PHONE = "SELECT CustomerPhoneNumber, CustomerName FROM [Customers] WHERE CustomerPhoneNumber=?";
    private static final String INSERT_NEW_CUSTOMER = "INSERT INTO [Customers] (CustomerPhoneNumber, CustomerName) VALUES (?,?)";
    private static final String UPDATE_CUSTOMER_INFO = "UPDATE [Customers] SET CustomerPhoneNumber=? WHERE CustomerPhoneNumber=?";
    private static final String DELETE_CUSTOMER = "DELETE FROM [Customers] WHERE CustomerPhoneNumber=?";

    public List<Customers> getAllCustomers() throws SQLException {
        List<Customers> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_ALL_CUSTOMERS);
            rs = st.executeQuery();

            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getString("CustomerPhoneNumber"),
                        rs.getString("CustomerName")
                );
                list.add(customer);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }

        return list;
    }
    
    public Customers getCustomerByPhoneNo(String phoneNumber) throws SQLException {
        Customers customer = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_CUSTOMER_BY_PHONE);
            st.setString(1, phoneNumber);
            rs = st.executeQuery();

            if (rs.next()) {
                customer = new Customers(
                        rs.getString("CustomerPhoneNumber"),
                        rs.getString("CustomerName")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }

        return customer;
    }
    
    public boolean insertNewCustomer(String customerPhoneNo, String customerName) throws SQLException {
        return insertUpdateDelete(INSERT_NEW_CUSTOMER, customerPhoneNo, customerName);
    }

    public boolean updateCustomerInfo(String newPhoneNo, String oldPhoneNo) throws SQLException {
        return insertUpdateDelete(UPDATE_CUSTOMER_INFO, newPhoneNo, oldPhoneNo);
    }

    public boolean deleteCustomer(String phoneNumber) throws SQLException {
        return insertUpdateDelete(DELETE_CUSTOMER, phoneNumber);
    }
    
    public static void main(String[] args) {
        CustomersDAO cDao = new CustomersDAO();
        try {
            cDao.insertNewCustomer("0701312321", "Someone");
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
}
