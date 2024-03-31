/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.InvoiceDetails;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class InvoiceDetailsDAO extends DBContext {
    private static final String GET_ALL_INVOICE_DETAILS = "SELECT InvoiceDetailID, InvoiceID, DishID, Quantity FROM [InvoiceDetails]";
    private static final String GET_INVOICE_DETAIL_BY_ID = "SELECT InvoiceDetailID, InvoiceID, DishID, Quantity FROM [InvoiceDetails] WHERE InvoiceDetailID=?";
    private static final String GET_INVOICE_DETAILS_BY_INVOICE_ID = "SELECT InvoiceDetailID, InvoiceID, DishID, Quantity FROM [InvoiceDetails] WHERE InvoiceID=?";
    private static final String INSERT_NEW_INVOICE_DETAIL = "INSERT INTO [InvoiceDetails] (InvoiceID, DishID, Quantity) VALUES (?, ?, ?)";

    public List<InvoiceDetails> getAllInvoiceDetails() throws SQLException {
        List<InvoiceDetails> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_ALL_INVOICE_DETAILS);
            rs = st.executeQuery();

            while (rs.next()) {
                InvoiceDetails rank = new InvoiceDetails(rs.getInt("InvoiceDetailID"),
                        rs.getInt("InvoiceID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"));
                list.add(rank);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }

    public InvoiceDetails getInvoiceDetailById(int id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement(GET_INVOICE_DETAIL_BY_ID);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs != null && rs.next()) {
                return new InvoiceDetails(rs.getInt("InvoiceDetailID"),
                        rs.getInt("InvoiceID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return null;
    }
    
    public List<InvoiceDetails> getInvoiceDetailsByInvoiceId(int invoiceId) throws SQLException {
        List<InvoiceDetails> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement(GET_INVOICE_DETAILS_BY_INVOICE_ID);
            st.setInt(1, invoiceId);
            rs = st.executeQuery();

            while (rs.next()) {
                InvoiceDetails rank = new InvoiceDetails(rs.getInt("InvoiceDetailID"),
                        rs.getInt("InvoiceID"),
                        rs.getInt("DishID"),
                        rs.getInt("Quantity"));
                list.add(rank);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            closeResources(st, rs);
        }
        return list;
    }

    public boolean insertNewInvoiceDetail(int invoiceId, int dishId, int quantity) throws SQLException {
        return insertUpdateDelete(INSERT_NEW_INVOICE_DETAIL, invoiceId, dishId, quantity);
    }
    
}

