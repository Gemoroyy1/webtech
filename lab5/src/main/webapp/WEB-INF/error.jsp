<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="ru.etu.web.OrderCalculator" %>
<!doctype html><html lang="ru"><meta charset="UTF-8"><title>Ошибка — JSP</title><body>
<h1>Ошибка ввода</h1><p><%= OrderCalculator.escape((String)request.getAttribute("error")) %></p>
<a href="index.jsp">Исправить данные</a></body></html>
