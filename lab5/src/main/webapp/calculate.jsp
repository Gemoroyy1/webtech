<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="ru.etu.web.OrderCalculator" %>
<%
request.setCharacterEncoding("UTF-8");
if (!"POST".equals(request.getMethod())) { response.sendRedirect("index.jsp"); return; }
try {
    request.setAttribute("total", new OrderCalculator().calculate(request.getParameter("customer"), request.getParameter("quantity"), request.getParameter("delivery")));
} catch (IllegalArgumentException e) {
    request.setAttribute("error", e.getMessage());
    response.setStatus(400);
    request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);
    return;
}
%>
<!doctype html><html lang="ru"><meta charset="UTF-8"><title>Результат — JSP</title><body>
<h1>Заказ рассчитан</h1>
<p>Покупатель: <%= OrderCalculator.escape(request.getParameter("customer")) %></p>
<p>Количество: <%= OrderCalculator.escape(request.getParameter("quantity")) %></p>
<p>Получение: <%= "courier".equals(request.getParameter("delivery")) ? "Курьер" : "Самовывоз" %></p>
<p>Итого: <strong><%= request.getAttribute("total") %> руб.</strong></p>
<a href="index.jsp">Новый расчёт</a></body></html>
