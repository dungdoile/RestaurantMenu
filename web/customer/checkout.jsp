<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : checkout
    Created on : Mar 7, 2024, 11:10:42 PM
    Author     : Admin
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/css/checkout.css">
        <link href="${contextPath}/resource/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/> 
        <link href='https://fonts.googleapis.com/css?family=Be Vietnam' rel='stylesheet'>
        <title>Hóa Đơn</title>
    </head>
    <body>
        <c:set var="customerPhoneNumber" value="${requestScope.customerPhoneNumber}"/>
        <c:set var="customerName" value="${requestScope.customerName}"/>
        <c:set var="tableId" value="${requestScope.tableId}"/>
        <c:set var="dateTimeFrom" value="${requestScope.dateTimeFrom}"/>
        <c:set var="listOfOrders" value="${requestScope.listOfOrders}"/>
        <c:set var="listOfDishNames" value="${requestScope.listOfDishNames}"/>
        <c:set var="listOfDishPrices" value="${requestScope.listOfDishPrices}"/>
        <c:set var="grossPrice" value="${requestScope.grossPrice}"/>
        <c:set var="vat" value="${requestScope.vat}"/>
        <c:set var="discount" value="${requestScope.discount}"/>
        <c:set var="netPrice" value="${requestScope.netPrice}"/>
        <div class="content">
            <div class="container" style="background: #F6F5F5; border-radius: 20px;">
                <div class= "row">

                    <div class ="col-sm-2 title ">
                        <h1>Checkout</h1>
                    </div>
                    <div class ="col-sm-1"></div>
                    <div class ="col-sm-6 customer">
                        <h5>Thông tin khách hàng</h5>

                        <div class="row">
                            <div class="col-6 col-sm-6">
                                Tên khách hàng:
                                <p>${customerName}</p>
                            </div>
                            <div class="col-6 col-sm-5">
                                Số điện thoại:
                                <p>${customerPhoneNumber}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <br/>

                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Tên món</th>
                            <th>Số lượng</th>
                            <th>Đơn giá</th>
                            <th>Tổng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listOfOrders}" var="i">
                            <c:set var="price" value="${listOfDishPrices.get(i.dishId)}"/>
                            <c:set var="total" value="${price * i.quantity}"/>
                            <tr>
                                <td>${listOfDishNames.get(i.dishId)}</td>
                                <td>${i.quantity}</td>
                                <td>${price}</td>
                                <td>${total}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>Thời gian: ${dateTimeFrom}</tr><br/>
                    <tr>Bàn số: ${tableId}</tr>
                    </tfoot>
                </table>

                <form action="checkout" method="post">
                    <div class= "container-fluid">

                        <div class= "row">
                            <div class ="col-2 "></div>
                            <div class ="col ">
                                <div class="btn-group paybuttom">
                                    <select  class="btn btn-primary dropdown-toggle" name="dropdownPay" id="dropdownPay" data-toggle="dropdown" required>
                                        <div class="dropdown-menu">
                                            <option class="dropdown-item" value="">Vui lòng chọn phương thức thanh toán</option>
                                            <option class="dropdown-item" value="Card">Chuyển khoản/ Quẹt thẻ</option>
                                            <option class="dropdown-item" value="Cash">Tiền mặt</option>
                                    </select>
                                </div>
                                <br/>

                                <div class="paywith" id="hide">
                                    <div id="Cash" class="data">
                                        <label for="cashcus">Cash advance:</label>
                                        <input type="number" id="cashcus" name="cashcus" value="${netPrice}" step="any"/>
                                        <p>Change: </p>
                                    </div>
                                    <div id="Card" class="data" >
                                        <img src="<%=request.getContextPath()%>/assets/images/QR.jpg">
                                    </div>
                                </div>
                            </div>
                            <div class ="col ">
                                <div class="cal">
                                    <p>Tạm tính:${grossPrice}</p>
                                    <p>VAT:${vat}%</p>
                                    <p>Chiết khấu:${discount}%</p>
                                    <h5>Phải trả:${netPrice}</h5>
                                </div>

                                <fieldset class="bill"  style="font-size: 15px;">
                                    <p>Hóa đơn<p>
                                    <div>
                                        <input type="checkbox" id="email-bill" name="email" value="email" onclick="myFunction()"/>
                                        <label for="email">Gửi qua mail</label>
                                    </div>
                                    <div class="form-group data" id="emailhide">
                                        <label for="myEmail">Vui lòng nhập email của bạn:</label>
                                        <input type="email" id = "myEmail"  placeholder="Email" name="customerEmail">
                                    </div>
                                </fieldset>
                            </div>
                        </div>

                        <div class="btn-group submitbuttom">
                            <input class="btn btn-primary" type="submit" id="complete" value="Hoàn thành">
                        </div>

                    </div>
                    <input type="hidden" name="action" value="finishtransaction">
                </form>
                <div class="btn-group back">
                    <button onclick="history.back()">Trở lại menu</button>
                </div>
            </div>
            <footer></footer>
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

            <script>
                        document.getElementById('dropdownPay').addEventListener('change', function () {
                            var selectedOption = this.value;
                            var nullop = document.getElementById("null");
                            var card = document.getElementById('Card');
                            var cash = document.getElementById('Cash');

                            if (selectedOption === 'Card') {
                                card.style.display = 'block';
                                cash.style.display = 'none';
                            } else if (selectedOption === 'Cash') {
                                cash.style.display = 'block';
                                card.style.display = 'none';
                            } else if (selectedOption === 'null') {
                                cash.style.display = 'none';
                                card.style.display = 'none';
                            }
                        });

                        function myFunction() {

                            var checkBox = document.getElementById("email-bill");

                            var text = document.getElementById("emailhide");


                            if (checkBox.checked === true) {
                                text.style.display = "block";
                            } else {
                                text.style.display = "none";
                            }
                        }
            </script>
    </body>
</html>
