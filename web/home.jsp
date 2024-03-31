<%-- 
    Document   : home
    Created on : Mar 5, 2024, 11:41:51 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bep Viet</title>
        <link rel="stylesheet" type="text/css" href="assets/css/mainstyle.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"/> 
        <link href='https://fonts.googleapis.com/css?family=Be Vietnam' rel='stylesheet'/>
    </head>
    <body>
        <c:if test="${param.transaction=='success'}">
            <script>
                window.onload = function() {
                    alert('Cảm ơn quý khách đã chọn Bếp Việt! Quý khách vui lòng đợi tại bàn cho đến khi nhân viên phục vụ mang hóa đơn ra.');
                };
            </script>
        </c:if>
        <div class="top">
            <video autoplay loop muted plays-inline class="bgvideo">
                <source src="assets/images/bgvideo.mp4" type="video/mp4">
            </video>
            <nav>
                <img src="assets/images/logormbg.png" class="logo">
                <ul>
                    <li>
                        <ul>
                            <li><i class="fa-regular fa-bookmark"></i></li>
                            <li><a href="#">Đặt bàn</a></li>
                        </ul>
                    </li>
                    <li>
                        <ul>
                            <li><i class="fa-solid fa-cart-shopping"></i></li>
                            <li><a href="#">Đặt món mang về</a></li>
                        </ul>
                    </li>
                    <li>
                        <ul>
                            <li><i class="fa-solid fa-utensils"></i></i></li>
                            <li><a href="menu">Thực đơn</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="content">
            <h1>Bếp Việt</h1>
        </div>
        <div>

        </div>
    </body>
</html>
