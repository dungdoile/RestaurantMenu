<%-- 
    Document   : arrangeTable
    Created on : Mar 7, 2024, 7:56:53 PM
    Author     : Admin
--%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Arrange Table</title>
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
                width: 100%;
                margin-right: 5px;
            }
            .displayCenter{
                display: flex;
                justify-content: center;
            }
        </style>
    </head>
    <body>
        <c:if test="${requestScope.message!=null}">
            <script>
                window.onload = function(){
                    alert(${requestScope.message});
                };
            </script>
        </c:if>
        <%@include file="headerMenuEmployee.jsp" %>
        <%
            int currentHour = new Date().getHours();
            int hourLimit = 23;
            if (currentHour <= hourLimit) {
        %>
        <c:set var="availableTables" value="${requestScope.listOfTables}"/>
        <div class="form-add">
            <h1 class="text-center">Arrange Table</h1>

            <form action="tablemanager" method="post">
                <div class="form-group">
                    Customer Name: <input type="text" name="customerName" class="form-control" value="${requestScope.customerName}" required/><br/>
                </div>
                <div class="form-group">
                    Customer Phone Number: <input type="text" name="customerPhoneNumber" class="form-control" minlength="10" maxlength="15" value="${requestScope.customerPhoneNumber}" required/><br/>
                </div>
                <div class="form-group">
                    Available Tables:
                    <select id="selectedTable" name="selectedTable" required>
                        <c:forEach items="${availableTables}" var="i">
                            <option  value="${i.tableId}">Table:${i.tableId} - Capacity:${i.tableCapacity}</option>
                        </c:forEach>
                    </select><br/>
                </div>
                <!--
                <div class="form-group">
                    Date Visit: <input type="date" name="dateVisit" required/><br/>
                </div>
                -->
                <div class="form-group">
                    Time Visit:
                    <select name="timeVisit" required>
                        <option value="">Choose a time</option>
                        <!-- Generate options incrementing by 15 minutes -->
                        <option value="now">Now</option>
                    </select><br/>
                </div>
                <input  type="hidden" name="action" value="arrangetable">
                <div class="form-group btn displayCenter">
                    <input value="Arrange Table" type="submit">
                </div>
            </form>
        
        <%
            } else {
        %>
        <h1>Sorry for this inconvenience, but now is not time for us to receive orders, please come back between 9h and 19h</h1>
        <%
            }
        %>
    </div>
</body>
</html>
