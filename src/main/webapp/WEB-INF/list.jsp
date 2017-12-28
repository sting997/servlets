<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE HTML>

<html>
<head>
<title>Lista produktów</title>
</head>
<body>

    <table width="100%">
        <tr  align="left">
            <th>ID</th>
            <th>Nazwa</th>
            <th>Cena</th>
        </tr>
        <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.PK}</td>
                    <td> <a href="./edit?productId=${product.PK}"> ${product.name} </a> </td>
                    <td>${product.price}</td>
                    <td>
                        <form action="./list?productId=${product.PK}" method="post">
                            <button type="submit" OnClick="return confirm('Usunąć?')"> Usuń </button>
                        </form>
                    </td>
                </tr>
        </c:forEach>
    </table>

    <form action="./edit?action=save_product" method="get">
        <button type="submit"> Dodaj produkt </button>
    </form>

    <%@ include file = "logout.jsp" %>
    <%@ include file = "counters.jsp" %>

</body>
</html>
