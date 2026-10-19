<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html><html lang="ru"><head><meta charset="UTF-8"><title>Заказ — JSP</title></head><body>
<h1>Расчёт заказа на JSP</h1><p>Блокнот: 250 руб. Курьерская доставка: 300 руб.</p>
<form method="post" action="calculate.jsp">
<p><label>Покупатель <input name="customer" required maxlength="80"></label></p>
<p><label>Количество <input name="quantity" type="number" min="1" max="1000" value="1" required></label></p>
<p><label>Получение <select name="delivery"><option value="pickup">Самовывоз</option><option value="courier">Курьер</option></select></label></p>
<button>Рассчитать</button></form></body></html>
