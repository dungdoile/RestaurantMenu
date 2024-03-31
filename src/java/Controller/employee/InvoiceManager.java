/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.employee;

import DAL.DishesDAO;
import DAL.InvoiceDetailsDAO;
import DAL.InvoicesDAO;
import Model.InvoiceDetails;
import Model.Invoices;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EmployeeInvoiceManager", urlPatterns = {"/employee/invoicemanager"})
public class InvoiceManager extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InvoicesDAO iDao = new InvoicesDAO();
        DishesDAO dDao = new DishesDAO();

        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        List<Invoices> listOfInvoices;
        try {
            switch (action) {
                case "view" -> {
                    String fromDateStr = request.getParameter("datefrom");
                    String toDateStr = request.getParameter("dateto");
                    if (fromDateStr == null && toDateStr == null) {
                        listOfInvoices = iDao.getAllInvoices();
                        request.setAttribute("listOfInvoices", listOfInvoices);
                        request.getRequestDispatcher("invoiceManager.jsp").forward(request, response);
                    } else if (fromDateStr != null && toDateStr != null) {
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date fromDate = format.parse(fromDateStr);
                            Date toDate = format.parse(toDateStr);
                            listOfInvoices = iDao.getInvoicesBetween(fromDate, toDate);
                            request.setAttribute("listOfInvoices", listOfInvoices);
                            request.getRequestDispatcher("invoiceManager.jsp").forward(request, response);
                        } catch (ParseException | SQLException e) {
                            response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                            return;
                        }
                    }
                }
                case "viewdetails" -> {
                    InvoiceDetailsDAO idDao = new InvoiceDetailsDAO();
                    String invoiceIdStr = request.getParameter("id");
                    int invoiceId;
                    List<InvoiceDetails> listOfInvoiceDetails;
                    Map<Integer, String> listOfDishNames;
                    Invoices invoice;
                    invoiceId = Integer.parseInt(invoiceIdStr);
                    invoice = iDao.getInvoiceById(invoiceId);
                    listOfInvoiceDetails = idDao.getInvoiceDetailsByInvoiceId(invoiceId);
                    listOfDishNames = dDao.getAllDishNames();
                    request.setAttribute("listOfDishNames", listOfDishNames);
                    request.setAttribute("invoice", invoice);
                    request.setAttribute("invoiceDetails", listOfInvoiceDetails);
                    request.getRequestDispatcher("viewInvoiceDetails.jsp").forward(request, response);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/error/error.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
