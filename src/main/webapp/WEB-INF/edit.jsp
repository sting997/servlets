<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE HTML>
<html>
   <head>
      <title>Edytuj produkt</title>
      <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
   </head>
    <body>
            <c:choose>
                <c:when test="${not empty productId}">
                    <form method="post" action="edit?productId=${productId}&action=save_product">
                </c:when>
                <c:otherwise>
                    <form method="post" action="edit?action=save_product">
                </c:otherwise>
            </c:choose>
            Nazwa: <input type="text" id="name" name="product_name" />
            Cena: <input type="text" id="price" name="product_price" />
            <input type="submit" onclick="return validateForm()" value="Zapisz"/>
            <button type="button" onclick="window.location.href='/servlets/list'"> Anuluj </button>
        </form>
        <%@ include file = "logout.jsp" %>
        <%@ include file = "counters.jsp" %>

    </body>
</html>

<script>
function validateForm()
{
    if (!$("#name").val()){
        $("#price").parent().prev(".validation").remove();
        if ($("#name").parent().prev(".validation").length == 0) {
            $("#name").parent()
            .before("<div class='validation' style='color:red;margin-bottom: 20px;'>Brak nazwy!</div>");
        }
        return false;
    }
    else {
        $("#name").parent().prev(".validation").remove();
    }

    if (!$("#price").val()){
        if ($("#price").parent().prev(".validation").length == 0) {
            $("#price").parent()
                .before("<div class='validation' style='color:red;margin-bottom: 20px;'>Brak ceny!</div>");
        }
        return false;
    }
    else {
        $("#price").parent().prev(".validation").remove();
    }

    if (!isFinite(document.getElementById('price').value) ){
        if ($("#price").parent().prev(".validation").length == 0) {
            $("#price").parent()
                .before("<div class='validation' style='color:red;margin-bottom: 20px;'>Niepoprawna cena!</div>");
        }
        return false;
    }
    else {
        $("#price").parent().prev(".validation").remove();
    }

    return true;
}
</script>