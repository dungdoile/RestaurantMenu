<%-- 
    Document   : menu
    Created on : Mar 5, 2024, 11:34:20 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="assets/css/mainmenustyle.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/> 
        <link href='https://fonts.googleapis.com/css?family=Be Vietnam' rel='stylesheet'>
        <title>Menu</title>
    </head>
    <body>
        <c:set var="cart" value="${requestScope.cart}"/>
        <c:set var="listOfDishNames" value="${requestScope.listOfDishNames}"/>
        <c:set var="listOfDishPrices" value="${requestScope.listOfDishPrices}"/>
        <c:set var="orders" value="${requestScope.orders}"/>
        
        <c:if test="${requestScope.verifymessage!=null}">
            <script>
                window.onload = function() {
                    alert('${requestScope.verifymessage}');
                };
            </script>
        </c:if>
        <c:if test="${requestScope.message!=null}">
            <script>
                window.onload = function() {
                    alert('${requestScope.message}');
                };
            </script>
        </c:if>
        <!-- header -->
        <nav class="navbar navbar-expand-sm navbar-dark head ">
            <!-- Left -->
            <ul class="navbar-nav mr-auto">
                <nav class="navbar navbar-expand-sm  navbar-dark">
                    <a class="navbar-brand" href="home">
                        <img src="assets/images/logo.jpg" 
                             style="width: 50px;
                             border-radius: 50%;">
                    </a>
                </nav>
            </ul>
            <nav class="navbar navbar-expand-sm  navbar-dark">
                <!-- Button -->
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- Navbar links -->
                <div class="collapse navbar-collapse" id="collapsibleNavbar">
                    <nav class="navbar navbar-expand-sm navbar-light centermenu">
                        <!-- Center -->
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <ul class="navbar-nav centermenuitem">
                                    <li><i class="fa-regular fa-bookmark"></i></li>
                                    <li><a href="#">Đặt bàn</a></li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <ul class="navbar-nav centermenuitem">
                                    <li><i class="fa-solid fa-cart-shopping"></i></li>
                                    <li><a href="#">Đặt món mang về</a></li>
                                </ul>
                            </li>
                            <li class="nav-item centermenuitem">
                                <ul class="navbar-nav">
                                    <li><i class="fa-solid fa-utensils"></i></i></li>
                                    <li><a href="menu">Ăn tại chỗ</a></li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                    <!-- Right, cart and orders -->
                    <ul class="navbar-nav ml-auto leftmenu">
                        <li class="nav-item cart" data-count="${cart.size()}">
                            <i class="fa-solid fa-basket-shopping" onclick="openNav()"></i>
                        </li>
                        <li class="nav-item orders" data-count=""${orders.size()}>
                            <i class="fa-regular fa-clock" onclick="openFoodNav()"></i>
                        </li>
                        <li class="nav-item">
                            <i class="fa-solid fa-sliders"></i>
                        </li>
                    </ul>
                </div>
            </nav>
        </nav>
        <!-- cart -->
        <c:if test="${requestScope.keepnav=='true'}">
            <script>
                window.onload = function() {
                    openNav();
                }
            </script>
        </c:if>
        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <c:if test="${cart != null and !cart.isEmpty()}">
                <h2>Giỏ hàng</h2>
                <div class="scrollit">
                    <table style="width:100%">
                        <thead>
                            <tr>
                                <th>Tên Món</th>
                                <th>Số lượng</th>
                                <th>Đơn giá</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${cart}" var="i">
                                <tr>
                                    <td>${listOfDishNames.get(i.key)}</td>
                                    <td>
                                        <div class="quantity">
                                            <a href="cart?action=subtract&dishTypeId=${dishTypeId}&dishId=${i.key}">
                                                <button class="minus" type="button">-</button>
                                            </a>
                                            ${i.value}
                                            <a href="cart?action=add&dishTypeId=${dishTypeId}&dishId=${i.key}">
                                                <button class="plus" type="button" >+</button>
                                            </a>
                                        </div>
                                    </td>
                                    <td>${listOfDishPrices.get(i.key)*i.value}</td>
                                    <td>
                                        <a href="cart?action=remove&dishTypeId=${dishTypeId}&dishId=${i.key}">
                                            <button><i class="fa-solid fa-xmark"></i></button>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div id="total-order" class="row">
                    <div class="col-md-8 total">
                        Tổng: ${cookie.totalPrice.getValue()}
                    </div>
                    <a href="<%=request.getContextPath()%>/cart?action=order&dishTypeId=${dishTypeId}" class="col-md-4 order">
                        Đặt món
                    </a>
                </div>
            </c:if>
            <c:if test="${cart == null || cart.isEmpty()}">
                <h2>Giỏ hàng đang trống!</h2>
            </c:if>
        </div>

        <!-- orders -->
        <c:if test="${requestScope.keepfoodnav=='true'}">
            <script>
                window.onload = function() {
                    openFoodNav();
                }
            </script>
        </c:if>
        <div id="myFoodSidenav" class="foodsidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeFoodNav()">&times;</a>
            <c:if test="${orders != null and !orders.isEmpty()}">
                <h2>Món đã đặt</h2>
                <div class="scrollit">
                    <table style="width:100%">
                        <thead>
                            <tr>
                                <th>Tên Món</th>
                                <th>SL</th>
                                <th>Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="i">
                                <tr>
                                    <td>${listOfDishNames.get(i.dishId)}</td>
                                    <td>${i.quantity}</td>
                                    <td>${i.serviceStatus}</td>
                                    <c:if test="${i.serviceStatus!='Served'}">
                                        <td>
                                            <!-- Rush -->
                                            <a href="cart?action=rush&dishId=${i.dishId}&dishTypeId=${dishTypeId}">
                                                <button><i class="fa-solid fa-exclamation"></i></button>
                                            </a>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <a href="<%=request.getContextPath()%>/checkout" class="row">
                    <div class="col-12 finish">Thanh toán</div>
                </a>
            </c:if>
            <c:if test="${orders == null || orders.isEmpty()}">
                <h2>Hiện chưa có đơn, hãy đặt món ngay!</h2>
            </c:if>
        </div>
        
        <!-- Main menu -->
        <h1>Nhà hàng Bếp Việt</h1>
        <br/>
        <nav class="container main">
            <div class= "row menutype">
                <c:forEach items="${requestScope.listOfDishTypes}" var="i">
                    <!--MENU type of food-->
                    <div class ="col order-sm-2">
                        <div class="foodtype">
                            <a href="menu?dishTypeId=${i.dishTypeId}" style="color: black;
                               <c:if test="${requestScope.dishTypeId==i.dishTypeId}">color: #9A3B3B;</c:if>">
                               <img src="${i.dishTypeId==1 ? "assets/images/banhloc.PNG" :
                                           i.dishTypeId==2 ? "assets/images/supcua.png" :
                                           i.dishTypeId==3 ? "assets/images/cadieckhotuong.jpg" :
                                           i.dishTypeId==4 ? "assets/images/raumuongluoc.png" :
                                           i.dishTypeId==5 ? "assets/images/canhbaunautom.png" :
                                           i.dishTypeId==6 ? "assets/images/comtrnag.png" :
                                           i.dishTypeId==7 ? "assets/images/lauuyenuong.PNG" :
                                           i.dishTypeId==8 ? "assets/images/chekhoaideo.png" :
                                           "assets/images/nuocdautam.png"}">
                               <div class="type">
                                   ${i.dishTypeName}
                               </div> 
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </nav>

        <div class= "container-fluid listfood">
            <div class= "row d-inline-flex">
                <c:forEach items="${requestScope.ListOfDishes}" var="i">
                    <div class ="col-12 col-sm-6 col-md-3 p-2 span 2">
                        <div class="d-flex flex-column  height100">
                            <div class="item">
                                <div class="image">
                                    <img src="${i.imageLink}" alt="">
                                    <div class="details">
                                        <h5>${i.dishName}</h5>
                                        <p>${i.description}</p>
                                        <div class="price">
                                            <h5>${i.price}</h5>
                                            <a class="add-to-cart"
                                               <c:if test="${cookie.tableId==null}">onclick="getPhoneNo()"</c:if>
                                               <c:if test="${cookie.tableId!=null}">
                                                   href="cart?action=add&keepfoodnav=true&dishTypeId=${dishTypeId}&dishId=${i.dishId}"
                                               </c:if>>
                                                <i class="fa-solid fa-cart-plus"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <%@include file="/footer.jsp" %>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

        <!-- javascript for cart and orders -->
        <script>
                function openNav() {
                    document.getElementById("mySidenav").style.width = "550px";
                    document.getElementById("main").style.marginLeft = "550px";
                    document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
                }

                function closeNav() {
                    document.getElementById("mySidenav").style.width = "0";
                    document.getElementById("main").style.marginLeft = "0";
                    document.body.style.backgroundColor = "white";
                }

                function openFoodNav() {
                    document.getElementById("myFoodSidenav").style.width = "550px";
                    document.getElementById("main").style.marginLeft = "550px";
                    document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
                }

                function closeFoodNav() {
                    document.getElementById("myFoodSidenav").style.width = "0";
                    document.getElementById("main").style.marginLeft = "0";
                    document.body.style.backgroundColor = "white";
                }

                const plusButtons = document.querySelectorAll(".plus");
                const minusButtons = document.querySelectorAll(".minus");
                const numInputs = document.querySelectorAll(".num");

                let quantities = Array.from(numInputs).map(input => parseInt(input.value));

