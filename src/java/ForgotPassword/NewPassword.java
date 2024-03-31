/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ForgotPassword;

import DAL.EmployeesDAO;
import Utils.Encryptor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation class NewPassword
 */
@WebServlet(name="NewPassword", urlPatterns="/newPassword")
public class NewPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

        @Override
	   protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String newPassword = Encryptor.SHA256Encryption(request.getParameter("password"));
        String confPassword = Encryptor.SHA256Encryption(request.getParameter("confPassword"));
        if (newPassword != null && confPassword != null && newPassword.equals(confPassword)) {

            try {
                EmployeesDAO eDao = new EmployeesDAO();
                boolean check = eDao.updateLoginPassword(newPassword, (String)session.getAttribute("email"));

                if (check) {
                    request.setAttribute("status", "resetSuccess");
                } else {
                    request.setAttribute("status", "resetFailed");
                }
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } catch (SQLException e) {
                response.sendRedirect(request.getContextPath() + "/error/error.jsp?error=" + e);
            }
        }
    }

}
