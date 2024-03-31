/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import DAL.EmployeesDAO;
import Model.Employees;
import Model.Positions;
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
@WebServlet(name="EmployeeManager", urlPatterns={"/admin/employeemanager"})
public class EmployeeManager extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        EmployeesDAO eDao = new EmployeesDAO();
        List<Employees> listOfEmployees;
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }
        
        request.setAttribute("listOfPositions", Positions.values());
        
        switch (action) {
            case "view" -> {
                String position = request.getParameter("position");
                if(position == null) {
                    try {
                        listOfEmployees = eDao.getAllEmployees();
                    } catch(SQLException e) {
                        response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                        return;
                    }
                }
                else {
                    try {
                        listOfEmployees = eDao.getEmployeesByPosition(position);
                    } catch(SQLException e) {
                        response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                        return;
                    }
                }
                request.setAttribute("ListOfEmployees", listOfEmployees);
                request.getRequestDispatcher("employeeManager.jsp").forward(request, response);
            }
            case "delete" -> {
                String employeeIdStr = request.getParameter("id");
                int employeeId;
                try {
                    employeeId = Integer.parseInt(employeeIdStr);
                } catch(NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                    return;
                }
                try {
                    eDao.deleteEmployee(employeeId);
                    response.sendRedirect(request.getContextPath() + "/admin/employeemanager");
                } catch(SQLException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                    return;
                }
            }
            case "add0" -> {
                request.setAttribute("action", "add");
                request.getRequestDispatcher("addOrUpdateEmployee.jsp").forward(request, response);
            }
            case "update0" -> {
                String employeeIdStr = request.getParameter("id");
                int employeeId;
                Employees employee;
                try {
                    employeeId = Integer.parseInt(employeeIdStr);
                    employee = eDao.getEmployeeById(employeeId);
                } catch (SQLException|NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                    return;
                }
                request.setAttribute("employee", employee);
                request.setAttribute("action", "update");
                request.getRequestDispatcher("addOrUpdateEmployee.jsp").forward(request, response);
            }
            case "add" -> {
                String employeeName = request.getParameter("employeeName");
                String employeePhoneNumber = request.getParameter("phonenumber");
                String employeeEmail = request.getParameter("email");
                String position = request.getParameter("position");
                String loginPassword = Utils.Encryptor.SHA256Encryption(request.getParameter("loginpassword"));

                try {
                    eDao.insertNewEmployee(employeeName, employeePhoneNumber, employeeEmail, position, loginPassword);
                    response.sendRedirect(request.getContextPath() + "/admin/employeemanager");
                } catch(SQLException e) {
                   response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                    return; 
                }
            }
            case "update" -> {
                String employeeIdStr = request.getParameter("id");
                String newEmployeePhoneNumber = request.getParameter("phonenumber");
                String newEmployeeEmail = request.getParameter("email");
                String newPosition = request.getParameter("position");
                int employeeId;
                try {
                    employeeId = Integer.parseInt(employeeIdStr);
                } catch(NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                    return;
                }
                try {
                    eDao.updateEmployee(newEmployeePhoneNumber, newEmployeeEmail, newPosition, employeeId);
                    response.sendRedirect(request.getContextPath() + "/admin/employeemanager");
                } catch(SQLException e) {
                    response.sendRedirect(request.getContextPath() + "/error/error.jsp");
                    return;
                }
            }
            default -> {
                response.sendRedirect(request.getContextPath() + "/error/error.jsp");
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
