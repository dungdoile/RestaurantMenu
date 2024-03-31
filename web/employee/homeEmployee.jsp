<%-- 
    Document   : homeEmployee
    Created on : Mar 5, 2024, 11:36:09 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="Model.Employees"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Home</title>
    </head>
    <body>
        <%@include file="headerMenuEmployee.jsp" %>
        <c:set var="employee" value="${sessionScope.user}"/>
        <h1>Hello ${employee.employeeName}!</h1>
        <p>Phone number: ${employee.employeePhoneNumber}</p>
        <p>Email: ${employee.employeeEmail}</p>
        <p>Position: ${employee.position}</p>
    </body>
</html>
