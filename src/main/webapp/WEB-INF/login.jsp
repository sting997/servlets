<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE HTML>
<html>
   <head>
      <title>Zaloguj się</title>
   </head>
   <body>
        <form method="post" action="login">
            Użytkownik: <input type="text" name="user" />
            Hasło: <input type="password" name="password" />
            <input type="submit" value="Zaloguj"/>
         </form>
   </body>
</html>