//                plusButtons.forEach((button, index) => {
//                    button.addEventListener("click", () => {
//                        quantities[index]++;
//                        quantities[index] = (quantities[index] < 10) ? quantities[index] : quantities[index];
//                        numInputs[index].value = quantities[index];
//                        console.log(quantities[index]);
//                    });
//                });
//
//                minusButtons.forEach((button, index) => {
//                    button.addEventListener("click", () => {
//                        if (quantities[index] > 1) {
//                            quantities[index]--;
//                            quantities[index] = (quantities[index] < 10) ? quantities[index] : quantities[index];
//                            numInputs[index].value = quantities[index];
//                            console.log(quantities[index]);
//                        }
//                    });
//                });

                function getPhoneNo() {
                    let phoneNo = prompt("Quý khách vui lòng nhập số điện thoại để xác minh đơn của quý khách");
                    if(phoneNo!==null || phoneNo.length>=10 && phoneNo.length<=15) {
                        window.location.href = "<%=request.getContextPath()%>/cart?action=checkPhoneNo&phoneNo=" + phoneNo;
                    }
                }
                
                function toCheckout() {
                    if(window.comfirm("Bạn có chắc chắn muốn thanh toán không?")) {
                        window.location.href = "<%=request.getContextPath()%>/checkout";
                    }
                }
        </script>

        <!-- style for cart and orders -->
        <style>
            /* width */
            ::-webkit-scrollbar {
                width: 10px;
            }

            /* Track */
            ::-webkit-scrollbar-track {
                background: #f1f1f1;
            }

            /* Handle */
            ::-webkit-scrollbar-thumb {
                background: #90836C;
            }

            /* Handle on hover */
            ::-webkit-scrollbar-thumb:hover {
                background: #4D3F33;
            }



            /*gio hang*/
            body {
                font-family: Be Vietnam;
                transition: background-color .5s;
            }

            .sidenav {
                height: 100%;
                width: 0;
                position: fixed;
                z-index: 1;
                top: 0;
                right: 0; /* Changed from left: 0; */
                background-color: #FAEBD7;
                overflow-x: hidden;
                transition: 0.5s;
                padding-top: 60px;
            }

            .sidenav h2{
                padding: 8px 8px 8px 32px;
                font-size: 32px;
            }
            .sidenav a {
                padding: 8px 8px 8px 32px;
                text-decoration: none;
                font-size: 32px;
                color: white;
                display: block;
                transition: 0.3s;
            }

            .sidenav a:hover {
                color: #887F77;
            }
            .sidenav .closebtn {
                width: 70px;
                color: #4D3F33;
                position: absolute;
                top: 0;
                left: 25px; /* Changed from right: 25px; */
                font-size: 36px;
                margin-right: 50px; /* Changed from margin-left: 50px; */
            }

            #main {
                transition: margin-right .5s; /* Changed from margin-left to margin-right */
                padding: 16px;
            }

            .foodsidenav {
                height: 100%;
                width: 0;
                position: fixed;
                z-index: 1;
                top: 0;
                right: 0; /* Changed from left: 0; */
                background-color: #FAEBD7;
                overflow-x: hidden;
                transition: 0.5s;
                padding-top: 60px;
            }

            .foodsidenav h2{
                padding: 8px 8px 8px 32px;
                font-size: 32px;
            }
            .foodsidenav a {
                padding: 8px 8px 8px 32px;
                text-decoration: none;
                font-size: 32px;
                color: #4D3F33;
                display: block;
                transition: 0.3s;
            }

            .foodsidenav a:hover {
                color: #D8C5A2;
            }

            .foodsidenav .closebtn {
                position: absolute;
                top: 0;
                left: 25px; /* Changed from right: 25px; */
                font-size: 36px;
                margin-right: 50px; /* Changed from margin-left: 50px; */
            }
            table{
                margin: 10px 10px;
                text-align: center;
                font-size: 24px;
            }

            table tr td{
                padding: 10px 0;
                width: 160px;

            }
            /*end gio hang*/

            .sidenav button{
                width: 34px;
                min-height: 30px;
                border-radius: 100px;
            }
            .foodsidenav button{
                width: 34px;
                min-height: 30px;
                border-radius: 100px;
            }
            #total-order {
                font-family: "Be Vietnam";
                font-size: 24px;
                text-align: center;
            }
            .total {
                background-color: #A27B5C;
                color: white;
            }
            .order{
                background-color: brown;
                color: white;
            }
            .finish{
                background-color: brown;
                color: white;
            }
            .quantity{
                width: 150px ;
                height: 50px;
                background-color: antiquewhite;
                color: #3B3B3B;
                padding: 15px;
                display: inline-flex;
                overflow: hidden;
                align-items: center;
                border-radius: 8px ;

            }
            .quantity input button{
                cursor: pointer;
            }
            .minus .plus{
                display: flex;
                justify-content: center;
                align-items: center;
                text-align: center;
                border: none;
                width: 50px;
                height: 50px;
                font-size: 1rem;
                background: 0 0;
                color: #000;
                cursor: pointer;
            }
            #input{
                width: 10px;
                border: none;
                font-size: 24px;
                text-align: center;
                color: #3B3B3B;
                background: 0 0;
                flex: 1;
                font-weight: 600;
            }
            #input:focus-visible{
                outline: none;
            }
            
            .cart {
                position: relative;
            }

            .cart::after {
                content: attr(data-count);
                position: absolute;
                top: -10px;
                right: -10px;
                background-color: red;
                color: white;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                text-align: center;
                line-height: 20px;
                font-size: 12px;
                <c:if test="${cart!=null || !cart.isEmpty()}">
                    display: block;
                </c:if>
                <c:if test="${cart==null || cart.isEmpty()}">
                    display: none;
                </c:if>
            }
            
            .orders {
                position: relative;
            }

            .orders::after {
                content: attr(data-count);
                position: absolute;
                top: -10px;
                right: -10px;
                background-color: red;
                color: white;
                border-radius: 50%;
                width: 20px;
                height: 20px;
                text-align: center;
                line-height: 20px;
                font-size: 12px;
                <c:if test="${orders!=null || !orders.isEmpty()}">
                    display: block;
                </c:if>
                <c:if test="${orders==null || orders.isEmpty()}">
                    display: none;
                </c:if>
            }
            
            .scrollit{
                overflow-y: scroll;
                height:73.4%;
            }
        </style>
    </body>
</html>
