<%-- 
    Document   : addDish
    Created on : Feb 17, 2024, 8:52:59 PM
    Author     : Admin
--%>

<%@page import="Model.Dishes"%>
<%@page import="java.util.List"%>
<%@page import="Model.DishTypes"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add or update dish</title>
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
                <h1 class="text-center">Add new dish</h1>
                <form action="<%=request.getContextPath()%>/admin/dishmanager?action=add" method="post">
                    <div class="form-group">
                        Dish Name:<input type="text"　class="form-control" name="dishName" required><br/>
                    </div>
                    <div class="form-group">
                        <label for="dishTypeId">Dish category:</label> 
                        <select id="dishTypeId" name="dishTypeId">
                            <c:forEach items="${requestScope.listOfDishTypes}" var="i">
                                <option value="${i.dishTypeId}">${i.dishTypeName}</option>
                            </c:forEach>
                        </select><br/>
                    </div>
                    <div class="form-group">
                        Price(VND):<input type="number"　class="form-control" name="price" step="any" required><br/>
                    </div>
                    <div class="form-group">
                        Description:<input type="text"　class="form-control" name="description" required><br/>
                    </div>
                    <div class="form-group">
                        Image Link: <input type="text"　class="form-control" name="imagelink" required><br/>
                    </div>
                    <div class="form-group">
                        <input type="submit"　class="btn displayCenter" value="Add new Dish">
                    </div>
                </form>
            </div>
        </c:if>
        <c:if test="${requestScope.action == 'update' and requestScope.dish != null}">
            <c:set var="dish" value="${requestScope.dish}"/>
            <div class="form-add">
                <h1 class="text-center">Update dish: ${dish.dishName}</h1>
                <form action="<%=request.getContextPath()%>/admin/dishmanager?action=update&id=${dish.dishId}" method="post">
                    <div class="form-group">
                        New Dish Name:<input type="text"　class="form-control" name="newDishName" value="${dish.dishName}" required><br/>
                    </div>
                    <div class="form-group">
                        <label for="newDishTypeId">New dish category:</label>
                        <select id="newDishTypeId" name="newDishTypeId">
                            <c:forEach items="${requestScope.listOfDishTypes}" var="i">
                                <option value="${i.dishTypeId}">${i.dishTypeName}</option>
                            </c:forEach>
                        </select><br/>
                    </div>
                    <div class="form-group">
                        New Price(VND):<input type="number"　class="form-control" name="newPrice" step="any" value="${dish.price}" required><br/>
                    </div>
                    <div class="form-group">
                        New Description:<input type="text"　class="form-control" name="newDescription" value="${dish.description}" required><br/>
                    </div>
                    <div class="form-group">
                        New Image Link: <input type="text"　class="form-control" name="newImagelink" value="${dish.imageLink}" required><br/>
                    </div>
                    <div class="form-group">
                        <input type="submit"　class="btn displayCenter" value="Update Dish">
                    </div>
                </form>
            </div>
        </c:if>
    </body>
</html>
