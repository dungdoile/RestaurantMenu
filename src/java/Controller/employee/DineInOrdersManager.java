/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.employee;

import DAL.DineInOrdersDAO;
import DAL.DishesDAO;
import DAL.TablesDAO;
import Model.DineInOrders;
import Model.Tables;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name="DineInOrdersManager", urlPatterns={"/employee/dineinordersmanager"})
public class DineInOrdersManager extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        DineInOrdersDAO diDao = new DineInOrdersDAO();
        TablesDAO tDao = new TablesDAO();
        DishesDAO dDao = new DishesDAO();
        List<Tables> tables;
        Map<Integer,String> dishNames;
        List<DineInOrders> dineInOrders;
        
        if(action == null) {
            action = "viewServingOrders";
        }
        
        try {
            tables = tDao.getAllTables();
            dishNames = dDao.getAllDishNames();
            request.setAttribute("tables", tables);
            request.setAttribute("dishNames", dishNames);
            switch(action) {
                default -> {
                    dineInOrders = diDao.getAllDineInOrders();
                    request.setAttribute("dineInOrders", dineInOrders);
                    request.getRequestDispatcher("dineInOrdersManager.jsp").forward(request, response);
                }
                case "viewPendingOrders" -> {
                    dineInOrders = diDao.getDineInOrdersByServiceStatus("Pending");
                    request.setAttribute("dineInOrders", dineInOrders);
                    request.getRequestDispatcher("dineInOrdersManager.jsp").forward(request, response);
                }
                case "viewCookingOrders" -> {
                    dineInOrders = diDao.getDineInOrdersByServiceStatus("Cooking");
                    request.setAttribute("dineInOrders", dineInOrders);
                    request.getRequestDispatcher("dineInOrdersManager.jsp").forward(request, response);
                }
                case "viewServingOrders" -> {
                    dineInOrders = diDao.getDineInOrdersByServiceStatus("Serving");
                    request.setAttribute("dineInOrders", dineInOrders);
                    request.getRequestDispatcher("dineInOrdersManager.jsp").forward(request, response);
                }
                case "viewServedOrders" -> {
                    dineInOrders = diDao.getDineInOrdersByServiceStatus("Served");
                    request.setAttribute("dineInOrders", dineInOrders);
                    request.getRequestDispatcher("dineInOrdersManager.jsp").forward(request, response);
                }
                case "confirm" -> {
                    int tableId = Integer.parseInt(request.getParameter("tableId"));
                    int dishId = Integer.parseInt(request.getParameter("dishId"));
                    diDao.updateOrderServiceStatus("Cooking", tableId, dishId);
                    response.sendRedirect(request.getContextPath() + "/employee/dineinordersmanager?action=viewPendingOrders");
                }
                case "finish" -> {
                    int tableId = Integer.parseInt(request.getParameter("tableId"));
                    int dishId = Integer.parseInt(request.getParameter("dishId"));
                    diDao.updateOrderServiceStatus("Serving", tableId, dishId);
                    response.sendRedirect(request.getContextPath() + "/employee/dineinordersmanager?action=viewCookingOrders");
                }
                case "served" -> {
                    int tableId = Integer.parseInt(request.getParameter("tableId"));
                    int dishId = Integer.parseInt(request.getParameter("dishId"));
                    diDao.updateOrderServiceStatus("Served", tableId, dishId);
                    response.sendRedirect(request.getContextPath() + "/employee/dineinordersmanager");
                }
            }
        } catch (NumberFormatException |  SQLException e) {
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
