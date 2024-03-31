/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Tables;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
/**
 *
 * @author Admin
 */
public class TablesDAO extends DBContext {
    private static final String INSERT_NEW_TABLE = "INSERT INTO [Tables] (TableCapacity) VALUES (?,?)";
    private static final String GET_ALL_TABLES = "SELECT TableID, TableCapacity, CustomerPhoneNumber, DateTimeUsed, InvoiceRequest FROM [Tables]";
    private static final String GET_TABLE_ID_BY_CUSTOMER = "SELECT TableID FROM [Tables] WHERE CustomerPhoneNumber=? AND DateTimeUsed <= ?";
    private static final String GET_PAY_REQUESTS = "SELECT TableID, TableCapacity, CustomerPhoneNumber, DateTimeUsed, InvoiceRequest FROM [Tables] WHERE InvoiceRequest=1";
    private static final String GET_TABLE_BY_ID = "SELECT TableID, TableCapacity, CustomerPhoneNumber, DateTimeUsed, InvoiceRequest FROM [Tables] WHERE TableId=?";
    private static final String GET_AVAILABLE_TABLES = "SELECT TableID, TableCapacity, CustomerPhoneNumber, DateTimeUsed, InvoiceRequest FROM [Tables] WHERE CustomerPhoneNumber IS NULL AND DateTimeUsed IS NULL";
    private static final String UPDATE_TABLE_CUSTOMER = "UPDATE [Tables] SET CustomerPhoneNumber=?, DateTimeUsed=? WHERE TableID=?";
    private static final String UPDATE_TABLE = "UPDATE [Tables] SET TableCapacity = ? WHERE TableID = ?";
    private static final String SEND_INVOICE_REQUEST = "UPDATE [Tables] SET InvoiceRequest = 1 WHERE TableID = ? AND InvoiceRequest = 0";
    private static final String REMOVE_INVOICE_REQUEST = "UPDATE [Tables] SET InvoiceRequest = 0 WHERE TableID = ? AND InvoiceRequest = 1";
    private static final String COUNT_AVAILABLE_TABLES = "SELECT COUNT(TableId) AS Counted FROM [Tables] WHERE CustomerPhoneNumber IS NULL AND DateTimeUsed IS NULL";
    public List<Tables> getAllTables() throws SQLException {
        List<Tables> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_ALL_TABLES);
            rs = st.executeQuery();
            while (rs.next()) {
                Tables t = new Tables(rs.getInt("TableID"),
                        rs.getInt("TableCapacity"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeUsed"),
                        rs.getInt("InvoiceRequest")
                );
                list.add(t);
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public List<Tables> getAvailableTables() throws SQLException {
        List<Tables> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_AVAILABLE_TABLES);
            rs = st.executeQuery();
            while (rs.next()) {
                Tables t = new Tables(rs.getInt("TableID"),
                        rs.getInt("TableCapacity"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeUsed"),
                        rs.getInt("InvoiceRequest")
                );
                list.add(t);
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public List<Tables> getInvoiceRequests() throws SQLException {
        List<Tables> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_PAY_REQUESTS);
            rs = st.executeQuery();
            while (rs.next()) {
                Tables t = new Tables(rs.getInt("TableID"),
                        rs.getInt("TableCapacity"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeUsed"),
                        rs.getInt("InvoiceRequest")
                );
                list.add(t);
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public Tables getTableById(int id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            if(connection != null) {
                st = connection.prepareStatement(GET_TABLE_BY_ID);
                st.setInt(1, id);
                rs = st.executeQuery();
            }
            if(rs!=null && rs.next()) {
                Tables t = new Tables(rs.getInt("TableID"),
                        rs.getInt("TableCapacity"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeUsed"),
                        rs.getInt("InvoiceRequest")
                );
                return t;
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return null;
    }
    
    public int getTableIdByCustomer(String phoneNo, Date dateTimeUsed) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            if(connection != null) {
                st = connection.prepareStatement(GET_TABLE_ID_BY_CUSTOMER);
                st.setString(1, phoneNo);
                st.setTimestamp(2, new java.sql.Timestamp(dateTimeUsed.getTime()));
                rs = st.executeQuery();
            }
            if(rs!=null && rs.next()) {
                return rs.getInt("TableID");
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return 0;
    }
    
    public int countAvailableTables() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            if(connection != null) {
                st = connection.prepareStatement(COUNT_AVAILABLE_TABLES);
                rs = st.executeQuery();
            }
            if(rs!=null && rs.next()) {
                return rs.getInt("Counted");
            }
        } catch(SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return -1;
    }
    
    public boolean insertNewTable(int tableCapacity, int isUsed) throws SQLException{
        return insertUpdateDelete(INSERT_NEW_TABLE, tableCapacity, isUsed);
    }
    
    public boolean updateTable(int tableCapacity, int isUsed, int tableId) throws SQLException{
        return insertUpdateDelete(UPDATE_TABLE, tableCapacity, isUsed, tableId);
    }
    
    public boolean updateTableCustomer(String customerPhoneNumber, Date dateTimeUsed, int tableId) throws SQLException{
        return insertUpdateDelete(UPDATE_TABLE_CUSTOMER, customerPhoneNumber, dateTimeUsed, tableId);
    }
    
    public boolean updateInvoiceRequest(int tableId, boolean sendOrRemove) throws SQLException{
        if(sendOrRemove) return insertUpdateDelete(SEND_INVOICE_REQUEST, tableId);
        else return insertUpdateDelete(REMOVE_INVOICE_REQUEST, tableId);
    }
    
//    public static void main(String[] args) {
//        TablesDAO tDao = new TablesDAO();
//        try {
//            System.out.println(tDao.updateInvoiceRequest(1, false));
//            //System.out.println(tDao.updateTableCustomer("0986169159", new Date(), 1));
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }
}
