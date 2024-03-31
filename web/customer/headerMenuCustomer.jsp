<%-- 
    Document   : headerMenuCustomer
    Created on : Mar 5, 2024, 11:33:43 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
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
                                    <li><a href="#">Reservation</a></li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <ul class="navbar-nav centermenuitem">
                                    <li><i class="fa-solid fa-cart-shopping"></i></li>
                                    <li><a href="#">Delivery</a></li>
                                </ul>
                            </li>
                            <li class="nav-item centermenuitem">
                                <ul class="navbar-nav">
                                    <li><i class="fa-solid fa-utensils"></i></i></li>
                                    <li><a href="menu">Dine-in</a></li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                    <!-- Right, cart and orders -->
                    <ul class="navbar-nav ml-auto leftmenu">
                        <li class="nav-item">
                            <i class="fa-solid fa-basket-shopping"></i>
                        </li>
                        <li class="nav-item">
                            <i class="fa-regular fa-clock"></i>
                        </li>
                        <li class="nav-item">
                            <i class="fa-solid fa-sliders"></i>
                        </li>
                    </ul>
                </div>
            </nav>
        </nav>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
</html>
