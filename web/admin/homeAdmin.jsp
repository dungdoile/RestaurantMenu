<%-- 
    Document   : homeAdmin
    Created on : Mar 5, 2024, 11:28:56 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Home</title>
    </head>
    <body>
        <%@include file="headerMenuAdmin.jsp" %>
        <h1>Admin</h1>
        <p>Name: ${sessionScope.user.employeeName}</p>
        <p>Phone No: ${sessionScope.user.employeePhoneNumber}</p>
        <p>Email: ${sessionScope.user.employeeEmail}</p>
    </body>
</html>
