/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.customer;

import DAL.DineInOrdersDAO;
import DAL.DishesDAO;
import DAL.TablesDAO;
import Model.Cart;
import Model.DineInOrders;
import Utils.Calculator;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
@WebServlet(name = "Cart", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private final int MAX_AGE = 60*60*24; //set new maxAge value

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
        response.setContentType("text/html;charset=UTF-8");
        DineInOrdersDAO diDao = new DineInOrdersDAO();
        DishesDAO dDao = new DishesDAO();
        TablesDAO tDao = new TablesDAO();
        String action = request.getParameter("action");
        String dishIdStr = request.getParameter("dishId");
        List<DineInOrders> orders;
        Map<Integer, Integer> cart = new HashMap<>();
        Map<Integer, Double> listOfDishPrices;
        Cookie tableIdCookie;
        Cookie cartCookie = getCookie(request, "cart");
        Cookie totalPriceCookie = getCookie(request, "totalPrice");
        int dishId=0;
        int tableId=0;
        
        if(action == null) {
            action="tomenu";
        }
        
        try {
            tableIdCookie = getCookie(request, "tableId");
            if(tableIdCookie!=null) {
                tableId = Integer.parseInt(tableIdCookie.getValue());
            }
            String dishTypeIdStr = request.getParameter("dishTypeId");
            listOfDishPrices = dDao.getAllDishPrices();
            if(cartCookie!=null) {
                cart = Cart.getAllItemsFromCart(cartCookie.getValue(), dDao);
            }
            orders = diDao.getDineInOrdersByTableId(tableId);
            if (dishIdStr != null) {
                dishId = Integer.parseInt(dishIdStr);
            }
            
            switch (action) {
                case "tomenu" -> {
                    //request.getRequestDispatcher("menu").forward(request, response);
                    String keepNav = request.getParameter("keepnav");
                    String keepFoodNav = request.getParameter("keepfoodnav");
                    if(keepNav!=null && keepNav.equals("true")) {
                        request.setAttribute("keepnav", "true");
                    }
                    if(keepFoodNav!=null && keepFoodNav.contains("true")) {
                        request.setAttribute("keepfoodnav", "true");
                    }
                    request.setAttribute("cart", cart);
                    request.setAttribute("orders", orders);
                    request.getRequestDispatcher("menu").forward(request, response);
                }
                case "add" -> {
                    cart.put(dishId, cart.getOrDefault(dishId, 0) + 1);
                    cartCookie = new Cookie("cart", Cart.cartToString(cart));
                    cartCookie.setMaxAge(MAX_AGE);
                    response.addCookie(cartCookie);
                    
                    //request.getRequestDispatcher("menu").forward(request, response);
                    totalPriceCookie = new Cookie("totalPrice", Calculator.calculateTotalPrice(cart, listOfDishPrices) + "");
                    totalPriceCookie.setMaxAge(MAX_AGE);
                    response.addCookie(totalPriceCookie);
                    response.sendRedirect("cart?keepnav=true&dishTypeId=" + dishTypeIdStr);
                }
                case "subtract" -> {
                    int quantity = cart.get(dishId);
                    if (quantity > 1) {
                        cart.put(dishId, quantity - 1);
                    } else {
                        cart.remove(dishId);
                    }
                    cartCookie = new Cookie("cart", Cart.cartToString(cart));
                    cartCookie.setMaxAge(MAX_AGE);
                    response.addCookie(cartCookie);
                    
                    //request.getRequestDispatcher("menu").forward(request, response);
                    totalPriceCookie = new Cookie("totalPrice", Calculator.calculateTotalPrice(cart, listOfDishPrices) + "");
                    totalPriceCookie.setMaxAge(MAX_AGE);
                    response.addCookie(totalPriceCookie);
                    response.sendRedirect("cart?keepnav=true&dishTypeId=" + dishTypeIdStr);
                }
                case "remove" -> {
                    if (!cart.isEmpty()) {
                        cart.remove(dishId);
                        cartCookie = new Cookie("cart", Cart.cartToString(cart));
                        cartCookie.setMaxAge(MAX_AGE);
                        response.addCookie(cartCookie);
                        
                        //request.getRequestDispatcher("menu").forward(request, response);
                        totalPriceCookie = new Cookie("totalPrice", Calculator.calculateTotalPrice(cart, listOfDishPrices) + "");
                        totalPriceCookie.setMaxAge(MAX_AGE);
                        response.addCookie(totalPriceCookie);
                        response.sendRedirect("cart?keepnav=true&dishTypeId=" + dishTypeIdStr);
                    }
                }
                case "order" -> {
                    //get tableId from cookie
                    boolean checkValidTable = tDao.getTableById(tableId) != null;
                    if (cartCookie != null && cart != null && checkValidTable) {
                        Date dateTimeOrdered = new Date();
                        for (int order : cart.keySet()) {
                            diDao.insertNewDineInOrder(tableId, order, cart.get(order), dateTimeOrdered);
                        }
                        cartCookie.setMaxAge(0); //delete cart cookie
                        totalPriceCookie.setMaxAge(0); //delete totalPrice cookie
                        response.addCookie(cartCookie);
                        response.addCookie(totalPriceCookie);
                        response.sendRedirect("cart?dishTypeId=" + dishTypeIdStr);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=No orders or invalid table");
                        return;
                    }
                }
                case "checkPhoneNo" -> {
                    String phoneNo = request.getParameter("phoneNo");
                    tableId = tDao.getTableIdByCustomer(phoneNo, new Date());
                    if (tableId > 0) {
                        tableIdCookie = new Cookie("tableId", tableId + "");
                        tableIdCookie.setMaxAge(MAX_AGE);
                        response.addCookie(tableIdCookie);
                        response.sendRedirect("menu?verify=success");
                    } else {
                        response.sendRedirect("menu?verify=failed");
                    }
                }
                case "rush" -> {
                    dishId = Integer.parseInt(request.getParameter("dishId"));
                    boolean rushed = diDao.rush(tableId, dishId);
                    response.sendRedirect("menu?dishTypeId=" + dishTypeIdStr + "&keepfoodnav=true&rushed=" + rushed);
                }
                default -> {
                    response.sendRedirect("menu");
                }
            }
        } catch (SQLException e) {
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
