<html>
   <head>
      <title>Edytuj produkt</title>
   </head>
    <body>
            <c:choose>
                <c:when test="${!empty productId}">
                    <form method="post" action="edit?productId=${productId}&action=save_product">
                </c:when>
                <c:otherwise>
                    <form method="post" action="edit?action=save_product">
                </c:otherwise>
            </c:choose>
            Nazwa: <input type="text" name="product_name" />
            Cena: <input type="text" name="product_price" />
            <input type="submit" value="Zapisz"/>
            <button type="button" onclick="window.location.href='/servlets/list'"> Anuluj </button>
        </form>
    </body>
</html>