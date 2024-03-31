/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author Admin
 */
import Model.DineInOrders;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DineInOrdersDAO extends DBContext {
    private static final String GET_ALL_DINE_IN_ORDERS = "SELECT TableID, DishID, Quantity, DateTimeOrdered, ServiceStatus, Rush FROM DineInOrders ORDER BY Rush DESC, DateTimeOrdered";
    private static final String GET_DINE_IN_ORDERS_BY_TABLE_ID = "SELECT TableID, DishID, Quantity, DateTimeOrdered, ServiceStatus, Rush FROM DineInOrders WHERE TableID=? ORDER BY Rush DESC, DateTimeOrdered";
    private static final String GET_DINE_IN_ORDERS_BETWEEN = "SELECT TableID, DishID, Quantity, DateTimeOrdered, ServiceStatus, Rush FROM DineInOrders WHERE TableID=? AND DateTimeOrdered BETWEEN ? AND ? ORDER BY Rush DESC, DateTimeOrdered";
    private static final String GET_DINE_IN_ORDERS_BY_SERVICE_STATUS = "SELECT TableID, DishID, Quantity, DateTimeOrdered, ServiceStatus, Rush FROM DineInOrders WHERE ServiceStatus=? ORDER BY Rush DESC, DateTimeOrdered";
    private static final String GET_RUSH_ORDERS = "SELECT TableID, DishID, Quantity, DateTimeOrdered, ServiceStatus, Rush FROM DineInOrders WHERE Rush=1 AND ServiceStatus!='Served'";
    private static final String INSERT_NEW_DINE_IN_ORDER = "INSERT INTO DineInOrders (TableID, DishID, Quantity, DateTimeOrdered) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ORDER_SERVICE_STATUS = "UPDATE DineInOrders SET ServiceStatus=? WHERE TableID=? AND DishID=?";
    private static final String UPDATE_RUSH = "UPDATE DineInOrders SET Rush=1 WHERE TableID=? AND DishID=? AND Rush=0";
    private static final String DELETE_DINE_IN_ORDERS = "DELETE FROM DineInOrders WHERE TableID = ?";

    public List<DineInOrders> getAllDineInOrders() throws SQLException {
        List<DineInOrders> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = connection.prepareStatement(GET_ALL_DINE_IN_ORDERS);
            rs = st.executeQuery();
            while (rs.next()) {
                DineInOrders order = new DineInOrders(rs.getInt("TableID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"),
                        rs.getTimestamp("DateTimeOrdered"),
                        rs.getString("ServiceStatus"),
                        rs.getInt("Rush"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }

    public List<DineInOrders> getDineInOrdersByTableId(int tableID) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DineInOrders> list = new ArrayList<>();
        try {
            st = connection.prepareStatement(GET_DINE_IN_ORDERS_BY_TABLE_ID);
            st.setInt(1, tableID);
            rs = st.executeQuery();
            while (rs.next()) {
                DineInOrders order = new DineInOrders(rs.getInt("TableID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"),
                        rs.getTimestamp("DateTimeOrdered"),
                        rs.getString("ServiceStatus"),
                        rs.getInt("Rush"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public List<DineInOrders> getDineInOrdersBetween(int tableID, Date from, Date to) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DineInOrders> list = new ArrayList<>();
        try {
            st = connection.prepareStatement(GET_DINE_IN_ORDERS_BETWEEN);
            st.setInt(1, tableID);
            st.setTimestamp(2, new java.sql.Timestamp(from.getTime()));
            st.setTimestamp(3, new java.sql.Timestamp(to.getTime()));
            rs = st.executeQuery();
            while (rs.next()) {
                DineInOrders order = new DineInOrders(rs.getInt("TableID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"),
                        rs.getTimestamp("DateTimeOrdered"),
                        rs.getString("ServiceStatus"),
                        rs.getInt("Rush"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public List<DineInOrders> getDineInOrdersByServiceStatus(String serviceStatus) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DineInOrders> list = new ArrayList<>();
        try {
            st = connection.prepareStatement(GET_DINE_IN_ORDERS_BY_SERVICE_STATUS);
            st.setString(1, serviceStatus);
            rs = st.executeQuery();
            while (rs.next()) {
                DineInOrders order = new DineInOrders(rs.getInt("TableID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"),
                        rs.getTimestamp("DateTimeOrdered"),
                        rs.getString("ServiceStatus"),
                        rs.getInt("Rush"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public List<DineInOrders> getRushOrders() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<DineInOrders> list = new ArrayList<>();
        try {
            st = connection.prepareStatement(GET_RUSH_ORDERS);
            rs = st.executeQuery();
            while (rs.next()) {
                DineInOrders order = new DineInOrders(rs.getInt("TableID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"),
                        rs.getTimestamp("DateTimeOrdered"),
                        rs.getString("ServiceStatus"),
                        rs.getInt("Rush"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public boolean insertNewDineInOrder(int tableId, int dishId, int quantity, Date dateTimeOrdered) throws SQLException{
        return insertUpdateDelete(INSERT_NEW_DINE_IN_ORDER, tableId, dishId, quantity, dateTimeOrdered);
    }
    
    public boolean updateOrderServiceStatus(String serviceStatus, int tableID, int dishId) throws SQLException{
        return insertUpdateDelete(UPDATE_ORDER_SERVICE_STATUS, serviceStatus, tableID, dishId);
    }
    
    public boolean rush(int tableId, int dishId) throws SQLException{
        return insertUpdateDelete(UPDATE_RUSH, tableId, dishId);
    }
    
    public boolean deletedDineInOrder(int tableID) throws SQLException{
        return insertUpdateDelete(DELETE_DINE_IN_ORDERS, tableID);
    }
    
    public static void main(String[] args) {
        DineInOrdersDAO diDao = new DineInOrdersDAO();
        try {
            System.out.println(diDao.rush(1, 1));
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
}
