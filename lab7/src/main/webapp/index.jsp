<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html><html lang="ru"><meta charset="UTF-8"><title>Cookie и сессия</title><body>
<h1>Настройки страницы</h1><form method="post" action="save.jsp">
<p><label>Имя <input name="name" required maxlength="80"></label></p>
<p><label>Цвет <select name="color"><option value="white">Белый</option><option value="lightblue">Голубой</option><option value="lightgreen">Зелёный</option><option value="pink">Розовый</option></select></label></p>
<button>Сохранить и показать</button></form><p><a href="display.jsp">Показать сохранённые данные</a></p></body></html>
