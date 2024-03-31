/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Invoices;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class InvoicesDAO extends DBContext {
    private static final String GET_ALL_INVOICES = "SELECT InvoiceID, CustomerPhoneNumber, DateTimeCreated, GrossPrice, VAT, Discount, NetPrice, PaymentMethod FROM Invoices ORDER BY DateTimeCreated DESC";
    private static final String GET_INVOICE_BY_ID = "SELECT InvoiceID, CustomerPhoneNumber, DateTimeCreated, GrossPrice, VAT, Discount, NetPrice, PaymentMethod FROM Invoices WHERE InvoiceID=?";
    private static final String GET_INVOICE_BY_CUSTOMER_AND_DATE = "SELECT InvoiceID, CustomerPhoneNumber, DateTimeCreated, GrossPrice, VAT, Discount, NetPrice, PaymentMethod FROM Invoices WHERE CustomerPhoneNumber=? AND DateTimeCreated BETWEEN ? AND ?";
    private static final String GET_INVOICES_BETWEEN = "SELECT InvoiceID, CustomerPhoneNumber, DateTimeCreated, GrossPrice, VAT, Discount, NetPrice, PaymentMethod FROM Invoices WHERE DateTimeCreated BETWEEN ? AND ? ORDER BY DateTimeCreated DESC";
    private static final String INSERT_NEW_INVOICE = "INSERT INTO Invoices (CustomerPhoneNumber, DateTimeCreated, GrossPrice, VAT, Discount, PaymentMethod) VALUES (?, ?, ?, ?, ?, ?) SELECT SCOPE_IDENTITY() AS InvoiceId";
    
    public List<Invoices> getAllInvoices() throws SQLException {
        List<Invoices> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_ALL_INVOICES);
            rs = st.executeQuery();

            while (rs.next()) {
                Invoices invoice = new Invoices(rs.getInt("InvoiceID"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeCreated"),
                        rs.getDouble("GrossPrice"),
                        rs.getDouble("VAT"),
                        rs.getDouble("Discount"),
                        rs.getDouble("NetPrice"),
                        rs.getString("PaymentMethod"));
                list.add(invoice);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }
    
    public List<Invoices> getInvoicesBetween(Date fromDate, Date toDate) throws SQLException {
        List<Invoices> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_INVOICES_BETWEEN);
            st.setDate(1, new java.sql.Date(fromDate.getTime()));
            st.setDate(2, new java.sql.Date(toDate.getTime()));
            rs = st.executeQuery();

            while (rs.next()) {
                Invoices invoice = new Invoices(rs.getInt("InvoiceID"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeCreated"),
                        rs.getDouble("GrossPrice"),
                        rs.getDouble("VAT"),
                        rs.getDouble("Discount"),
                        rs.getDouble("NetPrice"),
                        rs.getString("PaymentMethod"));
                list.add(invoice);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }

    public Invoices getInvoiceById(int id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_INVOICE_BY_ID);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs != null && rs.next()) {
                return new Invoices(rs.getInt("InvoiceID"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeCreated"),
                        rs.getDouble("GrossPrice"),
                        rs.getDouble("VAT"),
                        rs.getDouble("Discount"),
                        rs.getDouble("NetPrice"),
                        rs.getString("PaymentMethod"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return null;
    }
    
    public Invoices getInvoiceByCustomerAndDate(String customerPhoneNo, Date dateTimeFrom, Date dateTimeTo) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_INVOICE_BY_CUSTOMER_AND_DATE);
            st.setString(1, customerPhoneNo);
            st.setTimestamp(2, new java.sql.Timestamp(dateTimeFrom.getTime()));
            st.setTimestamp(3, new java.sql.Timestamp(dateTimeTo.getTime()));
            rs = st.executeQuery();

            if (rs != null && rs.next()) {
                return new Invoices(rs.getInt("InvoiceID"),
                        rs.getString("CustomerPhoneNumber"),
                        rs.getTimestamp("DateTimeCreated"),
                        rs.getDouble("GrossPrice"),
                        rs.getDouble("VAT"),
                        rs.getDouble("Discount"),
                        rs.getDouble("NetPrice"),
                        rs.getString("PaymentMethod"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return null;
    }

    public int insertNewInvoice(String customerPhoneNo, Date dateTimeCreated, double grossPrice, double vat, double discount, String paymentMethod) throws SQLException {
        int invoiceId=0;
        PreparedStatement psm = null;
        ResultSet rs = null;

        try {
            psm = connection.prepareStatement(INSERT_NEW_INVOICE + " SELECT SCOPE_IDENTITY()");
            psm.setString(1, customerPhoneNo);
            psm.setTimestamp(2, new java.sql.Timestamp(dateTimeCreated.getTime()));
            psm.setDouble(3, grossPrice);
            psm.setDouble(4, vat);
            psm.setDouble(5, discount);
            psm.setString(6, paymentMethod);
            psm.executeUpdate();
            rs = psm.getGeneratedKeys();
            if (rs.next()) {
                invoiceId = rs.getInt(1); // Retrieve the generated ID
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(psm, rs);
        }
        return invoiceId;
    }
    
}
