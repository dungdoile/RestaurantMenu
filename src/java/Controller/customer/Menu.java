/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.customer;

import DAL.DineInOrdersDAO;
import DAL.DishTypesDAO;
import DAL.DishesDAO;
import DAL.TablesDAO;
import Model.Cart;
import Model.DineInOrders;
import Model.DishTypes;
import Model.Dishes;
import Model.Tables;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name="HomeServlet", urlPatterns={"/menu"})
public class Menu extends HttpServlet {
    
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
        DishesDAO dDao = new DishesDAO();
        DishTypesDAO dtDao = new DishTypesDAO();
        DineInOrdersDAO diDao = new DineInOrdersDAO();
        TablesDAO tDao = new TablesDAO();
        List<Dishes> listOfDishes;
        List<DishTypes> listOfDishTypes;
        Map<Integer, String> listOfDishNames;
        Map<Integer, Double> listOfDishPrices;
        String dishTypeIdStr = request.getParameter("dishTypeId");
        String verify = request.getParameter("verify");
        Map<Integer, Integer> cart = new HashMap<>();
        List<DineInOrders> orders = new ArrayList<>();
        int dishTypeId=1;
        
        try {
            listOfDishTypes = dtDao.getAllDishTypes();
            if (dishTypeIdStr != null) {
                dishTypeId = Integer.parseInt(dishTypeIdStr);
            }
            Cookie tableIdCookie = getCookie(request, "tableId");
            Tables customerTable;
            if (tableIdCookie != null && tableIdCookie.getValue() != null && !tableIdCookie.getValue().isEmpty()) {
                customerTable = tDao.getTableById(Integer.parseInt(tableIdCookie.getValue()));
                orders = diDao.getDineInOrdersBetween(customerTable.getTableId(), customerTable.getDateTimeUsed(), new Date());
            }
            Cookie cartCookie = getCookie(request, "cart");
            if (cartCookie != null && cartCookie.getValue() != null && !cartCookie.getValue().isEmpty()) {
                cart = Cart.getAllItemsFromCart(cartCookie.getValue(), dDao);
            }
            
            listOfDishes = dDao.getDishesByDishTypeId(dishTypeId);
            listOfDishNames = dDao.getAllDishNames();
            listOfDishPrices = dDao.getAllDishPrices();
            
        } catch (NumberFormatException | SQLException e) {
            response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
            return;
        }
        
        if (verify != null) {
            if (verify.equals("success")) {
                request.setAttribute("verifymessage", "Xác nhận thành công! Chúc quý khách ngon miệng!");
            }
            if (verify.equals("failed")) {
                request.setAttribute("verifymessage", "Xin lỗi! Chúng tôi không thể xác minh đơn của bạn! Vui lòng liên hệ số điện thoại 0123456789 để đặt bàn hoặc nhận hỗ trợ. Xin cảm ơn!");
            }
        }
        if(request.getAttribute("message") != null) {
            request.setAttribute("message", (String) request.getAttribute("message"));
        }
        if(request.getAttribute("keepnav")!=null) {
            request.setAttribute("keepnav", (String) request.getAttribute("keepnav"));
        }
        if(request.getAttribute("keepFoodNav")!=null) {
            request.setAttribute("keepFoodNav", (String) request.getAttribute("keepFoodNav"));
        }

        request.setAttribute("cart", cart);
        request.setAttribute("orders", orders);
        request.setAttribute("listOfDishNames", listOfDishNames);
        request.setAttribute("listOfDishPrices", listOfDishPrices);
        request.setAttribute("listOfDishTypes", listOfDishTypes);
        request.setAttribute("dishTypeId", dishTypeId);
        request.setAttribute("ListOfDishes", listOfDishes);
        request.getRequestDispatcher("customer/menu.jsp").forward(request, response);
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
}
