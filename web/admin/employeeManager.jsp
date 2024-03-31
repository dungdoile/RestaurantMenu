<%-- 
    Document   : employeeManager
    Created on : Feb 19, 2024, 9:24:13 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Manager</title>


        <script>
            $(document).ready(function () {
                $('[data-toggle="tooltip"]').tooltip();
            });
        </script>
        <style>
            .table,td,th{
                border: 1px solid black;
            }
            .body {
                color: #566787;
                background: #f5f5f5;
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
                position: absolute;
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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

    </head>
    <body>
        <%@include file="headerMenuAdmin.jsp" %>
        <br/>
        <div class="displayCenter">
            <a href="<%=request.getContextPath()%>/admin/employeemanager"><button>Show all</button></a>

            <c:forEach items="${requestScope.listOfPositions}" var="i">
                <a href="<%=request.getContextPath()%>/admin/employeemanager?position=${i}"><button>${i}</button></a>
                    </c:forEach>
        </div>


        <div class="container-xl">
            <div class="table-responsive">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-8"><h2>Employees's Details</h2></div>
                            <div class="col-sm-4">
                                <div class="search-box">
                                    <a href="<%=request.getContextPath()%>/admin/employeemanager?action=add0"><button>Add new employee</button></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name </th>
                                <th>Phone Number</th>
                                <th> Email</th>
                                <th>Position</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.ListOfEmployees}" var="i">
                                <c:if test="${i.position == 'ADMIN'}">
                                    <tr>
                                        <td>${i.employeeId}</td>
                                        <td>${i.employeeName}</td>
                                        <td>${i.employeePhoneNumber}</td>
                                        <td>${i.employeeEmail}</td>
                                        <td>${i.position}</td>
                                        <td>Cannot take action</td>
                                    </tr>
                                </c:if>
                                <c:if test="${i.position != 'ADMIN'}">
                                    <tr>
                                        <td>${i.employeeId}</td>
                                        <td>${i.employeeName}</td>
                                        <td>${i.employeePhoneNumber}</td>
                                        <td>${i.employeeEmail}</td>
                                        <td>${i.position}</td>
                                        <td><a href="<%=request.getContextPath()%>/admin/employeemanager?action=update0&id=${i.employeeId}"class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>
                                            <button onclick="deleteEmployee(${i.employeeId})"class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>    
                        </tbody>
                    </table>
                </div>  
            </div> 
            <script>
                function deleteEmployee(id) {
                    if (window.confirm("Are you sure you want to delete dish with id: " + id + "?")) {
                        window.location.href = window.location.href + "?action=delete&id=" + id;
                    }
                }
            </script>
    </body>
</html>
