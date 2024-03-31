<%-- 
    Document   : DineInOrdersManager
    Created on : Feb 23, 2024, 10:46:00 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dine-in Orders</title>
        <style>
            table,td,th{
                border: 1px solid black;
            }
            .table,td,th{
                border: 1px solid black;
            }
            .body {
                color: #566787;
                background: #f5f5f5;
                font-family: 'Roboto', sans-serif;
            }
            .table-responsive {
                margin: 30px 0;
            }
            .table-wrapper {
                min-width: 1000px;
                background: #fff;
                padding: 20px;
                box-shadow: 0 1px 1px rgba(0,0,0,.05);
            }
            .table-title {
                padding-bottom: 10px;
                margin: 0 0 10px;
                min-width: 100%;
            }
            .table-title h2 {
                margin: 8px 0 0;
                font-size: 22px;
            }
            .search-box {
                position: relative;
                float: right;
            }
            .search-box input {
                height: 34px;
                border-radius: 20px;
                padding-left: 35px;
                border-color: #ddd;
                box-shadow: none;
            }
            .search-box input:focus {
                border-color: #3FBAE4;
            }
            .search-box i {
                color: #a0a5b1;
                position: absolute;
                font-size: 19px;
                top: 8px;
                left: 10px;
            }
            .table.table tr th, table.table tr td {
                border-color: #e9e9e9;
            }
            .table.table-striped tbody tr:nth-of-type(odd) {
                background-color: #fcfcfc;
            }
            .table.table-striped.table-hover tbody tr:hover {
                background: #f5f5f5;
            }
            .table.table th i {
                font-size: 13px;
                margin: 0 5px;
                cursor: pointer;
            }
            .table.table td:last-child {
                width: 130px;
            }
            .table.table td a {
                color: #a0a5b1;
                display: inline-block;
                margin: 0 5px;
            }
            .table.table td a.view {
                color: #03A9F4;
            }
            .table.table td a.edit {
                color: #FFC107;
            }
            .table.table td a.delete {
                color: #E34724;
            }
            .table.table td i {
                font-size: 19px;
            }
            .pagination {
                float: right;
                margin: 0 0 5px;
            }
            .pagination li a {
                border: none;
                font-size: 95%;
                width: 30px;
                height: 30px;
                color: #999;
                margin: 0 2px;
                line-height: 30px;
                border-radius: 30px !important;
                text-align: center;
                padding: 0;
            }
            .pagination li a:hover {
                color: #666;
            }
            .pagination li.active a {
                background: #03A9F4;
            }
            .pagination li.active a:hover {
                background: #0397d6;
            }
            .pagination li.disabled i {
                color: #ccc;
            }
            .pagination li i {
                font-size: 16px;
                padding-top: 6px
            }
            .hint-text {
                float: left;
                margin-top: 6px;
                font-size: 95%;
            }
            .displayCenter{
                display: flex;
                justify-content: center;
            }

        </style>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

    </head>
    <body>
        <%@include file="headerMenuEmployee.jsp" %>
        <h1>Dine-in orders manager</h1>
        <br/>
        <div class="displayCenter">
            <a href="<%=request.getContextPath()%>/employee/dineinordersmanager?action=viewPendingOrders"><button>Pending Orders</button></a>
            <a href="<%=request.getContextPath()%>/employee/dineinordersmanager?action=viewCookingOrders"><button>Cooking Orders</button></a>
            <a href="<%=request.getContextPath()%>/employee/dineinordersmanager?action=viewServingOrders"><button>Serving Orders</button></a>
            <a href="<%=request.getContextPath()%>/employee/dineinordersmanager?action=viewServedOrders"><button>Served Orders</button></a>
        </div>
        <p>${param.action}</p>
        <br/>
        <c:set var="dishNames" value="${requestScope.dishNames}"/>
        <div class="container-xl">
            <div class="table-responsive">
                <div class="table-wrapper">
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>Table ID</th>
                                <th>Dish </th>
                                <th>Quantity</th>
                                <th> Date time Ordered</th>
                                <th>Service status</th>
                                <th>Rush</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.dineInOrders}" var="i">
                                <tr>
                                    <td>${i.tableId}</td>
                                    <td>${dishNames.get(i.dishId)}</td>
                                    <td>${i.quantity}</td>
                                    <td>${i.dateTimeOrdered}</td>
                                    <td>${i.serviceStatus}</td>
                                    <td>${i.rush}</td>
                                    <c:if test="${sessionScope.user.position == 'STAFF'}">
                                        <!-- Actions for staff -->
                                        <c:if test="${i.serviceStatus == 'Serving'}">
                                            <td>
                                                <a href="<%=request.getContextPath()%>/employee/dineinordersmanager?action=served&tableId=${i.tableId}&dishId=${i.dishId}">
                                                    <button>Served</button>
                                                </a>
                                            </td>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${sessionScope.user.position == 'COOK'}">
                                        <!-- Actions for chef -->
                                        <c:if test="${i.serviceStatus == 'Pending'}">
                                            <td>
                                                <a href="<%=request.getContextPath()%>/employee/dineinordersmanager?action=confirm&tableId=${i.tableId}&dishId=${i.dishId}">
                                                    <button>Confirm</button>
                                                </a>
                                            </td>
                                        </c:if>

                                        <c:if test="${i.serviceStatus == 'Cooking'}">
                                            <td>
                                                <a href="<%=request.getContextPath()%>/employee/dineinordersmanager?action=finish&tableId=${i.tableId}&dishId=${i.dishId}">
                                                    <button>Finish</button>
                                                </a>
                                            </td>
                                        </c:if>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    </body>
                    </html>
