<%-- 
    Document   : viewInvoiceDetails
    Created on : Feb 21, 2024, 1:50:03 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Invoice Details</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
        <style>
            table,td,th{
                border: 1px solid black;
            }
            .form-add {
                width: 350px;
                margin: 50px auto;
                font-size: 15px;
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
            p{
                font-size: 15px;

            }
        </style>
    </head>
    <body>
        <%@include file="headerMenuAdmin.jsp" %>
        <c:set var="invoice" value="${requestScope.invoice}"/>
        <c:set var="listOfDishNames" value="${requestScope.listOfDishNames}"/>
        <div class="form-add">
            <button onclick="history.back()">Back</button><br>
            <h3>Details for invoice: ${invoice.invoiceId}</h3>
            <p>Date: ${invoice.dateTimeCreated}</p>
            <p>Customer Phone No: ${invoice.customerPhoneNumber}</p>
            <table>
                <tr>
                    <th>Dish</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
                <c:forEach items="${requestScope.invoiceDetails}" var="i">
                    <tr>
                        <td>${listOfDishNames.get(i.dishId)}</td>
                        <td>${i.quantity}</td>
                    </tr>
                </c:forEach>
            </table>
            <p>Gross Price: ${invoice.grossPrice}</p>
            <p>VAT: ${invoice.vat}%</p>
            <p>Discount: ${invoice.discount}%</p>
            <p>Net Price: ${invoice.netPrice}</p>
            <p>Payment method: ${invoice.paymentMethod}</p>
        </div>
    </body>

</html>
