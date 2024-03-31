/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.customer;

import DAL.CustomersDAO;
import DAL.DineInOrdersDAO;
import DAL.DishesDAO;
import DAL.InvoiceDetailsDAO;
import DAL.InvoicesDAO;
import DAL.TablesDAO;
import Model.DineInOrders;
import Model.Tables;
import Utils.Calculator;
import Utils.Email;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name = "Checkout", urlPatterns = {"/checkout"})
public class Checkout extends HttpServlet {

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
        InvoiceDetailsDAO idDao = new InvoiceDetailsDAO();
        DishesDAO dDao = new DishesDAO();
        TablesDAO tDao = new TablesDAO();
        CustomersDAO cDao = new CustomersDAO();
        DineInOrdersDAO diDao = new DineInOrdersDAO();
        List<DineInOrders> listOfOrders;
        Map<Integer, String> listOfDishNames;
        Map<Integer, Double> listOfDishPrices;
        String action = request.getParameter("action");
        int tableId=0;
        
        if (action == null) {
            action = "tocheckout";
        }

        try {
            //get data
            Cookie tableIdCookie = getCookie(request, "tableId");
            if(tableIdCookie==null) {
                response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=tableIdIsNull");
            } else {
                tableIdCookie.setMaxAge(60*60*24);
                tableId = Integer.parseInt(tableIdCookie.getValue());
            }
            Tables table = tDao.getTableById(tableId);
            String customerPhoneNumber = table.getCustomerPhoneNumber();
            String customerName = cDao.getCustomerByPhoneNo(customerPhoneNumber).getCustomerName();
            Date dateTimeFrom = table.getDateTimeUsed();
            Date dateTimeCreated = new Date();
            listOfOrders = diDao.getDineInOrdersBetween(tableId, dateTimeFrom, dateTimeCreated);
            listOfDishNames = dDao.getAllDishNames();
            listOfDishPrices = dDao.getAllDishPrices();
            double grossPrice = Calculator.calculateTotalPrice(listOfOrders, dDao.getAllDishPrices());
            double vat = 0, discount = 0;
            double netPrice = grossPrice + (grossPrice*vat/100) - (grossPrice*discount/100);

            switch (action) {
                case "tocheckout" -> {
                    for(DineInOrders orders : listOfOrders) {
                        if(!orders.getServiceStatus().equals("Served")) {
                            request.setAttribute("message", "Quý khách vui lòng thanh toán khi toàn bộ các món đã đặt được phục vụ!");
                            request.getRequestDispatcher("menu").forward(request, response);
                            return;
                        }
                    }
                    request.setAttribute("customerPhoneNumber", customerPhoneNumber);
                    request.setAttribute("customerName", customerName);
                    request.setAttribute("tableId", tableId);
                    request.setAttribute("dateTimeFrom", dateTimeFrom);
                    request.setAttribute("listOfOrders", listOfOrders);
                    request.setAttribute("listOfDishNames", listOfDishNames);
                    request.setAttribute("listOfDishPrices", listOfDishPrices);
                    request.setAttribute("grossPrice", grossPrice);
                    request.setAttribute("vat", vat);
                    request.setAttribute("discount", discount);
                    request.setAttribute("netPrice", netPrice);
                    request.getRequestDispatcher("customer/checkout.jsp").forward(request, response);
                    return;
                }
                case "finishtransaction" -> {
                    //get information
                    String paymentMethod = request.getParameter("dropdownPay");
                    String customerEmail = request.getParameter("customerEmail");
                    
                    //create invoice
                    dateTimeCreated = new Date();
                    int invoiceId = iDao.insertNewInvoice(customerPhoneNumber, dateTimeCreated, grossPrice, 0, 0, paymentMethod);
                    for (DineInOrders order : listOfOrders) {
                        idDao.insertNewInvoiceDetail(invoiceId, order.getDishId(), order.getQuantity());
                    }
                    
                    //send email
                    if(customerEmail!=null && !customerEmail.isEmpty()) {
                        StringBuilder orderList = new StringBuilder();
                        orderList.append("Tên món\tSố lượng\tGiá\tTổng");
                        for(DineInOrders order : listOfOrders) {
                            double price = listOfDishPrices.get(order.getDishId());
                            double total = price * order.getQuantity();
                            
                            orderList.append(listOfDishNames.get(order.getDishId())).append("\t")
                                    .append(order.getQuantity()).append("\t")
                                    .append(price).append("\t")
                                    .append(total).append("\n");
                        }
                        Email.sendEmail(request, customerEmail, "Hóa đơn",
                                "Ngày xuất hóa đơn: " + dateTimeCreated,
                                "Tên khách hàng: " + customerName,
                                "Số điện thoại khách hàng: " + customerPhoneNumber,
                                orderList.toString(),
                                "Tổng: " + grossPrice,
                                "VAT: " + vat,
                                "Chiết khấu: " + discount,
                                "Phải trả: " + netPrice);
                    }
                    
                    tDao.updateInvoiceRequest(tableId, true);
                    if (tableIdCookie != null) {
                        tableIdCookie.setMaxAge(0);
                        response.addCookie(tableIdCookie);
                    }
                    //redirect to main page
                    response.sendRedirect("home?transaction=success");
                }
            }

        } catch (SQLException | NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
        }

    }

    private Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(cookieName)) {
                    return c;
                }
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
