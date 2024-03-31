/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import DAL.DishTypesDAO;
import DAL.DishesDAO;
import Model.DishTypes;
import Model.Dishes;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name="DishManager", urlPatterns={"/admin/dishmanager"})
public class DishManager extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DishesDAO dDao = new DishesDAO();
        DishTypesDAO dtDao = new DishTypesDAO();
        List<DishTypes> listOfDishTypes;
        List<Dishes> listOfDishes;
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }
        
        try {
            listOfDishTypes = dtDao.getAllDishTypes();
        } catch(SQLException e) {
            response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
            return;
        }
        request.setAttribute("listOfDishTypes", listOfDishTypes);
        
        switch (action) {
            case "view" -> {
                String dishTypeIdStr = request.getParameter("dishTypeId");
                if (dishTypeIdStr == null) {
                    try {
                        listOfDishes = dDao.getAllDishes();
                    } catch (SQLException e) {
                        response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                        return;
                    }
                } else {
                    try {
                        int dishTypeId = Integer.parseInt(dishTypeIdStr);
                        listOfDishes = dDao.getDishesByDishTypeId(dishTypeId);
                    } catch (SQLException e) {
                        response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                        return;
                    }
                }
                request.setAttribute("ListOfDishes", listOfDishes);
                request.getRequestDispatcher("dishManager.jsp").forward(request, response);
            }
            case "delete" -> {
                String dishIdStr = request.getParameter("id");
                int dishId;
                try {
                    dishId = Integer.parseInt(dishIdStr);
                    dDao.deleteDish(dishId);
                    response.sendRedirect(request.getContextPath() + "/admin/dishmanager");
                } catch(NumberFormatException | SQLException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                    return;
                }
            }
            case "add0" -> {
                request.setAttribute("action", "add");
                request.getRequestDispatcher("addOrUpdateDish.jsp").forward(request, response);
            }
            case "update0" -> {
                String dishIdStr = request.getParameter("id");
                int dishId;
                Dishes dish;
                try {
                    dishId = Integer.parseInt(dishIdStr);
                    dish = dDao.getDishById(dishId);
                } catch (SQLException|NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                    return;
                }
                request.setAttribute("dish", dish);
                request.setAttribute("action", "update");
                request.getRequestDispatcher("addOrUpdateDish.jsp").forward(request, response);
            }
            case "add" -> {
                String dishName = request.getParameter("dishName");
                String dishTypeIdStr = request.getParameter("dishTypeId");
                String priceStr = request.getParameter("price");
                String description = request.getParameter("description");
                String imageLink = request.getParameter("imageLink");
                int dishTypeId;
                double price;

                try {
                    dishTypeId = Integer.parseInt(dishTypeIdStr);
                    price = Double.parseDouble(priceStr);
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                    return;
                }
                try {
                    dDao.insertNewDish(dishName, description, price, dishTypeId, imageLink);
                    response.sendRedirect(request.getContextPath() + "/admin/dishmanager");
                } catch(SQLException e) {
                   response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                    return; 
                }
            }
            case "update" -> {
                String dishIdStr = request.getParameter("id");
                String newDishName = request.getParameter("newDishName");
                String newDishTypeIdStr = request.getParameter("newDishTypeId");
                String newPriceStr = request.getParameter("newPrice");
                String newDescription = request.getParameter("newDescription");
                String newImageLink = request.getParameter("newImagelink");
                int dishId, newDishTypeId;
                double newPrice;
                try {
                    dishId = Integer.parseInt(dishIdStr);
                    newDishTypeId = Integer.parseInt(newDishTypeIdStr);
                    newPrice = Double.parseDouble(newPriceStr);
                } catch(NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                    return;
                }
                try {
                    dDao.updateDish(newDishName, newDescription, newPrice, newDishTypeId, dishId, newImageLink);
                    response.sendRedirect(request.getContextPath() + "/admin/dishmanager");
                } catch(SQLException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
                    return;
                }
            }
            default -> {
                response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=noAction");
                return;
            }
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
