<%-- 
    Document   : addOrUpdateEmployee
    Created on : Feb 19, 2024, 4:46:13 PM
    Author     : Admin
--%>

<%@page import="Model.Employees"%>
<%@page import="DAL.EmployeesDAO"%>
<%@page import="java.util.List"%>
<%@page import="Model.DishTypes"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add or update employee</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <style>
            .form-add {
                width: 350px;
                margin: 50px auto;
                font-size: 15px;
            }
            .form-add form {
                margin-bottom: 15px;
                background: #f7f7f7;
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                padding: 30px;
            }
            .form-add h2 {
                margin: 0 0 15px;
            }
            .form-control, .btn {
                min-height: 38px;
                border-radius: 2px;
            }
            .btn {
                font-size: 15px;
                font-weight: bolder;

            }
            .form-control{
                width: 120%;
                margin-right: 5px;
            }
            .displayCenter{
                display: flex;
                justify-content: center;
            }
        </style>
    </head>
    <body>
        <%@include file="headerMenuAdmin.jsp" %>
        <c:if test="${requestScope.action == 'add'}">
            <div class="form-add">
                <h1 class="text-center">Add new employee</h1>
                <form action="<%=request.getContextPath()%>/admin/employeemanager?action=add" method="post">
                    <div class="form-group">
                        Employee Name:<input type="text"　class="form-control" name="employeeName" required><br/>
                    </div>
                    <div class="form-group">
                        Phone Number: <input type="text"　class="form-control" name="phonenumber" step="any" required><br/>
                    </div>
                    <div class="form-group">
                        Email Address:<input type="email"　class="form-control" name="email" required><br/>
                    </div>
                    <div class="form-group">
                        <label for="position">Position:</label>
                        <select id="position" name="position">
                            <c:forEach items="${requestScope.listOfPositions}" var="i">
                                <c:if test="${i.toString() != 'ADMIN'}">
                                    <option value="${i}">${i}</option>
                                </c:if>
                            </c:forEach>
                        </select><br/>
                    </div>

                    <div class="form-group">
                        Login Password:<input type="text"　class="form-control" name="loginpassword" required><br/>
                    </div>

                    <div class="form-group">
                        <input type="submit"　class="btn displayCenter" value="Add new Employee">
                    </div>
                </form>
            </div>

        </c:if>
        <c:if test="${requestScope.action == 'update' and requestScope.employee != null}">
            <c:set var="employee" value="${requestScope.employee}"/>
            <h1 class="text-center">Update employee: ${employee.employeeName}</h1>
            <div class="form-add">
                <form action="<%=request.getContextPath()%>/admin/employeemanager?action=update&id=${employee.employeeId}" method="post">
                    <div class="form-group">
                        New Phone Number:<input type="text"　class="form-control" name="phonenumber" value="${employee.employeePhoneNumber}"required><br/>
                    </div>
                    <div class="form-group">
                        New Email Address:<input type="email"　class="form-control"  name="email" value="${employee.employeeEmail}" required><br/>
                    </div>
                    <div class="form-group">
                        <label for="position">New Position:</label>
                        <select id="position" name="position"><br>
                            <c:forEach items="${requestScope.listOfPositions}" var="i">
                                <c:if test="${i.toString() != 'ADMIN'}">
                                    <option value="${i}">${i}</option>
                                </c:if>
                            </c:forEach>
                        </select><br/>
                    </div>
                    <input type="hidden" value="${employee.employeeId}" name="id">
                    <div class="form-group">
                        <input type="submit"　class="btn displayCenter" value="Update Employee">
                    </div>
                </form>
            </div>

        </c:if>
        <!-- update password is in another part -->
    </body>
</html>
