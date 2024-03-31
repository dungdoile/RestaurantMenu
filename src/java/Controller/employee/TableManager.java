/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.employee;

import DAL.CustomersDAO;
import DAL.DineInOrdersDAO;
import DAL.DishesDAO;
import DAL.InvoiceDetailsDAO;
import DAL.InvoicesDAO;
import DAL.TablesDAO;
import Model.InvoiceDetails;
import Model.Invoices;
import Model.Tables;
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
@WebServlet(name="TableManager", urlPatterns={"/employee/tablemanager"})
public class TableManager extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        TablesDAO tDao = new TablesDAO();
        DishesDAO dDao = new DishesDAO();
        CustomersDAO cDao = new CustomersDAO();
        InvoicesDAO iDao = new InvoicesDAO();
        InvoiceDetailsDAO idDao = new InvoiceDetailsDAO();
        String action = request.getParameter("action");
        DineInOrdersDAO diDao = new DineInOrdersDAO();
        List<Tables> listOfTables;
        if(action == null) {
            action = "view";
        }
        
        try {
            switch(action) {
                case "view" -> {
                    String viewInvoiceRequests = request.getParameter("viewinvoicerequests");
                    String message = request.getParameter("message");
                    if(message!=null) {
                        if(message.equals("success")) {
                            request.setAttribute("message", "Xếp bàn thành công!");
                        }
                        if(message.equals("confirmed")) {
                            request.setAttribute("message", "Yêu cầu hóa đơn đã được xác nhận!");
                        }
                    }
                    if(viewInvoiceRequests!=null && viewInvoiceRequests.equals("true")) {
                        listOfTables = tDao.getInvoiceRequests();
                        request.setAttribute("viewinvoicerequests", viewInvoiceRequests);
                    } else {
                        listOfTables = tDao.getAllTables();
                        request.setAttribute("numsOfAvailableTables", tDao.countAvailableTables());
                    }
                    request.setAttribute("listOfTables", listOfTables);
                    request.getRequestDispatcher("tableManager.jsp").forward(request, response);
                }
                case "getinfo" -> {
                    listOfTables = tDao.getAvailableTables();
                    request.setAttribute("listOfTables", listOfTables);
                    request.getRequestDispatcher("arrangeTable.jsp").forward(request, response);
                }
                case "arrangetable" -> {
                    //set customer phone no, date used, tableId
                    int tableId = Integer.parseInt(request.getParameter("selectedTable"));
                    String customerPhoneNumber = request.getParameter("customerPhoneNumber");
                    String customerName = request.getParameter("customerName");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    
                    String dateTimeUsedStr;
                    Date dateTimeUsed;
                    String timeVisit = request.getParameter("timeVisit");
                    if(timeVisit.equals("now")) {
                        dateTimeUsed = new Date();
                    } else {
                        dateTimeUsedStr = request.getParameter("dateVisit") + " " + request.getParameter("timeVisit") + ":00";
                        dateTimeUsed = sdf.parse(dateTimeUsedStr);
                    }
                    if(dateTimeUsed.before(new Date())) {
                        request.setAttribute("customerPhoneNumber", customerPhoneNumber);
                        request.setAttribute("customerName", customerName);
                        request.setAttribute("message", "Ngày không hợp lệ, phải là ngày hôm nay hoặc ngày sau hôm nay");
                        request.getRequestDispatcher("arrangeTable.jsp").forward(request, response);
                        return;
                    }
                    cDao.insertNewCustomer(customerPhoneNumber, customerName);
                    tDao.updateTableCustomer(customerPhoneNumber, dateTimeUsed, tableId);
                    response.sendRedirect("tablemanager?message=success");
                }
                case "viewinvoicerequest" -> {
                    int tableId = Integer.parseInt(request.getParameter("tableId"));
                    Tables table = tDao.getTableById(tableId);
                    Invoices invoice = iDao.getInvoiceByCustomerAndDate(table.getCustomerPhoneNumber(), table.getDateTimeUsed(), new Date());
                //fix this, change to hashmap
                    List<InvoiceDetails> invoiceDetail = idDao.getInvoiceDetailsByInvoiceId(invoice.getInvoiceId());
                    Map<Integer, String> listOfDishNames = dDao.getAllDishNames();
                    request.setAttribute("invoice", invoice);
                    request.setAttribute("invoiceDetails", invoiceDetail);
                    request.setAttribute("listOfDishNames", listOfDishNames);
                    request.setAttribute("tableId", tableId);
                    request.setAttribute("action", "confirminvoicerequest");
                    request.getRequestDispatcher("viewInvoiceDetails.jsp").forward(request, response);
                }
                case "confirminvoicerequest" -> {
                    int tableId = Integer.parseInt(request.getParameter("tableId"));
                    diDao.deletedDineInOrder(tableId);
                    tDao.updateInvoiceRequest(tableId, false);
                    tDao.updateTableCustomer(null, null, tableId);
                    response.sendRedirect("tablemanager?message=confirmed");
                }
            }
        } catch(SQLException | NumberFormatException | ParseException e) {
            response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
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
