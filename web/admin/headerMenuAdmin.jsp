<%-- 
    Document   : headerMenuAdmin
    Created on : Feb 18, 2024, 10:25:43 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Roboto&display=swap');


            .nav {
                display: flex;
                justify-content: center;
                text-align: center;
                font-size: 1rem;
                border-bottom: 1px solid #dee2e6;
                padding-left: 5px;
                padding-right: 5px;

            }

            .nav-link {
                text-decoration: none;
                display: inline-block;
                padding: 13px 13px;
                color: #212529;
                margin-right: 5px;
            }

            .nav-link:hover {
                text-decoration:none;
                background-color: #d0ebff;
                border-bottom: 2px solid #339af0
            }
            .nav-link.current {
                border-bottom: 2px solid #1971c2;
            }
            .btn {
                padding: 10px 20px;
                font-size: 1rem;
            }
            @media (min-width: 768px) {
                .nav {
                    font-size: 1.1rem;
                    padding-left: 10px;
                    padding-right: 10px;
                }
                .nav-link {
                    padding: 20px 20px;
                }

            }
        </style>


    </head>
    <body>
        <nav class="nav">
            <a href="<%=request.getContextPath()%>/admin/home" class="nav-link">Home</a>
            <a href="<%=request.getContextPath()%>/admin/dishmanager" class="nav-link">Dish Manager</a>
            <a href="<%=request.getContextPath()%>/admin/employeemanager" class="nav-link">Employee Manager</a>
            <a href="<%=request.getContextPath()%>/admin/invoicemanager" class="nav-link">Invoice Manager</a>
            <button onclick="logout()" class="nav-link">Logout</button>
        </nav>
        <script src="script.js"></script>



        <script>
                function logout() {
                    if (window.confirm("Are you sure you want to logout?")) {
                        location.replace("${pageContext.request.contextPath}/logout");
                    }
                }
        </script>
    </body>
</html>
