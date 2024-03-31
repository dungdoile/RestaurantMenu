/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

/**
 *
 * @author Admin
 */

import Model.Employees;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO extends DBContext {

    private static final String GET_ALL_EMPLOYEES = "SELECT EmployeeID, EmployeeName, EmployeePhoneNumber, EmployeeEmail, Position FROM Employees";
    private static final String GET_EMPLOYEES_BY_POSITION_ID = "SELECT EmployeeID, EmployeeName, EmployeePhoneNumber, EmployeeEmail, Position FROM Employees WHERE Position=?";
    private static final String GET_EMPLOYEE_BY_ID = "SELECT EmployeeID, EmployeeName, EmployeePhoneNumber, EmployeeEmail, Position FROM Employees WHERE EmployeeID=?";
    private static final String INSERT_NEW_EMPLOYEE = "INSERT INTO Employees (EmployeeName, EmployeePhoneNumber, EmployeeEmail, Position, LoginPassword) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE Employees SET EmployeePhoneNumber = ?, EmployeeEmail = ?, Position = ? WHERE EmployeeID = ?";
    private static final String UPDATE_LOGIN_PASSWORD = "UPDATE Employees SET LoginPassword = ? WHERE EmployeeEmail = ?";
    private static final String DELETE_EMPLOYEE = "DELETE FROM Employees WHERE EmployeeID = ?";
    private static final String FETCH_LOGIN_DATA = "SELECT EmployeeID, EmployeeName, EmployeePhoneNumber, Position FROM Employees WHERE EmployeeEmail=? AND LoginPassword=?";

    public List<Employees> getAllEmployees() throws SQLException {
        List<Employees> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_ALL_EMPLOYEES);
            rs = st.executeQuery();

            while (rs.next()) {
                Employees employee = new Employees(rs.getInt("EmployeeID"),
                        rs.getString("EmployeeName"),
                        rs.getString("EmployeePhoneNumber"),
                        rs.getString("EmployeeEmail"),
                        rs.getString("Position"));
                list.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public List<Employees> getEmployeesByPosition(String position) throws SQLException {
        List<Employees> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_EMPLOYEES_BY_POSITION_ID);
            st.setString(1, position);
            rs = st.executeQuery();

            while (rs.next()) {
                Employees employee = new Employees(rs.getInt("EmployeeID"),
                        rs.getString("EmployeeName"),
                        rs.getString("EmployeePhoneNumber"),
                        rs.getString("EmployeeEmail"),
                        rs.getString("Position"));
                list.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }

    public Employees getEmployeeById(int id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_EMPLOYEE_BY_ID);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs != null && rs.next()) {
                return new Employees(rs.getInt("EmployeeID"),
                        rs.getString("EmployeeName"),
                        rs.getString("EmployeePhoneNumber"),
                        rs.getString("EmployeeEmail"),
                        rs.getString("Position"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return null;
    }

    public boolean insertNewEmployee(String employeeName, String employeePhoneNo,
                                    String employeeEmail, String employeePosition, 
                                    String employeeLoginPassword) throws SQLException {
        return insertUpdateDelete(INSERT_NEW_EMPLOYEE, employeeName, 
                                employeePhoneNo, employeeEmail, 
                                employeePosition, employeeLoginPassword);
    }

    public boolean updateEmployee(String employeePhoneNo, String employeeEmail, String employeePosition, 
                                    int employeeId) throws SQLException {
        return insertUpdateDelete(UPDATE_EMPLOYEE, employeePhoneNo, employeeEmail, 
                                    employeePosition, employeeId);
    }
    
    public boolean updateLoginPassword(String newPassword, String employeeEmail) throws SQLException {
        return insertUpdateDelete(UPDATE_LOGIN_PASSWORD, newPassword, employeeEmail);
    }

    public boolean deleteEmployee(int employeeId) throws SQLException {
        return insertUpdateDelete(DELETE_EMPLOYEE, employeeId);
    }

    public Employees checkEmployeeLogin(String employeeEmail, String loginPassword) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(FETCH_LOGIN_DATA);
            st.setString(1, employeeEmail);
            st.setString(2, loginPassword);
            rs = st.executeQuery();
            if (rs.next()) {
                return new Employees(rs.getInt("EmployeeID"),
                        rs.getString("EmployeeName"),
                        rs.getString("EmployeePhoneNumber"),
                        employeeEmail,
                        rs.getString("Position"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return null;
    }
    
}

